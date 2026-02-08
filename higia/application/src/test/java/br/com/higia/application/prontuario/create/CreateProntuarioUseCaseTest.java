package br.com.higia.application.prontuario.create;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateProntuarioUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateProntuarioUseCase useCase;

    @Mock
    private ProntuarioGateway prontuarioGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                prontuarioGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsCreateProntuario_shouldReturnId() {
        final var expectedPacienteId = PacienteID.unique().getValue();
        final var command = CreateProntuarioCommand.with(expectedPacienteId);

        when(prontuarioGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(prontuarioGateway)
                .create(argThat(prontuario -> prontuario.getPacienteId().getValue().equals(expectedPacienteId) &&
                        prontuario.isAtivo()));
    }
}
