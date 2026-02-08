package br.com.higia.application.prontuario.update;

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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateProntuarioUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateProntuarioUseCase useCase;

    @Mock
    private ProntuarioGateway prontuarioGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                prontuarioGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateProntuario_shouldReturnId() {
        final var expectedId = ProntuarioID.unique().getValue();
        final var expectedPacienteId = PacienteID.unique();
        final var expectedPacienteIdValue = expectedPacienteId.getValue();

        final var prontuario = Prontuario.with(
                expectedId,
                expectedPacienteId,
                true,
                Instant.now().minusSeconds(60),
                Instant.now().minusSeconds(60),
                List.of());

        final var command = UpdateProntuarioCommand.with(
                expectedId,
                false);

        when(prontuarioGateway.findById(ProntuarioID.from(expectedId)))
                .thenReturn(Optional.of(prontuario));

        when(prontuarioGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());

        verify(prontuarioGateway).update(argThat(updated -> updated.getId().getValue().equals(expectedId) &&
                expectedPacienteIdValue.equals(updated.getPacienteId().getValue()) &&
                !updated.isAtivo()));
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateProntuario_shouldReturnNotFoundException() {
        final var expectedId = ProntuarioID.unique().getValue();

        final var command = UpdateProntuarioCommand.with(
                expectedId,
                false);

        when(prontuarioGateway.findById(ProntuarioID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command));

        assertNotNull(actualException);

        verify(prontuarioGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
        verify(prontuarioGateway, never()).update(any());
    }
}
