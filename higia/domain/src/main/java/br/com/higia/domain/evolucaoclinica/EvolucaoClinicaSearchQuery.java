package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.prontuario.ProntuarioID;

public record EvolucaoClinicaSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        ProntuarioID prontuarioId) {
}
