package br.com.higia.infrastructure.api.controller;

import br.com.higia.application.paciente.create.CreatePacienteCommand;
import br.com.higia.application.paciente.create.CreatePacienteUseCase;
import br.com.higia.application.paciente.retrieve.get.GetPacienteByIdUseCase;
import br.com.higia.application.paciente.retrieve.list.ListPacientesUseCase;
import br.com.higia.application.paciente.update.UpdatePacienteCommand;
import br.com.higia.application.paciente.update.UpdatePacienteUseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import br.com.higia.infrastructure.api.PacienteAPI;
import br.com.higia.infrastructure.paciente.models.CreatePacienteRequest;
import br.com.higia.infrastructure.paciente.models.PacienteListResponse;
import br.com.higia.infrastructure.paciente.models.PacienteResponse;
import br.com.higia.infrastructure.paciente.models.UpdatePacienteRequest;
import br.com.higia.infrastructure.paciente.presenters.PacienteAPIPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;

@RestController
public class PacienteController implements PacienteAPI {

    private final CreatePacienteUseCase createPacienteUseCase;

    private final UpdatePacienteUseCase updatePacienteUseCase;

    private final GetPacienteByIdUseCase getPacienteByIdUseCase;

    private final ListPacientesUseCase listPacientesUseCase;

    public PacienteController(
            final CreatePacienteUseCase createPacienteUseCase,
            final UpdatePacienteUseCase updatePacienteUseCase,
            final GetPacienteByIdUseCase getPacienteByIdUseCase,
            final ListPacientesUseCase listPacientesUseCase) {
        this.createPacienteUseCase = Objects.requireNonNull(createPacienteUseCase);
        this.updatePacienteUseCase = Objects.requireNonNull(updatePacienteUseCase);
        this.getPacienteByIdUseCase = Objects.requireNonNull(getPacienteByIdUseCase);
        this.listPacientesUseCase = Objects.requireNonNull(listPacientesUseCase);
    }

    @Override
    public ResponseEntity<?> create(CreatePacienteRequest request) {
        final var aCommand = CreatePacienteCommand.with(
                request.nome(),
                request.cpf(),
                request.dataNascimento(),
                request.nacionalidade(),
                request.cep(),
                request.endereco());

        final var output = createPacienteUseCase.execute(aCommand);
        return ResponseEntity.created(URI.create("/pacientes/" + output.id())).body(output);
    }

    @Override
    public ResponseEntity<PacienteResponse> getById(String id) {
            final var output = getPacienteByIdUseCase.execute(id);
            return ResponseEntity.ok(PacienteAPIPresenter.present(output));
    }

    @Override
    public ResponseEntity<?> updateBy(String id, UpdatePacienteRequest request) {

        final var aCommand = UpdatePacienteCommand.with(
                id,
                request.nome(),
                request.dataNascimento(),
                request.cpf(),
                request.nacionalidade(),
                request.cep(),
                request.endereco());

        final var output = updatePacienteUseCase.execute(aCommand);
        return ResponseEntity.ok(output);
    }

    @Override
    public ResponseEntity<Pagination<PacienteListResponse>> list(int page, int perPage, String terms, String sort, String direction) {
        final var aCommand = new SearchQuery(page, perPage, terms, sort, direction);
        return ResponseEntity.ok(
                listPacientesUseCase.execute(aCommand)
                        .map(PacienteAPIPresenter::present)
        );
    }
}
