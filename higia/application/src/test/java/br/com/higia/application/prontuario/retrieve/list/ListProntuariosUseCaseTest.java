package br.com.higia.application.prontuario.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioSearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListProntuariosUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListProntuariosUseCase useCase;

    @Mock
    private ProntuarioGateway prontuarioGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                prontuarioGateway
        };
    }

    @Test
    public void givenAValidQuery_whenCallsListProntuarios_shouldReturnPagination() {
        final var expectedPacienteId = PacienteID.unique();
        final var expectedCreatedAt = Instant.parse("2024-01-01T10:00:00Z");

        final var prontuario = Prontuario.with(
                "pr-1",
                expectedPacienteId,
                true,
                expectedCreatedAt,
                expectedCreatedAt,
                List.of());

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;

        final var query = new ProntuarioSearchQuery(
                expectedPage,
                expectedPerPage,
                "",
                "id",
                "asc",
                expectedPacienteId,
                true);

        final var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, List.of(prontuario));

        when(prontuarioGateway.findAll(query))
                .thenReturn(pagination);

        final var actualOutput = useCase.execute(query);

        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(1, actualOutput.items().size());
        assertEquals(expectedPacienteId.getValue(), actualOutput.items().get(0).pacienteId());

        verify(prontuarioGateway).findAll(query);
    }
}
