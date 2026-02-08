package br.com.higia.application.evolucaoclinica.retrieve.get;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaID;
import br.com.higia.domain.exceptions.NotFoundException;

import java.util.Objects;

public class DefaultGetEvolucaoClinicaByIdUseCase extends GetEvolucaoClinicaByIdUseCase {

    private final EvolucaoClinicaGateway evolucaoClinicaGateway;

    public DefaultGetEvolucaoClinicaByIdUseCase(final EvolucaoClinicaGateway evolucaoClinicaGateway) {
        this.evolucaoClinicaGateway = Objects.requireNonNull(evolucaoClinicaGateway);
    }

    @Override
    public EvolucaoClinicaOutput execute(final String input) {
        final var evolucaoId = EvolucaoClinicaID.from(input);

        final var evolucao = evolucaoClinicaGateway.findById(evolucaoId)
                .orElseThrow(() -> NotFoundException.with(EvolucaoClinica.class, evolucaoId));

        return EvolucaoClinicaOutput.from(evolucao);
    }
}
