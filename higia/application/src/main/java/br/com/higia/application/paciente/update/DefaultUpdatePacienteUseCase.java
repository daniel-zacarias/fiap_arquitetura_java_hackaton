package br.com.higia.application.paciente.update;

import br.com.higia.application.paciente.update.UpdatePacienteCommand;
import br.com.higia.application.paciente.update.UpdatePacienteOutput;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.utils.DateUtils;

import java.util.Objects;

public class DefaultUpdatePacienteUseCase extends UpdatePacienteUseCase {

    private final PacienteGateway pacienteGateway;

    public DefaultUpdatePacienteUseCase(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }

    @Override
    public UpdatePacienteOutput execute(UpdatePacienteCommand input) {
        final var id = input.id();

        final var pacienteId = PacienteID.from(id);

        final var paciente = pacienteGateway.findById(pacienteId)
                .orElseThrow(() -> NotFoundException.with(Paciente.class, pacienteId));

        paciente.update(
                input.nome(),
                DateUtils.parse(input.dataNascimento()),
                input.cpf(),
                input.nacionalidade(),
                input.cep(),
                input.endereco());

        return UpdatePacienteOutput.from(pacienteGateway.update(paciente));
    }
}
