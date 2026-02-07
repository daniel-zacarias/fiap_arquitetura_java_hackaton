package br.com.higia.application.paciente.update;

import br.com.higia.domain.paciente.Paciente;

public record UpdatePacienteOutput(
        String id) {
    public static UpdatePacienteOutput from(final String id) {
        return new UpdatePacienteOutput(id);
    }

    public static UpdatePacienteOutput from(final Paciente paciente) {
        return from(paciente.getId().getValue());
    }
}
