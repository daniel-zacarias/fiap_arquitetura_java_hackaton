package br.com.higia.application.prontuario.retrieve.get;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;

import java.util.Objects;

public class DefaultGetProntuarioAtivoByPacienteIdUseCase extends GetProntuarioAtivoByPacienteIdUseCase {

    private final ProntuarioGateway prontuarioGateway;

    public DefaultGetProntuarioAtivoByPacienteIdUseCase(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Override
    public ProntuarioOutput execute(final String input) {
        final var pacienteId = PacienteID.from(input);

        final var prontuario = prontuarioGateway.findAtivoByPacienteId(pacienteId)
                .orElseThrow(() -> NotFoundException.with(Prontuario.class, pacienteId));

        return ProntuarioOutput.from(prontuario);
    }
}
