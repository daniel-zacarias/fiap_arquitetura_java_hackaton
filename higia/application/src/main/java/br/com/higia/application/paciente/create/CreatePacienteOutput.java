package br.com.higia.application.paciente.create;

import br.com.higia.domain.paciente.Paciente;

public record CreatePacienteOutput(
        String id
) {
        public static CreatePacienteOutput from(final String id) {
            return new CreatePacienteOutput(id);
        }

        public static CreatePacienteOutput from(final Paciente paciente) {
            return from(paciente.getId().getValue());
        }
}
