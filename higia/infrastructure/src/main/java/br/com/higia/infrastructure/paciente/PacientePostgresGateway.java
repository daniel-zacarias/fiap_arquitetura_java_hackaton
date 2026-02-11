package br.com.higia.infrastructure.paciente;

import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import br.com.higia.infrastructure.paciente.persistence.PacienteJpaEntity;
import br.com.higia.infrastructure.paciente.persistence.PacienteJpaRepository;
import br.com.higia.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class PacientePostgresGateway implements PacienteGateway {

    private final PacienteJpaRepository pacienteRepository;

    public PacientePostgresGateway(final PacienteJpaRepository pacienteRepository) {
        this.pacienteRepository = Objects.requireNonNull(pacienteRepository);
    }

    @Override
    public Paciente create(final Paciente paciente) {
        return save(paciente);
    }

    @Override
    public Optional<Paciente> findById(final PacienteID id) {
        return pacienteRepository.findById(uuid(id.getValue()))
                .map(PacienteJpaEntity::toAggregate);
    }

    @Override
    public Paciente update(final Paciente paciente) {
        return save(paciente);
    }

    @Override
    public Pagination<Paciente> findAll(final SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

        final var where = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = pacienteRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(PacienteJpaEntity::toAggregate).toList());

    }

    private Specification<PacienteJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("name", terms);
    }

    private Paciente save(final Paciente paciente) {
        return pacienteRepository.save(PacienteJpaEntity.from(paciente)).toAggregate();
    }
}
