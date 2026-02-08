package br.com.higia.infrastructure.paciente.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePacienteRequest(
        @JsonProperty("nome") String nome,
        @JsonProperty("cpf") String cpf,
        @JsonProperty("data_nascimento") String dataNascimento,
        @JsonProperty("nacionalidade") String nacionalidade,
        @JsonProperty("cep") String cep,
        @JsonProperty("endereco") String endereco
) {

}
