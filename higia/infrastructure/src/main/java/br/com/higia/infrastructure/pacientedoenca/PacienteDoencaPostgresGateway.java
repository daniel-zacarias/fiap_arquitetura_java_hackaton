package br.com.higia.infrastructure.pacientedoenca;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaID;
import br.com.higia.domain.pacientedoenca.PacienteDoencaSearchQuery;
import br.com.higia.infrastructure.pacientedoenca.persistence.PacienteDoencaJpaEntity;
import br.com.higia.infrastructure.pacientedoenca.persistence.PacienteDoencaJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class PacienteDoencaPostgresGateway implements PacienteDoencaGateway {

    private final PacienteDoencaJpaRepository pacienteDoencaRepository;

    public PacienteDoencaPostgresGateway(final PacienteDoencaJpaRepository pacienteDoencaRepository) {
        this.pacienteDoencaRepository = Objects.requireNonNull(pacienteDoencaRepository);
    }

    @Override
    public PacienteDoenca create(final PacienteDoenca pacienteDoenca) {
        return save(pacienteDoenca);
    }

    @Override
    public Optional<PacienteDoenca> findById(final PacienteDoencaID id) {
        return pacienteDoencaRepository.findById(uuid(id.getValue()))
                .map(PacienteDoencaJpaEntity::toAggregate);
    }

    @Override
    public Pagination<PacienteDoenca> findAll(final PacienteDoencaSearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

        final var where = assembleSpecification(query);

        final var pageResult = pacienteDoencaRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(PacienteDoencaJpaEntity::toAggregate).toList());
    }

    @Override
    public PacienteDoenca update(final PacienteDoenca pacienteDoenca) {
        return save(pacienteDoenca);
    }

    private PacienteDoenca save(final PacienteDoenca pacienteDoenca) {
        return pacienteDoencaRepository.save(PacienteDoencaJpaEntity.from(pacienteDoenca)).toAggregate();
    }

    private Specification<PacienteDoencaJpaEntity> assembleSpecification(final PacienteDoencaSearchQuery query) {
        Specification<PacienteDoencaJpaEntity> spec = null;

        if (query.terms() != null && !query.terms().isBlank()) {
            spec = append(spec, assembleTerms(query.terms()));
        }

        if (query.pacienteId() != null) {
            spec = append(spec, (root, criteria, cb) -> cb.equal(
                    root.get("pacienteId"),
                    uuid(query.pacienteId().getValue())));
        }

        if (query.doencaCronicaId() != null) {
            spec = append(spec, (root, criteria, cb) -> cb.equal(
                    root.get("doencaCronicaId"),
                    uuid(query.doencaCronicaId().getValue())));
        }

        return spec;
    }

    private Specification<PacienteDoencaJpaEntity> assembleTerms(final String terms) {
        final var parsedUuid = parseUuid(terms.trim());
        if (parsedUuid.isEmpty()) {
            return null;
        }

        return (root, criteria, cb) -> cb.or(
                cb.equal(root.get("id"), parsedUuid.get()),
                cb.equal(root.get("pacienteId"), parsedUuid.get()),
                cb.equal(root.get("doencaCronicaId"), parsedUuid.get()));
    }

    private Optional<UUID> parseUuid(final String term) {
        try {
            return Optional.of(UUID.fromString(term));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private Specification<PacienteDoencaJpaEntity> append(
            final Specification<PacienteDoencaJpaEntity> base,
            final Specification<PacienteDoencaJpaEntity> add) {
        if (add == null) {
            return base;
        }
        return base == null ? Specification.where(add) : base.and(add);
    }
}
