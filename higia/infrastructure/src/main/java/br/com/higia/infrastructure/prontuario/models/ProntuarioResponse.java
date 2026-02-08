package br.com.higia.infrastructure.prontuario.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProntuarioResponse(
        @JsonProperty("id") String id,
        @JsonProperty("paciente_id") String pacienteId,
        @JsonProperty("ativo") boolean ativo,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt) {
}
