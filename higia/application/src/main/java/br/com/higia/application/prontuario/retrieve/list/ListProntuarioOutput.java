package br.com.higia.application.prontuario.retrieve.list;

import br.com.higia.domain.prontuario.Prontuario;

public record ListProntuarioOutput(
        String id,
        String pacienteId,
        boolean ativo,
        String createdAt,
        String updatedAt) {
    public static ListProntuarioOutput from(final Prontuario prontuario) {
        return new ListProntuarioOutput(
                prontuario.getId().getValue(),
                prontuario.getPacienteId().getValue(),
                prontuario.isAtivo(),
                prontuario.getCreatedAt() != null ? prontuario.getCreatedAt().toString() : null,
                prontuario.getUpdatedAt() != null ? prontuario.getUpdatedAt().toString() : null);
    }
}
