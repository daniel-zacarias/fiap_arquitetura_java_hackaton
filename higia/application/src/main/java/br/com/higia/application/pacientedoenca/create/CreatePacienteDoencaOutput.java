package br.com.higia.application.pacientedoenca.create;

import br.com.higia.domain.pacientedoenca.PacienteDoenca;

public record CreatePacienteDoencaOutput(String id) {
    public static CreatePacienteDoencaOutput from(final String id) {
        return new CreatePacienteDoencaOutput(id);
    }

    public static CreatePacienteDoencaOutput from(final PacienteDoenca pacienteDoenca) {
        return from(pacienteDoenca.getId().getValue());
    }
}
