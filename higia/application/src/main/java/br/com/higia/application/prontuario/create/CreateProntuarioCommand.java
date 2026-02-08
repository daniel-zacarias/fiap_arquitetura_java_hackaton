package br.com.higia.application.prontuario.create;

public record CreateProntuarioCommand(String pacienteId) {
    public static CreateProntuarioCommand with(final String pacienteId) {
        return new CreateProntuarioCommand(pacienteId);
    }
}
