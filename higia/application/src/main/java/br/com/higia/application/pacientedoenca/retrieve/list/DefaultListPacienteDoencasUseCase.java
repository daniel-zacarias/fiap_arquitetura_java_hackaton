package br.com.higia.application.pacientedoenca.retrieve.list;

import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaSearchQuery;
import br.com.higia.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListPacienteDoencasUseCase extends ListPacienteDoencasUseCase {

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public DefaultListPacienteDoencasUseCase(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Override
    public Pagination<ListPacienteDoencaOutput> execute(final PacienteDoencaSearchQuery input) {
        return pacienteDoencaGateway.findAll(input)
                .map(ListPacienteDoencaOutput::from);
    }
}
