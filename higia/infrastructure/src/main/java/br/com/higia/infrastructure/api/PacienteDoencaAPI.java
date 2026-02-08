package br.com.higia.infrastructure.api;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.infrastructure.pacientedoenca.models.CreatePacienteDoencaRequest;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaListResponse;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaResponse;
import br.com.higia.infrastructure.pacientedoenca.models.UpdatePacienteDoencaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Pacientes Doencas", description = "Endpoints para gerenciar vinculos de pacientes e doencas")
@RequestMapping(value = "/paciente-doencas")
public interface PacienteDoencaAPI {

    @PostMapping
    @Operation(summary = "Criar vinculo paciente-doenca", description = "Cria um vinculo de paciente com doenca cronica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vinculo criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDoencaResponse.class))),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> create(
            @RequestBody CreatePacienteDoencaRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Buscar vinculo por ID", description = "Retorna os dados do vinculo paciente-doenca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vinculo encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDoencaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vinculo nao encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<PacienteDoencaResponse> getById(
            @PathVariable(name = "id") String id);

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar vinculo", description = "Atualiza a data de diagnostico do vinculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vinculo atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDoencaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Vinculo nao encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validacao nos dados fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<?> updateBy(
            @PathVariable(name = "id") String id,
            @RequestBody UpdatePacienteDoencaRequest request);

    @GetMapping
    @Operation(summary = "Listar vinculos", description = "Retorna uma lista paginada de vinculos paciente-doenca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de vinculos retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PacienteDoencaListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<Pagination<PacienteDoencaListResponse>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage,
            @RequestParam(name = "terms", required = false, defaultValue = "") String terms,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "paciente_id", required = false) String pacienteId,
            @RequestParam(name = "doenca_cronica_id", required = false) String doencaCronicaId);
}
