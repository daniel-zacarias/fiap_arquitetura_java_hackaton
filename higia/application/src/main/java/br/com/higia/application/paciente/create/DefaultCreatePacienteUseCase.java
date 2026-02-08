package br.com.higia.application.paciente.create;

import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.utils.DateUtils;
import java.util.Objects;

public class DefaultCreatePacienteUseCase extends CreatePacienteUseCase {

    private final PacienteGateway pacienteGateway;

    public DefaultCreatePacienteUseCase(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }

    @Override
    public CreatePacienteOutput execute(CreatePacienteCommand input) {
        Paciente paciente = Paciente.newPaciente(
                input.nome(),
                DateUtils.parse(input.dataNascimento()),
                input.cpf(),
                input.nacionalidade(),
                input.cep(),
                input.endereco()
        );

        return CreatePacienteOutput.from(pacienteGateway.create(paciente));
    }
}
