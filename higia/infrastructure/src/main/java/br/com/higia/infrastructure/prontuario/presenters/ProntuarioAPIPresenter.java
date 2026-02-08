package br.com.higia.infrastructure.prontuario.presenters;

import br.com.higia.application.prontuario.retrieve.get.ProntuarioOutput;
import br.com.higia.application.prontuario.retrieve.list.ListProntuarioOutput;
import br.com.higia.infrastructure.prontuario.models.ProntuarioListResponse;
import br.com.higia.infrastructure.prontuario.models.ProntuarioResponse;

public interface ProntuarioAPIPresenter {

    static ProntuarioResponse present(final ProntuarioOutput output) {
        return new ProntuarioResponse(
                output.id(),
                output.pacienteId(),
                output.ativo(),
                output.createdAt(),
                output.updatedAt());
    }

    static ProntuarioListResponse present(final ListProntuarioOutput output) {
        return new ProntuarioListResponse(
                output.id(),
                output.pacienteId(),
                output.ativo(),
                output.createdAt(),
                output.updatedAt());
    }
}
