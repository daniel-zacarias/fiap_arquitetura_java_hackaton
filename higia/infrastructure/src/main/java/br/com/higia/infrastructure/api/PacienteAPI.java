package br.com.higia.infrastructure.api;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.infrastructure.paciente.models.CreatePacienteRequest;
import br.com.higia.infrastructure.paciente.models.PacienteListResponse;
import br.com.higia.infrastructure.paciente.models.PacienteResponse;
import br.com.higia.infrastructure.paciente.models.UpdatePacienteRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pacientes", description = "Endpoints para gerenciar pacientes")
@RequestMapping(value = "/pacientes")
public interface PacienteAPI {

    @PostMapping
    @Operation(
            summary = "Criar um novo paciente",
            description = "Cria um novo paciente com os dados fornecidos"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> create(
            @RequestBody CreatePacienteRequest request
    );

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar paciente por ID",
            description = "Retorna os dados de um paciente específico pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<PacienteResponse> getById(
            @PathVariable(name = "id") String id
    );

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar paciente",
            description = "Atualiza os dados de um paciente existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteResponse.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> updateBy(
            @PathVariable(name = "id") String id,
            @RequestBody UpdatePacienteRequest request
    );

    @GetMapping
    @Operation(
            summary = "Listar pacientes",
            description = "Retorna uma lista paginada de pacientes"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Pagination<PacienteListResponse>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "terms", required = false, defaultValue = "") String terms,
            @RequestParam(name = "sort", required = false, defaultValue = "nome") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    );
}
