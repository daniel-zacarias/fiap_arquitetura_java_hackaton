package br.com.higia.application.paciente.create;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.paciente.PacienteGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class CreatePacienteUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreatePacienteUseCase useCase;

    @Mock
    private PacienteGateway pacienteGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsCreatePaciente_shouldReturnPacienteId() {
        // given
        final var expectedName = "João da Silva";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalide = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores, 123";

        final var command = CreatePacienteCommand.with(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalide,
                expectedCep,
                expectedEndereco
        );

        when(pacienteGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(command);

        // then
        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(pacienteGateway).create(argThat(paciente ->
                paciente.getNome().equals(expectedName) &&
                paciente.getCpf().getValue().equals(expectedCpf) &&
                paciente.getNacionalidade().equals(expectedNacionalide) &&
                paciente.getCep().equals(expectedCep) &&
                paciente.getEndereco().equals(expectedEndereco)
        ));
    }

    @Test
    public void givenAInvalidCommand_whenCallsCreatePaciente_shouldReturnNotificationException() {
        // given
        final var expectedName = "João da Silva";
        final var expectedCpf = "111111111111";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores, 123";

        final var expectedErrorMessage = "cpf não é valido";
        final var expectedErrorCount = 1;

        final var command = CreatePacienteCommand.with(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco
        );

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(command)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).create(any());
    }

    @Test
    public void givenAInvalidNameCommand_whenCallsCreatePaciente_shouldReturnNotificationException() {
        // given
        final String expectedName = null;
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores, 123";

        final var expectedErrorMessage = "'nome' não pode ser nulo";
        final var expectedErrorCount = 1;

        final var command = CreatePacienteCommand.with(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco
        );

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(command)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).create(any());
    }

    @Test
    public void givenAEmptyName_whenCallsCreatePaciente_shouldReturnNotificationException() {
        // given
        final var expectedName = "";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores, 123";

        final var expectedErrorMessage = "'nome' não pode ser vazio";
        final var expectedErrorCount = 1;

        final var command = CreatePacienteCommand.with(
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco
        );

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(command)
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).create(any());
    }


}
