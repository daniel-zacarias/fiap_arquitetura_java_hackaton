package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListPacienteUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListPacientesUseCase useCase;

    @Mock
    private PacienteGateway pacienteGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteGateway
        };
    }

    @Test
    public void givenAValidInput_whenCallsListPacientes_shouldReturnListWithItems() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 2;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";


        final var paciente1 = Paciente.with(
                PacienteID.unique().getValue(),
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "39922782049",
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                Instant.now(),
                Instant.now());

        final var paciente2 = Paciente.with(
                PacienteID.unique().getValue(),
                "Maria Souza",
                LocalDate.of(1985, 5, 15),
                "17048198060",
                "Brasileira",
                "98765-432",
                "Rua da Paz, 456",
                Instant.now(),
                Instant.now());

        final var pagination = new Pagination<>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.of(paciente1, paciente2));

        when(pacienteGateway.findAll(any()))
                .thenReturn(pagination);

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        // when
        final var actualOutput = useCase.execute(input);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(2, actualOutput.items().size());

        final var firstItem = actualOutput.items().get(0);
        assertEquals(paciente1.getId().getValue(), firstItem.id());
        assertEquals(paciente1.getNome(), firstItem.nome());
        assertEquals(paciente1.getCpf().getValue(), firstItem.cpf());
        assertEquals(paciente1.getNacionalidade(), firstItem.nacionalidade());

        final var secondItem = actualOutput.items().get(1);
        assertEquals(paciente2.getId().getValue(), secondItem.id());
        assertEquals(paciente2.getNome(), secondItem.nome());
        assertEquals(paciente2.getCpf().getValue(), secondItem.cpf());
        assertEquals(paciente2.getNacionalidade(), secondItem.nacionalidade());


        verify(pacienteGateway).findAll(any());
    }

    @Test
    public void givenAValidInput_whenCallsListPacientes_shouldReturnEmptyList() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTotal = 0;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";

        final var pagination = new Pagination<Paciente>(
                expectedPage,
                expectedPerPage,
                expectedTotal,
                List.of());

        when(pacienteGateway.findAll(any()))
                .thenReturn(pagination);

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualOutput = useCase.execute(input);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedTotal, actualOutput.total());
        assertEquals(0, actualOutput.items().size());
        assertTrue(actualOutput.items().isEmpty());

        verify(pacienteGateway).findAll(any());
    }

    @Test
    public void givenAnInvalidInput_whenCallsListPacientes_shouldThrowException() {
        // given
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "nome";
        final var expectedDirection = "asc";

        when(pacienteGateway.findAll(any()))
                .thenThrow(new RuntimeException("Database connection error"));

        final var input = new SearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection);

        // when
        final var actualException = assertThrows(
                RuntimeException.class, () -> useCase.execute(input));

        // then
        assertNotNull(actualException);
        assertEquals("Database connection error", actualException.getMessage());

        verify(pacienteGateway).findAll(any());
    }
}
