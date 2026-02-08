package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.pacientedoenca.create.CreatePacienteDoencaCommand;
import br.com.higia.application.pacientedoenca.create.CreatePacienteDoencaUseCase;
import br.com.higia.application.pacientedoenca.retrieve.get.GetPacienteDoencaByIdUseCase;
import br.com.higia.application.pacientedoenca.retrieve.list.ListPacienteDoencasUseCase;
import br.com.higia.application.pacientedoenca.update.UpdatePacienteDoencaCommand;
import br.com.higia.application.pacientedoenca.update.UpdatePacienteDoencaUseCase;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pacientedoenca.PacienteDoencaSearchQuery;
import br.com.higia.infrastructure.api.PacienteDoencaAPI;
import br.com.higia.infrastructure.pacientedoenca.models.CreatePacienteDoencaRequest;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaListResponse;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaResponse;
import br.com.higia.infrastructure.pacientedoenca.models.UpdatePacienteDoencaRequest;
import br.com.higia.infrastructure.pacientedoenca.presenters.PacienteDoencaAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class PacienteDoencaController implements PacienteDoencaAPI {

    private final CreatePacienteDoencaUseCase createPacienteDoencaUseCase;
    private final UpdatePacienteDoencaUseCase updatePacienteDoencaUseCase;
    private final GetPacienteDoencaByIdUseCase getPacienteDoencaByIdUseCase;
    private final ListPacienteDoencasUseCase listPacienteDoencasUseCase;

    public PacienteDoencaController(
            final CreatePacienteDoencaUseCase createPacienteDoencaUseCase,
            final UpdatePacienteDoencaUseCase updatePacienteDoencaUseCase,
            final GetPacienteDoencaByIdUseCase getPacienteDoencaByIdUseCase,
            final ListPacienteDoencasUseCase listPacienteDoencasUseCase) {
        this.createPacienteDoencaUseCase = Objects.requireNonNull(createPacienteDoencaUseCase);
        this.updatePacienteDoencaUseCase = Objects.requireNonNull(updatePacienteDoencaUseCase);
        this.getPacienteDoencaByIdUseCase = Objects.requireNonNull(getPacienteDoencaByIdUseCase);
        this.listPacienteDoencasUseCase = Objects.requireNonNull(listPacienteDoencasUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreatePacienteDoencaRequest request) {
        final var command = CreatePacienteDoencaCommand.with(
                request.pacienteId(),
                request.doencaCronicaId(),
                request.dataDiagnostico());

        final var output = createPacienteDoencaUseCase.execute(command);
        return ResponseEntity.created(URI.create("/paciente-doencas/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<PacienteDoencaResponse> getById(final String id) {
        final var output = getPacienteDoencaByIdUseCase.execute(id);
        return ResponseEntity.ok(PacienteDoencaAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<?> updateBy(final String id, final UpdatePacienteDoencaRequest request) {
        final var command = UpdatePacienteDoencaCommand.with(id, request.dataDiagnostico(), request.ativo());
        final var output = updatePacienteDoencaUseCase.execute(command);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<Pagination<PacienteDoencaListResponse>> list(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction,
            final String pacienteId,
            final String doencaCronicaId) {
        final var pacienteFilter = (pacienteId == null || pacienteId.isBlank())
                ? null
                : PacienteID.from(pacienteId);
        final var doencaFilter = (doencaCronicaId == null || doencaCronicaId.isBlank())
                ? null
                : DoencaCronicaID.from(doencaCronicaId);

        final var query = new PacienteDoencaSearchQuery(
                page,
                perPage,
                terms,
                sort,
                direction,
                pacienteFilter,
                doencaFilter);

        return ResponseEntity.ok(
                listPacienteDoencasUseCase.execute(query)
                        .map(PacienteDoencaAPIPresenter::present));
    }
}
