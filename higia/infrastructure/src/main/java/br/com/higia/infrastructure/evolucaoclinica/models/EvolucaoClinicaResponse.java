package br.com.higia.infrastructure.evolucaoclinica.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record EvolucaoClinicaResponse(
        @JsonProperty("id") String id,
        @JsonProperty("prontuario_id") String prontuarioId,
        @JsonProperty("data_atendimento") String dataAtendimento,
        @JsonProperty("pressao_sistolica") Integer pressaoSistolica,
        @JsonProperty("pressao_diastolica") Integer pressaoDiastolica,
        @JsonProperty("glicemia") Integer glicemia,
        @JsonProperty("peso") BigDecimal peso,
        @JsonProperty("observacoes") String observacoes,
        @JsonProperty("created_at") String createdAt) {
}
