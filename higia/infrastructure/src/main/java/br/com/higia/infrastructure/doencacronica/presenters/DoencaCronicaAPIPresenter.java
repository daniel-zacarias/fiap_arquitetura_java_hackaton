package br.com.higia.infrastructure.doencacronica.presenters;

import br.com.higia.application.doencacronica.retrieve.get.DoencaCronicaOutput;
import br.com.higia.application.doencacronica.retrieve.list.ListDoencaCronicaOutput;
import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaListResponse;
import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaResponse;

public interface DoencaCronicaAPIPresenter {

    static DoencaCronicaResponse present(final DoencaCronicaOutput output) {
        return new DoencaCronicaResponse(
                output.id(),
                output.nome(),
                output.cid10(),
                output.createdAt(),
                output.updatedAt());
    }

    static DoencaCronicaListResponse present(final ListDoencaCronicaOutput output) {
        return new DoencaCronicaListResponse(
                output.id(),
                output.nome(),
                output.cid10(),
                output.createdAt(),
                output.updatedAt());
    }
}
