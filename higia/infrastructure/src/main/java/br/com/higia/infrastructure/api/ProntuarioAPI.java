package br.com.higia.infrastructure.api;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.infrastructure.prontuario.models.CreateProntuarioRequest;
import br.com.higia.infrastructure.prontuario.models.ProntuarioListResponse;
import br.com.higia.infrastructure.prontuario.models.ProntuarioResponse;
import br.com.higia.infrastructure.prontuario.models.UpdateProntuarioRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Prontuarios", description = "Endpoints para gerenciar prontuarios")
@RequestMapping(value = "/prontuarios")
public interface ProntuarioAPI {

    @PostMapping
    @Operation(summary = "Criar prontuario", description = "Cria um prontuario para um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prontuario criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProntuarioResponse.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> create(
            @RequestBody CreateProntuarioRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar prontuario por ID", description = "Retorna os dados de um prontuario especifico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProntuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prontuario nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<ProntuarioResponse> getById(
            @PathVariable(name = "id") String id);

    @GetMapping("/ativo/{pacienteId}")
    @Operation(summary = "Buscar prontuario ativo por paciente", description = "Retorna o prontuario ativo do paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProntuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prontuario nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<ProntuarioResponse> getAtivoByPacienteId(
            @PathVariable(name = "pacienteId") String pacienteId);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prontuario", description = "Atualiza o status de um prontuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuario atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProntuarioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Prontuario nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> updateBy(
            @PathVariable(name = "id") String id,
            @RequestBody UpdateProntuarioRequest request);

    @GetMapping
    @Operation(summary = "Listar prontuarios", description = "Retorna uma lista paginada de prontuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prontuarios retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProntuarioListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Pagination<ProntuarioListResponse>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "terms", required = false, defaultValue = "") String terms,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "paciente_id", required = false) String pacienteId,
            @RequestParam(name = "ativo", required = false) Boolean ativo);
}
