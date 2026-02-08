package br.com.higia.infrastructure.pacientedoenca.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UpdatePacienteDoencaRequest(
        @JsonProperty("data_diagnostico")
        String dataDiagnostico,
        @JsonProperty("ativo") Boolean ativo){
}
