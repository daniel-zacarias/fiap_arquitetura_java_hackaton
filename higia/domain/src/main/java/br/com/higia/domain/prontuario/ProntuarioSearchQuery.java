package br.com.higia.domain.prontuario;

import br.com.higia.domain.paciente.PacienteID;

public record ProntuarioSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        PacienteID pacienteId,
        Boolean ativo) {
}
