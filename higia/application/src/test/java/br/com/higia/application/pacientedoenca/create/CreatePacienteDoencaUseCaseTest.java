package br.com.higia.application.pacientedoenca.create;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreatePacienteDoencaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreatePacienteDoencaUseCase useCase;

    @Mock
    private PacienteDoencaGateway pacienteDoencaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteDoencaGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsCreatePacienteDoenca_shouldReturnId() {
        final var expectedPacienteId = PacienteID.unique().getValue();
        final var expectedDoencaId = DoencaCronicaID.unique().getValue();
        final var expectedDataDiagnostico = "01/01/2020";

        final var command = CreatePacienteDoencaCommand.with(
                expectedPacienteId,
                expectedDoencaId,
                expectedDataDiagnostico);

        when(pacienteDoencaGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(pacienteDoencaGateway).create(
                argThat(pacienteDoenca -> pacienteDoenca.getPacienteId().getValue().equals(expectedPacienteId) &&
                        pacienteDoenca.getDoencaCronicaId().getValue().equals(expectedDoencaId) &&
                        LocalDate.of(2020, 1, 1).equals(pacienteDoenca.getDataDiagnostico())));
    }
}
