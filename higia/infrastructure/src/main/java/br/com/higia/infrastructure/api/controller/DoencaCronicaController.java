package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.doencacronica.retrieve.get.GetDoencaCronicaByIdUseCase;
import br.com.higia.infrastructure.api.DoencaCronicaAPI;
import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaResponse;
import br.com.higia.infrastructure.doencacronica.presenters.DoencaCronicaAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class DoencaCronicaController implements DoencaCronicaAPI {

    private final GetDoencaCronicaByIdUseCase getDoencaCronicaByIdUseCase;

    public DoencaCronicaController(final GetDoencaCronicaByIdUseCase getDoencaCronicaByIdUseCase) {
        this.getDoencaCronicaByIdUseCase = Objects.requireNonNull(getDoencaCronicaByIdUseCase);
    }

    @Override
    public ResponseEntity<DoencaCronicaResponse> getById(String id) {
        final var output = getDoencaCronicaByIdUseCase.execute(id);
        return ResponseEntity.ok(DoencaCronicaAPIPresenter.present(output));
    }
}
