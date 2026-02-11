package br.com.higia.application.doencacronica.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.doencacronica.DoencaCronica;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListDoencasCronicasUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListDoencasCronicasUseCase useCase;

    @Mock
    private DoencaCronicaGateway doencaCronicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                doencaCronicaGateway
        };
    }

    @Test
    public void givenAValidInput_whenCallsListDoencasCronicas_shouldReturnListWithItems() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 2;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";

        final var doenca1 = DoencaCronica.with(
                "dc-1",
                "Diabetes Mellitus",
                "E10",
                Instant.now(),
                Instant.now());

        final var doenca2 = DoencaCronica.with(
                "dc-2",
                "Hipertensao",
                "I10",
                Instant.now(),
                Instant.now());

        final var pagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.of(doenca1, doenca2));

        when(doencaCronicaGateway.findAll(any()))
                .thenReturn(pagination);

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        final var actualOutput = useCase.execute(input);

        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(2, actualOutput.items().size());

        final var firstItem = actualOutput.items().get(0);
        assertEquals(doenca1.getId().getValue(), firstItem.id());
        assertEquals(doenca1.getNome(), firstItem.nome());
        assertEquals(doenca1.getCid10(), firstItem.cid10());

        final var secondItem = actualOutput.items().get(1);
        assertEquals(doenca2.getId().getValue(), secondItem.id());
        assertEquals(doenca2.getNome(), secondItem.nome());
        assertEquals(doenca2.getCid10(), secondItem.cid10());

        verify(doencaCronicaGateway).findAll(any());
    }

    @Test
    public void givenAValidInput_whenCallsListDoencasCronicas_shouldReturnEmptyList() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 0;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";

        final var pagination = new Pagination<DoencaCronica>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.of());

        when(doencaCronicaGateway.findAll(any()))
                .thenReturn(pagination);

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        final var actualOutput = useCase.execute(input);

        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(0, actualOutput.items().size());
        assertTrue(actualOutput.items().isEmpty());

        verify(doencaCronicaGateway).findAll(any());
    }

    @Test
    public void givenAnInvalidInput_whenCallsListDoencasCronicas_shouldThrowException() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";

        when(doencaCronicaGateway.findAll(any()))
                .thenThrow(new RuntimeException("Database connection error"));

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        final var actualException = assertThrows(
                RuntimeException.class, () -> useCase.execute(input));

        assertNotNull(actualException);
        assertEquals("Database connection error", actualException.getMessage());

        verify(doencaCronicaGateway).findAll(any());
    }
}
