package br.com.higia.application.evolucaoclinica.update;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaID;
import br.com.higia.domain.exceptions.NotFoundException;

import java.util.Objects;

import static br.com.higia.domain.utils.DateUtils.parseInstant;

public class DefaultUpdateEvolucaoClinicaUseCase extends UpdateEvolucaoClinicaUseCase {

    private final EvolucaoClinicaGateway evolucaoClinicaGateway;

    public DefaultUpdateEvolucaoClinicaUseCase(final EvolucaoClinicaGateway evolucaoClinicaGateway) {
        this.evolucaoClinicaGateway = Objects.requireNonNull(evolucaoClinicaGateway);
    }


    @Override
    public UpdateEvolucaoClinicaOutput execute(UpdateEvolucaoClinicaCommand input) {
        EvolucaoClinicaID evolucaoClinicaID = EvolucaoClinicaID.from(input.id());
        EvolucaoClinica evolucaoClinica = this.evolucaoClinicaGateway.findById(evolucaoClinicaID)
                .orElseThrow(() -> NotFoundException.with(EvolucaoClinica.class, evolucaoClinicaID));

        evolucaoClinica.update(
                parseInstant(input.dataAtendimento()),
                input.pressaoSistolica(),
                input.pressaoDiastolica(),
                input.glicemia(),
                input.peso(),
                input.observacao()
        );

        return UpdateEvolucaoClinicaOutput.from(this.evolucaoClinicaGateway.update(evolucaoClinica));
    }
}
