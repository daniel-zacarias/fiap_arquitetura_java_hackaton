package br.com.higia.application.pacientedoenca.update;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import br.com.higia.domain.pacientedoenca.PacienteDoencaID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdatePacienteDoencaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdatePacienteDoencaUseCase useCase;

    @Mock
    private PacienteDoencaGateway pacienteDoencaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteDoencaGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsUpdatePacienteDoenca_shouldReturnId() {
        final var expectedId = PacienteDoencaID.unique().getValue();
        final var expectedDataDiagnostico = "10/02/2021";

        final var pacienteDoenca = PacienteDoenca.with(
                expectedId,
                PacienteID.unique(),
                DoencaCronicaID.unique(),
                LocalDate.of(2020, 1, 1),
                true,
                Instant.now());

        final var command = UpdatePacienteDoencaCommand.with(
                expectedId,
                expectedDataDiagnostico);

        when(pacienteDoencaGateway.findById(PacienteDoencaID.from(expectedId)))
                .thenReturn(Optional.of(pacienteDoenca));

        when(pacienteDoencaGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());

        verify(pacienteDoencaGateway).update(argThat(updated -> updated.getId().getValue().equals(expectedId) &&
                LocalDate.of(2021, 2, 10).equals(updated.getDataDiagnostico())));
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdatePacienteDoenca_shouldReturnNotFoundException() {
        final var expectedId = PacienteDoencaID.unique().getValue();

        final var command = UpdatePacienteDoencaCommand.with(
                expectedId,
                "10/02/2021");

        when(pacienteDoencaGateway.findById(PacienteDoencaID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command));

        assertNotNull(actualException);

        verify(pacienteDoencaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
