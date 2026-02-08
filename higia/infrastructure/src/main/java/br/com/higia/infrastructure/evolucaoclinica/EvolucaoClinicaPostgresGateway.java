package br.com.higia.infrastructure.evolucaoclinica;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaID;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaSearchQuery;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.infrastructure.evolucaoclinica.persistence.EvolucaoClinicaJpaEntity;
import br.com.higia.infrastructure.evolucaoclinica.persistence.EvolucaoClinicaJpaRepository;
import br.com.higia.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class EvolucaoClinicaPostgresGateway implements EvolucaoClinicaGateway {

    private final EvolucaoClinicaJpaRepository evolucaoClinicaRepository;

    public EvolucaoClinicaPostgresGateway(final EvolucaoClinicaJpaRepository evolucaoClinicaRepository) {
        this.evolucaoClinicaRepository = Objects.requireNonNull(evolucaoClinicaRepository);
    }

    @Override
    public EvolucaoClinica create(final EvolucaoClinica evolucaoClinica) {
        return save(evolucaoClinica);
    }

    @Override
    public Optional<EvolucaoClinica> findById(final EvolucaoClinicaID id) {
        return evolucaoClinicaRepository.findById(uuid(id.getValue()))
                .map(EvolucaoClinicaJpaEntity::toAggregate);
    }

    @Override
    public Pagination<EvolucaoClinica> findAll(final EvolucaoClinicaSearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

        final var where = assembleSpecification(query);

        final var pageResult = evolucaoClinicaRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(EvolucaoClinicaJpaEntity::toAggregate).toList());
    }

    @Override
    public EvolucaoClinica update(final EvolucaoClinica evolucaoClinica) {
        return save(evolucaoClinica);
    }

    private EvolucaoClinica save(final EvolucaoClinica evolucaoClinica) {
        return evolucaoClinicaRepository.save(EvolucaoClinicaJpaEntity.from(evolucaoClinica)).toAggregate();
    }

    private Specification<EvolucaoClinicaJpaEntity> assembleSpecification(final EvolucaoClinicaSearchQuery query) {
        Specification<EvolucaoClinicaJpaEntity> spec = null;

        if (query.terms() != null && !query.terms().isBlank()) {
            spec = append(spec, assembleTerms(query.terms()));
        }

        if (query.prontuarioId() != null) {
            spec = append(spec, (root, criteria, cb) -> cb.equal(
                    root.get("prontuarioId"),
                    uuid(query.prontuarioId().getValue())));
        }

        return spec;
    }

    private Specification<EvolucaoClinicaJpaEntity> assembleTerms(final String terms) {
        final var parsedUuid = parseUuid(terms.trim());
        if (parsedUuid.isPresent()) {
            return (root, criteria, cb) -> cb.or(
                    cb.equal(root.get("id"), parsedUuid.get()),
                    cb.equal(root.get("prontuarioId"), parsedUuid.get()));
        }

        return SpecificationUtils.like("observacoes", terms);
    }

    private Optional<UUID> parseUuid(final String term) {
        try {
            return Optional.of(UUID.fromString(term));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private Specification<EvolucaoClinicaJpaEntity> append(
            final Specification<EvolucaoClinicaJpaEntity> base,
            final Specification<EvolucaoClinicaJpaEntity> add) {
        if (add == null) {
            return base;
        }
        return base == null ? Specification.where(add) : base.and(add);
    }
}
