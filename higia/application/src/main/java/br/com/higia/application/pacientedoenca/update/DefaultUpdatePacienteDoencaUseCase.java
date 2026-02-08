package br.com.higia.application.pacientedoenca.update;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaID;
import br.com.higia.domain.utils.DateUtils;

import java.util.Objects;

public class DefaultUpdatePacienteDoencaUseCase extends UpdatePacienteDoencaUseCase {

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public DefaultUpdatePacienteDoencaUseCase(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Override
    public UpdatePacienteDoencaOutput execute(final UpdatePacienteDoencaCommand input) {
        final var pacienteDoencaId = PacienteDoencaID.from(input.id());

        final var pacienteDoenca = pacienteDoencaGateway.findById(pacienteDoencaId)
                .orElseThrow(() -> NotFoundException.with(PacienteDoenca.class, pacienteDoencaId));

        pacienteDoenca.update(DateUtils.parse(input.dataDiagnostico()), input.ativo());

        return UpdatePacienteDoencaOutput.from(pacienteDoencaGateway.update(pacienteDoenca));
    }
}
