package br.com.higia.infrastructure.prontuario.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateProntuarioRequest(
        @JsonProperty("paciente_id") String pacienteId) {
}
