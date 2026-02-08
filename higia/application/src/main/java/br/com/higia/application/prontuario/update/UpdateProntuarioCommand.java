package br.com.higia.application.prontuario.update;

public record UpdateProntuarioCommand(
        String id,
        Boolean ativo) {
    public static UpdateProntuarioCommand with(
            final String id,
            final Boolean ativo) {
        return new UpdateProntuarioCommand(id, ativo);
    }
}

