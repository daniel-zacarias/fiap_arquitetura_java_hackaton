package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.doencacronica.retrieve.get.GetDoencaCronicaByIdUseCase;
import br.com.higia.application.doencacronica.retrieve.list.ListDoencasCronicasUseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import br.com.higia.infrastructure.api.DoencaCronicaAPI;
import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaListResponse;
import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaResponse;
import br.com.higia.infrastructure.doencacronica.presenters.DoencaCronicaAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DoencaCronicaController implements DoencaCronicaAPI {

    private final GetDoencaCronicaByIdUseCase getDoencaCronicaByIdUseCase;
    private final ListDoencasCronicasUseCase listDoencasCronicasUseCase;

    public DoencaCronicaController(
            final GetDoencaCronicaByIdUseCase getDoencaCronicaByIdUseCase,
            final ListDoencasCronicasUseCase listDoencasCronicasUseCase) {
        this.getDoencaCronicaByIdUseCase = Objects.requireNonNull(getDoencaCronicaByIdUseCase);
        this.listDoencasCronicasUseCase = Objects.requireNonNull(listDoencasCronicasUseCase);
    }

    @Override
    public ResponseEntity<DoencaCronicaResponse> getById(String id) {
        final var output = getDoencaCronicaByIdUseCase.execute(id);
        return ResponseEntity.ok(DoencaCronicaAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<Pagination<DoencaCronicaListResponse>> list(int page, int perPage, String terms, String sort,
            String direction) {
        final var query = new SearchQuery(page, perPage, terms, sort, direction);

        return ResponseEntity.ok(
                listDoencasCronicasUseCase.execute(query)
                        .map(DoencaCronicaAPIPresenter::present));
    }
}
