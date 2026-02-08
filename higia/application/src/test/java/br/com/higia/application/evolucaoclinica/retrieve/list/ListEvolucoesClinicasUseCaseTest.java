package br.com.higia.application.evolucaoclinica.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaSearchQuery;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.ProntuarioID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListEvolucoesClinicasUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListEvolucoesClinicasUseCase useCase;

    @Mock
    private EvolucaoClinicaGateway evolucaoClinicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                evolucaoClinicaGateway
        };
    }

    @Test
    public void givenAValidQuery_whenCallsListEvolucoesClinicas_shouldReturnPagination() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var evolucao = EvolucaoClinica.with(
                "ec-1",
                expectedProntuarioId,
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "obs",
                Instant.parse("2024-01-01T11:00:00Z"));

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 1;

        final var query = new EvolucaoClinicaSearchQuery(
                expectedPage,
                expectedPerPage,
                "",
                "id",
                "asc",
                expectedProntuarioId);

        final var pagination = new Pagination<>(expectedPage, expectedPerPage, expectedTotal, List.of(evolucao));

        when(evolucaoClinicaGateway.findAll(query))
                .thenReturn(pagination);

        final var actualOutput = useCase.execute(query);

        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(1, actualOutput.items().size());
        assertEquals(expectedProntuarioId.getValue(), actualOutput.items().get(0).prontuarioId());

        verify(evolucaoClinicaGateway).findAll(query);
    }
}
