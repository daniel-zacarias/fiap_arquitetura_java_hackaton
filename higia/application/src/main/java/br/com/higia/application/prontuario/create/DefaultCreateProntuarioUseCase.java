package br.com.higia.application.prontuario.create;

import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;

import java.util.Objects;

public class DefaultCreateProntuarioUseCase extends CreateProntuarioUseCase {

    private final ProntuarioGateway prontuarioGateway;

    public DefaultCreateProntuarioUseCase(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Override
    public CreateProntuarioOutput execute(final CreateProntuarioCommand input) {
        final var prontuario = Prontuario.newProntuario(PacienteID.from(input.pacienteId()));

        return CreateProntuarioOutput.from(prontuarioGateway.create(prontuario));
    }
}
