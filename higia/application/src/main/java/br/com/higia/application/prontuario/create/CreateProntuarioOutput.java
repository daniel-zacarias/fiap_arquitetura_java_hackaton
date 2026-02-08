package br.com.higia.application.prontuario.create;

import br.com.higia.domain.prontuario.Prontuario;

public record CreateProntuarioOutput(String id) {
    public static CreateProntuarioOutput from(final String id) {
        return new CreateProntuarioOutput(id);
    }

    public static CreateProntuarioOutput from(final Prontuario prontuario) {
        return from(prontuario.getId().getValue());
    }
}
