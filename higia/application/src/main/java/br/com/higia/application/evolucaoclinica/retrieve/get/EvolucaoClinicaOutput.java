package br.com.higia.application.evolucaoclinica.retrieve.get;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;

import java.math.BigDecimal;

public record EvolucaoClinicaOutput(
        String id,
        String prontuarioId,
        String dataAtendimento,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Integer glicemia,
        BigDecimal peso,
        String observacoes,
        String createdAt) {
    public static EvolucaoClinicaOutput from(final EvolucaoClinica evolucaoClinica) {
        return new EvolucaoClinicaOutput(
                evolucaoClinica.getId().getValue(),
                evolucaoClinica.getProntuarioId().getValue(),
                evolucaoClinica.getDataAtendimento() != null ? evolucaoClinica.getDataAtendimento().toString() : null,
                evolucaoClinica.getPressaoSistolica(),
                evolucaoClinica.getPressaoDiastolica(),
                evolucaoClinica.getGlicemia(),
                evolucaoClinica.getPeso(),
                evolucaoClinica.getObservacoes(),
                evolucaoClinica.getCreatedAt() != null ? evolucaoClinica.getCreatedAt().toString() : null);
    }
}
