package br.com.higia.application.evolucaoclinica.retrieve.list;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;

import java.math.BigDecimal;

public record ListEvolucaoClinicaOutput(
        String id,
        String prontuarioId,
        String dataAtendimento,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Integer glicemia,
        BigDecimal peso,
        String observacoes,
        String createdAt) {
    public static ListEvolucaoClinicaOutput from(final EvolucaoClinica evolucaoClinica) {
        return new ListEvolucaoClinicaOutput(
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
