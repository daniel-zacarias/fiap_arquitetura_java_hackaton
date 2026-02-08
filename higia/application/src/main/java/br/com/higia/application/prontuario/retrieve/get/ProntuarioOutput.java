package br.com.higia.application.prontuario.retrieve.get;

import br.com.higia.domain.prontuario.Prontuario;

public record ProntuarioOutput(
        String id,
        String pacienteId,
        boolean ativo,
        String createdAt,
        String updatedAt
) {
    public static ProntuarioOutput from(final Prontuario prontuario) {
        return new ProntuarioOutput(
                prontuario.getId().getValue(),
                prontuario.getPacienteId().getValue(),
                prontuario.isAtivo(),
                prontuario.getCreatedAt() != null ? prontuario.getCreatedAt().toString() : null,
                prontuario.getUpdatedAt() != null ? prontuario.getUpdatedAt().toString() : null
        );
    }
}
