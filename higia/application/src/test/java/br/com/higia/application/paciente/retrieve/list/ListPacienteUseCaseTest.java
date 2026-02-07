package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.pagination.Pagination;
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
                "550e8400-e29b-41d4-a716-446655440000",
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "39922782049",
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                Instant.now(),
                Instant.now());

        final var paciente2 = Paciente.with(
                "550e8400-e29b-41d4-a716-446655440001",
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

        final var input = ListPacientesInput.with(
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
        assertEquals(2, actualOutput.items().size());

        final var firstItem = actualOutput.items().get(0);
        assertEquals("550e8400-e29b-41d4-a716-446655440000", firstItem.id());
        assertEquals("João da Silva", firstItem.nome());
        assertEquals("39922782049", firstItem.cpf());
        assertEquals("Brasileiro", firstItem.nacionalidade());

        final var secondItem = actualOutput.items().get(1);
        assertEquals("550e8400-e29b-41d4-a716-446655440001", secondItem.id());
        assertEquals("Maria Souza", secondItem.nome());
        assertEquals("17048198060", secondItem.cpf());
        assertEquals("Brasileira", secondItem.nacionalidade());

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

        final var input = ListPacientesInput.with(
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

        final var input = ListPacientesInput.with(
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
