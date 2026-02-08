package br.com.higia.application.evolucaoclinica.update;

import java.math.BigDecimal;
import java.time.Instant;

public record UpdateEvolucaoClinicaCommand(
        String id,
        String dataAtendimento,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Integer glicemia,
        BigDecimal peso,
        String observacao
) {
    public static UpdateEvolucaoClinicaCommand with(
            final String id,
            final String dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacao
    ) {
        return new UpdateEvolucaoClinicaCommand(
                id,
                dataAtendimento,
                pressaoSistolica,
                pressaoDiastolica,
                glicemia,
                peso,
                observacao
        );
    }
}
