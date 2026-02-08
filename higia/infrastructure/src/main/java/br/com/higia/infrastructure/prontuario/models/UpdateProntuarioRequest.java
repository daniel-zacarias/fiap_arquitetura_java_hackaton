package br.com.higia.infrastructure.prontuario.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdateProntuarioRequest(
        @JsonProperty("ativo") Boolean ativo) {
}
