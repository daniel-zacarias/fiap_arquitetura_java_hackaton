package br.com.higia.application.evolucaoclinica.create;

import java.math.BigDecimal;

public record CreateEvolucaoClinicaCommand(
        String prontuarioId,
        String dataAtendimento,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Integer glicemia,
        BigDecimal peso,
        String observacoes) {
    public static CreateEvolucaoClinicaCommand with(
            final String prontuarioId,
            final String dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes) {
        return new CreateEvolucaoClinicaCommand(
                prontuarioId,
                dataAtendimento,
                pressaoSistolica,
                pressaoDiastolica,
                glicemia,
                peso,
                observacoes);
    }
}
