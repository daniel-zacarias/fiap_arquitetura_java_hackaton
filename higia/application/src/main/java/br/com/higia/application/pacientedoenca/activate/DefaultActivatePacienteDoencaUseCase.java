package br.com.higia.application.pacientedoenca.activate;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaID;

import java.util.Objects;

public class DefaultActivatePacienteDoencaUseCase extends ActivatePacienteDoencaUseCase {

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public DefaultActivatePacienteDoencaUseCase(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Override
    public ActivatePacienteDoencaOutput execute(final String input) {
        final var pacienteDoencaId = PacienteDoencaID.from(input);

        final var pacienteDoenca = pacienteDoencaGateway.findById(pacienteDoencaId)
                .orElseThrow(() -> NotFoundException.with(PacienteDoenca.class, pacienteDoencaId));

        pacienteDoenca.ativar();

        return ActivatePacienteDoencaOutput.from(pacienteDoencaGateway.update(pacienteDoenca));
    }
}
