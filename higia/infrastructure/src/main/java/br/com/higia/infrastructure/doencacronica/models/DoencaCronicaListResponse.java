package br.com.higia.infrastructure.doencacronica.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DoencaCronicaListResponse(
        @JsonProperty("id") String id,
        @JsonProperty("nome") String nome,
        @JsonProperty("cid10") String cid10,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("updated_at") String updatedAt) {

}
