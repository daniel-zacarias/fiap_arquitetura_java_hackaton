package br.com.higia.infrastructure.prontuario;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioID;
import br.com.higia.domain.prontuario.ProntuarioSearchQuery;
import br.com.higia.infrastructure.prontuario.persistence.ProntuarioJpaEntity;
import br.com.higia.infrastructure.prontuario.persistence.ProntuarioJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class ProntuarioPostgresGateway implements ProntuarioGateway {

    private final ProntuarioJpaRepository prontuarioRepository;

    public ProntuarioPostgresGateway(final ProntuarioJpaRepository prontuarioRepository) {
        this.prontuarioRepository = Objects.requireNonNull(prontuarioRepository);
    }

    @Override
    public Prontuario create(final Prontuario prontuario) {
        return save(prontuario);
    }

    @Override
    public Optional<Prontuario> findById(final ProntuarioID id) {
        return prontuarioRepository.findById(uuid(id.getValue()))
                .map(ProntuarioJpaEntity::toAggregate);
    }

    @Override
    public Optional<Prontuario> findAtivoByPacienteId(final br.com.higia.domain.paciente.PacienteID pacienteId) {
        return prontuarioRepository.findByPacienteIdAndAtivo(uuid(pacienteId.getValue()), true)
                .map(ProntuarioJpaEntity::toAggregate);
    }

    @Override
    public Pagination<Prontuario> findAll(final ProntuarioSearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

        final var where = assembleSpecification(query);

        final var pageResult = prontuarioRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ProntuarioJpaEntity::toAggregate).toList());
    }

    @Override
    public Prontuario update(final Prontuario prontuario) {
        return save(prontuario);
    }

    private Prontuario save(final Prontuario prontuario) {
        return prontuarioRepository.save(ProntuarioJpaEntity.from(prontuario)).toAggregate();
    }

    private Specification<ProntuarioJpaEntity> assembleSpecification(final ProntuarioSearchQuery query) {
        Specification<ProntuarioJpaEntity> spec = null;

        if (query.terms() != null && !query.terms().isBlank()) {
            spec = append(spec, assembleTerms(query.terms()));
        }

        if (query.pacienteId() != null) {
            spec = append(spec, (root, criteria, cb) -> cb.equal(
                    root.get("pacienteId"),
                    uuid(query.pacienteId().getValue())));
        }

        if (query.ativo() != null) {
            spec = append(spec, (root, criteria, cb) -> cb.equal(root.get("ativo"), query.ativo()));
        }

        return spec;
    }

    private Specification<ProntuarioJpaEntity> assembleTerms(final String terms) {
        final var parsedUuid = parseUuid(terms.trim());
        if (parsedUuid.isEmpty()) {
            return null;
        }

        return (root, criteria, cb) -> cb.or(
                cb.equal(root.get("id"), parsedUuid.get()),
                cb.equal(root.get("pacienteId"), parsedUuid.get()));
    }

    private Optional<UUID> parseUuid(final String term) {
        try {
            return Optional.of(UUID.fromString(term));
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    private Specification<ProntuarioJpaEntity> append(
            final Specification<ProntuarioJpaEntity> base,
            final Specification<ProntuarioJpaEntity> add) {
        if (add == null) {
            return base;
        }
        return base == null ? Specification.where(add) : base.and(add);
    }
}
