package br.com.higia.application.pacientedoenca.create;

import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;

import java.util.Objects;

import static br.com.higia.domain.utils.DateUtils.parse;

public class DefaultCreatePacienteDoencaUseCase extends CreatePacienteDoencaUseCase{

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public DefaultCreatePacienteDoencaUseCase(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Override
    public CreatePacienteDoencaOutput execute(CreatePacienteDoencaCommand input) {
        PacienteDoenca pacienteDoenca = PacienteDoenca.newPacienteDoenca(
                PacienteID.from(input.pacienteId()),
                DoencaCronicaID.from(input.doencaCronicaId()),
                parse(input.dataDiagnostico())
        );
        return CreatePacienteDoencaOutput.from(this.pacienteDoencaGateway.create(pacienteDoenca));
    }
}
