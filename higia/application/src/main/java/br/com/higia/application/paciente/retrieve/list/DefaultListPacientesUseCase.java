package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListPacientesUseCase extends ListPacientesUseCase {

    private final PacienteGateway pacienteGateway;

    public DefaultListPacientesUseCase(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }


    @Override
    public Pagination<ListPacientesOutput> execute(SearchQuery input) {
        return pacienteGateway.findAll(input)
                .map(ListPacientesOutput::from);
    }
}
