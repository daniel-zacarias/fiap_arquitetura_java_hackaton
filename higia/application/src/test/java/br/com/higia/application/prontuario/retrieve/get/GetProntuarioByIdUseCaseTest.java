package br.com.higia.application.prontuario.retrieve.get;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetProntuarioByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProntuarioByIdUseCase useCase;

    @Mock
    private ProntuarioGateway prontuarioGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                prontuarioGateway
        };
    }

    @Test
    public void givenAValidId_whenCallsGetProntuarioById_shouldReturnProntuario() {
        final var expectedId = ProntuarioID.unique().getValue();
        final var expectedPacienteId = PacienteID.unique();
        final var expectedCreatedAt = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedUpdatedAt = Instant.parse("2024-01-02T10:00:00Z");

        final var prontuario = Prontuario.with(
                expectedId,
                expectedPacienteId,
                true,
                expectedCreatedAt,
                expectedUpdatedAt,
                java.util.List.of());

        when(prontuarioGateway.findById(ProntuarioID.from(expectedId)))
                .thenReturn(Optional.of(prontuario));

        final var actualOutput = useCase.execute(expectedId);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedPacienteId.getValue(), actualOutput.pacienteId());
        assertEquals(expectedCreatedAt.toString(), actualOutput.createdAt());
        assertEquals(expectedUpdatedAt.toString(), actualOutput.updatedAt());

        verify(prontuarioGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetProntuarioById_shouldReturnNotFoundException() {
        final var expectedId = ProntuarioID.unique().getValue();

        when(prontuarioGateway.findById(ProntuarioID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId));

        assertNotNull(actualException);
        assertTrue(actualException.getMessage().contains("Prontuario with ID " + expectedId + " was not found"));

        verify(prontuarioGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
