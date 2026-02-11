package br.com.higia.infrastructure.doencacronica;

import br.com.higia.domain.doencacronica.DoencaCronica;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import br.com.higia.infrastructure.doencacronica.persistence.DoencaCronicaJpaEntity;
import br.com.higia.infrastructure.doencacronica.persistence.DoencaCronicaJpaRepository;
import br.com.higia.infrastructure.paciente.persistence.PacienteJpaEntity;
import br.com.higia.infrastructure.pacientedoenca.persistence.PacienteDoencaJpaEntity;
import br.com.higia.infrastructure.utils.SpecificationUtils;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Component
public class DoencaCronicaPostgresGateway implements DoencaCronicaGateway {

    private final DoencaCronicaJpaRepository doencaCronicaRepository;

    public DoencaCronicaPostgresGateway(final DoencaCronicaJpaRepository doencaCronicaRepository) {
        this.doencaCronicaRepository = Objects.requireNonNull(doencaCronicaRepository);
    }

    @Override
    public Optional<DoencaCronica> findById(final DoencaCronicaID id) {
        return doencaCronicaRepository.findById(uuid(id.getValue()))
                .map(DoencaCronicaJpaEntity::toAggregate);
    }

    @Override
    public Pagination<DoencaCronica> findAll(SearchQuery query) {
        final var page = PageRequest.of(
                query.page(),
                query.perPage(),
                Sort.by(Sort.Direction.fromString(query.direction()), query.sort()));

        final var where = Optional.ofNullable(query.terms())
                .filter(str -> !str.isBlank())
                .map(this::assembleSpecification)
                .orElse(null);

        final var pageResult = doencaCronicaRepository.findAll(Specification.where(where), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(DoencaCronicaJpaEntity::toAggregate).toList());
    }

    private Specification<DoencaCronicaJpaEntity> assembleSpecification(final String terms) {
        return SpecificationUtils.like("nome", terms);
    }
}
