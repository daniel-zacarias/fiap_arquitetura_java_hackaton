package br.com.higia.infrastructure.api;

import br.com.higia.infrastructure.doencacronica.models.DoencaCronicaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Doencas Cronicas", description = "Endpoints para consultar doencas cronicas")
@RequestMapping(value = "/doencas-cronicas")
public interface DoencaCronicaAPI {

    @GetMapping("/{id}")
    @Operation(summary = "Buscar doenca cronica por ID", description = "Retorna os dados de uma doenca cronica especifica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doenca cronica encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoencaCronicaResponse.class))),
            @ApiResponse(responseCode = "404", description = "Doenca cronica nao encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    ResponseEntity<DoencaCronicaResponse> getById(
            @PathVariable(name = "id") String id);
}
