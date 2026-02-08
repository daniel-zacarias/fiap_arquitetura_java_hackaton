package br.com.higia.application.prontuario.retrieve.get;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
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

public class GetProntuarioAtivoByPacienteIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetProntuarioAtivoByPacienteIdUseCase useCase;

    @Mock
    private ProntuarioGateway prontuarioGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                prontuarioGateway
        };
    }

    @Test
    public void givenAValidPacienteId_whenCallsGetProntuarioAtivoByPacienteId_shouldReturnProntuario() {
        final var expectedPacienteId = PacienteID.unique();
        final var expectedId = br.com.higia.domain.prontuario.ProntuarioID.unique().getValue();
        final var expectedCreatedAt = Instant.parse("2024-01-01T10:00:00Z");

        final var prontuario = Prontuario.with(
                expectedId,
                expectedPacienteId,
                true,
                expectedCreatedAt,
                expectedCreatedAt,
                java.util.List.of());

        when(prontuarioGateway.findAtivoByPacienteId(expectedPacienteId))
                .thenReturn(Optional.of(prontuario));

        final var actualOutput = useCase.execute(expectedPacienteId.getValue());

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedPacienteId.getValue(), actualOutput.pacienteId());

        verify(prontuarioGateway)
                .findAtivoByPacienteId(argThat(id -> id.getValue().equals(expectedPacienteId.getValue())));
    }

    @Test
    public void givenAnInvalidPacienteId_whenCallsGetProntuarioAtivoByPacienteId_shouldReturnNotFoundException() {
        final var expectedPacienteId = PacienteID.unique().getValue();

        when(prontuarioGateway.findAtivoByPacienteId(PacienteID.from(expectedPacienteId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedPacienteId));

        assertNotNull(actualException);
        assertTrue(
                actualException.getMessage().contains("Prontuario with ID " + expectedPacienteId + " was not found"));

        verify(prontuarioGateway).findAtivoByPacienteId(argThat(id -> id.getValue().equals(expectedPacienteId)));
    }
}
