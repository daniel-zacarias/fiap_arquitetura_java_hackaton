package br.com.higia.application.pacientedoenca.update;

import br.com.higia.domain.pacientedoenca.PacienteDoenca;

public record UpdatePacienteDoencaOutput(String id) {
    public static UpdatePacienteDoencaOutput from(final String id) {
        return new UpdatePacienteDoencaOutput(id);
    }

    public static UpdatePacienteDoencaOutput from(final PacienteDoenca pacienteDoenca) {
        return from(pacienteDoenca.getId().getValue());
    }
}
