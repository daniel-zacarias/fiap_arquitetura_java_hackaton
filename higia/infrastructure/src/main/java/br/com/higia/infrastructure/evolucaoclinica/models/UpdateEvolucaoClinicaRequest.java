package br.com.higia.infrastructure.evolucaoclinica.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record UpdateEvolucaoClinicaRequest(
        @JsonProperty("data_atendimento") String dataAtendimento,
        @JsonProperty("pressao_sistolica") Integer pressaoSistolica,
        @JsonProperty("pressao_diastolica") Integer pressaoDiastolica,
        @JsonProperty("glicemia") Integer glicemia,
        @JsonProperty("peso") BigDecimal peso,
        @JsonProperty("observacao") String observacao) {
}
