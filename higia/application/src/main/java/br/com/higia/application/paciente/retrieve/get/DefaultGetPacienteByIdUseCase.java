package br.com.higia.application.paciente.retrieve.get;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;

import java.util.Objects;

public class DefaultGetPacienteByIdUseCase extends GetPacienteByIdUseCase {

    private final PacienteGateway pacienteGateway;

    public DefaultGetPacienteByIdUseCase(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }

    @Override
    public PacienteOutput execute(String input) {
        final var pacienteId = PacienteID.from(input);

        final var paciente = pacienteGateway.findById(pacienteId)
                .orElseThrow(() -> NotFoundException.with(Paciente.class, pacienteId));

        return PacienteOutput.from(paciente);
    }
}
