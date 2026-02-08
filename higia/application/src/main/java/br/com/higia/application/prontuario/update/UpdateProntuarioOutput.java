package br.com.higia.application.prontuario.update;

import br.com.higia.domain.prontuario.Prontuario;

public record UpdateProntuarioOutput(String id) {
    public static UpdateProntuarioOutput from(final String id) {
        return new UpdateProntuarioOutput(id);
    }

    public static UpdateProntuarioOutput from(final Prontuario prontuario) {
        return from(prontuario.getId().getValue());
    }
}

