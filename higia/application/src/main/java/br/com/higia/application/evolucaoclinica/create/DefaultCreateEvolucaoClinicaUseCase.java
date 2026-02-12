package br.com.higia.application.evolucaoclinica.create;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.prontuario.ProntuarioID;
import br.com.higia.domain.utils.DateUtils;
import br.com.higia.domain.utils.InstantUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class DefaultCreateEvolucaoClinicaUseCase extends CreateEvolucaoClinicaUseCase {

    private final EvolucaoClinicaGateway evolucaoClinicaGateway;

    public DefaultCreateEvolucaoClinicaUseCase(final EvolucaoClinicaGateway evolucaoClinicaGateway) {
        this.evolucaoClinicaGateway = Objects.requireNonNull(evolucaoClinicaGateway);
    }

    @Override
    public CreateEvolucaoClinicaOutput execute(final CreateEvolucaoClinicaCommand input) {
        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                ProntuarioID.from(input.prontuarioId()),
                InstantUtils.parseInstant(DateUtils.parse(input.dataAtendimento())),
                input.pressaoSistolica(),
                input.pressaoDiastolica(),
                input.glicemia(),
                input.peso(),
                input.observacoes());

        return CreateEvolucaoClinicaOutput.from(evolucaoClinicaGateway.create(evolucao));
    }


}
