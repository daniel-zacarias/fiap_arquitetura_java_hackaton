package br.com.higia.application.pacientedoenca.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaSearchQuery;
import br.com.higia.domain.pagination.Pagination;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListPacienteDoencasUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListPacienteDoencasUseCase useCase;

    @Mock
    private PacienteDoencaGateway pacienteDoencaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteDoencaGateway
        };
    }

    @Test
    public void givenAValidQuery_whenCallsListPacienteDoencas_shouldReturnPagination() {
        final var expectedPacienteId = PacienteID.unique();
        final var expectedDoencaId = DoencaCronicaID.unique();
        final var expectedCreatedAt = Instant.parse("2024-01-01T10:00:00Z");

        final var pacienteDoenca = PacienteDoenca.with(
                "pd-1",
                expectedPacienteId,
                expectedDoencaId,
                LocalDate.of(2020, 1, 1),
                true,
                expectedCreatedAt);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;

        final var query = new PacienteDoencaSearchQuery(
                expectedPage,
                expectedPerPage,
                "",
                "id",
                "asc",
                expectedPacienteId,
                expectedDoencaId);

        final var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, List.of(pacienteDoenca));

        when(pacienteDoencaGateway.findAll(query))
                .thenReturn(pagination);

        final var actualOutput = useCase.execute(query);

        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(1, actualOutput.items().size());
        assertEquals(expectedPacienteId.getValue(), actualOutput.items().get(0).pacienteId());
        assertEquals(expectedDoencaId.getValue(), actualOutput.items().get(0).doencaCronicaId());
        assertEquals("01/01/2020", actualOutput.items().get(0).dataDiagnostico());

        verify(pacienteDoencaGateway).findAll(query);
    }
}
