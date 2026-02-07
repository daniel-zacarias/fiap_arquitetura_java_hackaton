package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.domain.paciente.PacienteGateway;

import java.util.Objects;

public class DefaultListPacientesUseCase extends ListPacientesUseCase {

    private final PacienteGateway pacienteGateway;

    public DefaultListPacientesUseCase(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }

    @Override
    public ListPacientesOutput execute(final ListPacientesInput input) {
        final var searchQuery = input.toSearchQuery();
        final var result = pacienteGateway.findAll(searchQuery);
        return ListPacientesOutput.from(result);
    }
}
