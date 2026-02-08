package br.com.higia.infrastructure.pacientedoenca.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PacienteDoencaResponse(
        @JsonProperty("id") String id,
        @JsonProperty("paciente_id") String pacienteId,
        @JsonProperty("doenca_cronica_id") String doencaCronicaId,
        @JsonProperty("data_diagnostico") String dataDiagnostico,
        @JsonProperty("ativo") boolean ativo,
        @JsonProperty("created_at") String createdAt) {
}
