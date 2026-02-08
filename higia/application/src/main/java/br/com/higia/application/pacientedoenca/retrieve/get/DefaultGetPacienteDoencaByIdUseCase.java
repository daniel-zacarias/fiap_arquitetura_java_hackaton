package br.com.higia.application.pacientedoenca.retrieve.get;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaID;

import java.util.Objects;

public class DefaultGetPacienteDoencaByIdUseCase extends GetPacienteDoencaByIdUseCase {

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public DefaultGetPacienteDoencaByIdUseCase(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Override
    public PacienteDoencaOutput execute(final String input) {
        final var pacienteDoencaId = PacienteDoencaID.from(input);

        final var pacienteDoenca = pacienteDoencaGateway.findById(pacienteDoencaId)
                .orElseThrow(() -> NotFoundException.with(PacienteDoenca.class, pacienteDoencaId));

        return PacienteDoencaOutput.from(pacienteDoenca);
    }
}
