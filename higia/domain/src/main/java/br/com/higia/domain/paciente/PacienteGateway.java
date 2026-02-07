package br.com.higia.domain.paciente;

import java.util.Optional;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

public interface PacienteGateway {

    Paciente create(Paciente paciente);

    Optional<Paciente> findById(PacienteID id);

    Paciente update(Paciente paciente);

    Pagination<Paciente> findAll(SearchQuery query);

}
