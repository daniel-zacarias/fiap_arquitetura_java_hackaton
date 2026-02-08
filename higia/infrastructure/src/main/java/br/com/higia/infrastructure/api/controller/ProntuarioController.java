package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.prontuario.create.CreateProntuarioCommand;
import br.com.higia.application.prontuario.create.CreateProntuarioUseCase;
import br.com.higia.application.prontuario.retrieve.get.GetProntuarioAtivoByPacienteIdUseCase;
import br.com.higia.application.prontuario.retrieve.get.GetProntuarioByIdUseCase;
import br.com.higia.application.prontuario.retrieve.list.ListProntuariosUseCase;
import br.com.higia.application.prontuario.update.UpdateProntuarioCommand;
import br.com.higia.application.prontuario.update.UpdateProntuarioUseCase;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.ProntuarioSearchQuery;
import br.com.higia.infrastructure.api.ProntuarioAPI;
import br.com.higia.infrastructure.prontuario.models.CreateProntuarioRequest;
import br.com.higia.infrastructure.prontuario.models.ProntuarioListResponse;
import br.com.higia.infrastructure.prontuario.models.ProntuarioResponse;
import br.com.higia.infrastructure.prontuario.models.UpdateProntuarioRequest;
import br.com.higia.infrastructure.prontuario.presenters.ProntuarioAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class ProntuarioController implements ProntuarioAPI {

    private final CreateProntuarioUseCase createProntuarioUseCase;
    private final UpdateProntuarioUseCase updateProntuarioUseCase;
    private final GetProntuarioByIdUseCase getProntuarioByIdUseCase;
    private final GetProntuarioAtivoByPacienteIdUseCase getProntuarioAtivoByPacienteIdUseCase;
    private final ListProntuariosUseCase listProntuariosUseCase;

    public ProntuarioController(
            final CreateProntuarioUseCase createProntuarioUseCase,
            final UpdateProntuarioUseCase updateProntuarioUseCase,
            final GetProntuarioByIdUseCase getProntuarioByIdUseCase,
            final GetProntuarioAtivoByPacienteIdUseCase getProntuarioAtivoByPacienteIdUseCase,
            final ListProntuariosUseCase listProntuariosUseCase) {
        this.createProntuarioUseCase = Objects.requireNonNull(createProntuarioUseCase);
        this.updateProntuarioUseCase = Objects.requireNonNull(updateProntuarioUseCase);
        this.getProntuarioByIdUseCase = Objects.requireNonNull(getProntuarioByIdUseCase);
        this.getProntuarioAtivoByPacienteIdUseCase = Objects.requireNonNull(getProntuarioAtivoByPacienteIdUseCase);
        this.listProntuariosUseCase = Objects.requireNonNull(listProntuariosUseCase);
    }

    @Override
    public ResponseEntity<?> create(final CreateProntuarioRequest request) {
        final var command = CreateProntuarioCommand.with(request.pacienteId());
        final var output = createProntuarioUseCase.execute(command);
        return ResponseEntity.created(URI.create("/prontuarios/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<ProntuarioResponse> getById(final String id) {
        final var output = getProntuarioByIdUseCase.execute(id);
        return ResponseEntity.ok(ProntuarioAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<ProntuarioResponse> getAtivoByPacienteId(final String pacienteId) {
        final var output = getProntuarioAtivoByPacienteIdUseCase.execute(pacienteId);
        return ResponseEntity.ok(ProntuarioAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<?> updateBy(final String id, final UpdateProntuarioRequest request) {
        final var command = UpdateProntuarioCommand.with(id, request.ativo());
        final var output = updateProntuarioUseCase.execute(command);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<Pagination<ProntuarioListResponse>> list(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction,
            final String pacienteId,
            final Boolean ativo) {
        final var pacienteFilter = (pacienteId == null || pacienteId.isBlank())
                ? null
                : PacienteID.from(pacienteId);

        final var query = new ProntuarioSearchQuery(
                page,
                perPage,
                terms,
                sort,
                direction,
                pacienteFilter,
                ativo);

        return ResponseEntity.ok(
                listProntuariosUseCase.execute(query)
                        .map(ProntuarioAPIPresenter::present));
    }
}
