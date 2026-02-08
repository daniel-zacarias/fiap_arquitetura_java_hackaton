package br.com.higia.infrastructure.api;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.infrastructure.evolucaoclinica.models.CreateEvolucaoClinicaRequest;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaListResponse;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaResponse;
import br.com.higia.infrastructure.evolucaoclinica.models.UpdateEvolucaoClinicaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Evolucoes Clinicas", description = "Endpoints para gerenciar evolucoes clinicas")
@RequestMapping(value = "/evolucoes-clinicas")
public interface EvolucaoClinicaAPI {

    @PostMapping
    @Operation(summary = "Criar evolucao clinica", description = "Cria uma evolucao clinica para um prontuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evolucao clinica criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> create(
            @RequestBody CreateEvolucaoClinicaRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evolucao clinica por ID", description = "Retorna os dados de uma evolucao clinica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evolucao clinica encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvolucaoClinicaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Evolucao clinica nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<EvolucaoClinicaResponse> getById(
            @PathVariable(name = "id") String id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evolucao clinica", description = "Atualiza uma evolucao clinica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evolucao clinica atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EvolucaoClinicaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Evolucao clinica nao encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> updateBy(
            @PathVariable(name = "id") String id,
            @RequestBody UpdateEvolucaoClinicaRequest request);

    @GetMapping
    @Operation(summary = "Listar evolucoes clinicas", description = "Retorna uma lista paginada de evolucoes clinicas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de evolucoes clinicas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Pagination<EvolucaoClinicaListResponse>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "terms", required = false, defaultValue = "") String terms,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "prontuario_id", required = false) String prontuarioId);
}
