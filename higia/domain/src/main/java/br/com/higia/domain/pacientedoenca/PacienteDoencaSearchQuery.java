package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;

public record PacienteDoencaSearchQuery(
                int page,
                int perPage,
                String terms,
                String sort,
                String direction,
                PacienteID pacienteId,
                DoencaCronicaID doencaCronicaId) {
}
