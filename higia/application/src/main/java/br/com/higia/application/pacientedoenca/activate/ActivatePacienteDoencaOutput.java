package br.com.higia.application.pacientedoenca.activate;

import br.com.higia.domain.pacientedoenca.PacienteDoenca;

public record ActivatePacienteDoencaOutput(String id) {
    public static ActivatePacienteDoencaOutput from(final String id) {
        return new ActivatePacienteDoencaOutput(id);
    }

    public static ActivatePacienteDoencaOutput from(final PacienteDoenca pacienteDoenca) {
        return from(pacienteDoenca.getId().getValue());
    }
}
