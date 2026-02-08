package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.evolucaoclinica.create.CreateEvolucaoClinicaCommand;
import br.com.higia.application.evolucaoclinica.create.CreateEvolucaoClinicaUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.get.GetEvolucaoClinicaByIdUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.list.ListEvolucoesClinicasUseCase;
import br.com.higia.application.evolucaoclinica.update.UpdateEvolucaoClinicaCommand;
import br.com.higia.application.evolucaoclinica.update.UpdateEvolucaoClinicaUseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaSearchQuery;
import br.com.higia.domain.prontuario.ProntuarioID;
import br.com.higia.infrastructure.api.EvolucaoClinicaAPI;
import br.com.higia.infrastructure.evolucaoclinica.models.CreateEvolucaoClinicaRequest;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaListResponse;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaResponse;
import br.com.higia.infrastructure.evolucaoclinica.models.UpdateEvolucaoClinicaRequest;
import br.com.higia.infrastructure.evolucaoclinica.presenters.EvolucaoClinicaAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class EvolucaoClinicaController implements EvolucaoClinicaAPI {

    private final CreateEvolucaoClinicaUseCase createEvolucaoClinicaUseCase;
    private final UpdateEvolucaoClinicaUseCase updateEvolucaoClinicaUseCase;
    private final GetEvolucaoClinicaByIdUseCase getEvolucaoClinicaByIdUseCase;
    private final ListEvolucoesClinicasUseCase listEvolucoesClinicasUseCase;

    public EvolucaoClinicaController(
            final CreateEvolucaoClinicaUseCase createEvolucaoClinicaUseCase,
            final UpdateEvolucaoClinicaUseCase updateEvolucaoClinicaUseCase,
            final GetEvolucaoClinicaByIdUseCase getEvolucaoClinicaByIdUseCase,
            final ListEvolucoesClinicasUseCase listEvolucoesClinicasUseCase) {
        this.createEvolucaoClinicaUseCase = Objects.requireNonNull(createEvolucaoClinicaUseCase);
        this.updateEvolucaoClinicaUseCase = Objects.requireNonNull(updateEvolucaoClinicaUseCase);
        this.getEvolucaoClinicaByIdUseCase = Objects.requireNonNull(getEvolucaoClinicaByIdUseCase);
        this.listEvolucoesClinicasUseCase = Objects.requireNonNull(listEvolucoesClinicasUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateEvolucaoClinicaRequest request) {
        final var command = CreateEvolucaoClinicaCommand.with(
                request.prontuarioId(),
                request.dataAtendimento(),
                request.pressaoSistolica(),
                request.pressaoDiastolica(),
                request.glicemia(),
                request.peso(),
                request.observacoes());

        final var output = createEvolucaoClinicaUseCase.execute(command);
        return ResponseEntity.created(URI.create("/evolucoes-clinicas/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<EvolucaoClinicaResponse> getById(final String id) {
        final var output = getEvolucaoClinicaByIdUseCase.execute(id);
        return ResponseEntity.ok(EvolucaoClinicaAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<?> updateBy(final String id, final UpdateEvolucaoClinicaRequest request) {
        final var command = UpdateEvolucaoClinicaCommand.with(
                id,
                request.dataAtendimento(),
                request.pressaoSistolica(),
                request.pressaoDiastolica(),
                request.glicemia(),
                request.peso(),
                request.observacao());

        final var output = updateEvolucaoClinicaUseCase.execute(command);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<Pagination<EvolucaoClinicaListResponse>> list(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction,
            final String prontuarioId) {
        final var prontuarioFilter = (prontuarioId == null || prontuarioId.isBlank())
                ? null
                : ProntuarioID.from(prontuarioId);

        final var query = new EvolucaoClinicaSearchQuery(
                page,
                perPage,
                terms,
                sort,
                direction,
                prontuarioFilter);

        return ResponseEntity.ok(
                listEvolucoesClinicasUseCase.execute(query)
                        .map(EvolucaoClinicaAPIPresenter::present));
    }
}
