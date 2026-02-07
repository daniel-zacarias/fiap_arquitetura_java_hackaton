package br.com.higia.application.paciente.update;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class UpdatePacienteUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdatePacienteUseCase useCase;

    @Mock
    private PacienteGateway pacienteGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsUpdatePaciente_shouldReturnPacienteId() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final var expectedName = "João da Silva Atualizado";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores Atualizada, 456";

        final var aPaciente = Paciente.with(
                expectedId,
                "João da Silva",
                java.time.LocalDate.of(1990, 1, 1),
                expectedCpf,
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                java.time.Instant.now(),
                java.time.Instant.now());

        final var command = UpdatePacienteCommand.with(
                expectedId,
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco);

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.of(aPaciente));

        when(pacienteGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(command);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());

        verify(pacienteGateway).findById(argThat(id -> id.getValue().equals(expectedId)));

        verify(pacienteGateway).update(argThat(paciente -> paciente.getId().getValue().equals(expectedId) &&
                paciente.getNome().equals(expectedName) &&
                paciente.getCpf().getValue().equals(expectedCpf) &&
                paciente.getNacionalidade().equals(expectedNacionalidade) &&
                paciente.getCep().equals(expectedCep) &&
                paciente.getEndereco().equals(expectedEndereco)));
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdatePaciente_shouldReturnNotFoundException() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final var expectedName = "João da Silva Atualizado";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores Atualizada, 456";

        final var command = UpdatePacienteCommand.with(
                expectedId,
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco);

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.empty());

        // when
        final var actualException = assertThrows(
                NotFoundException.class, () -> useCase.execute(command));

        // then
        assertNotNull(actualException);
        assertTrue(actualException.getMessage().contains("Paciente with ID " + expectedId + " was not found"));

        verify(pacienteGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
        verify(pacienteGateway, times(0)).update(any());
    }

    @Test
    public void givenAInvalidName_whenCallsUpdatePaciente_shouldReturnNotificationException() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final String expectedName = null;
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores Atualizada, 456";

        final var aPaciente = Paciente.with(
                expectedId,
                "João da Silva",
                java.time.LocalDate.of(1990, 1, 1),
                expectedCpf,
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                java.time.Instant.now(),
                java.time.Instant.now());

        final var command = UpdatePacienteCommand.with(
                expectedId,
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco);

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.of(aPaciente));

        // when
        final var actualException = assertThrows(
                NotificationException.class, () -> useCase.execute(command));

        // then
        assertNotNull(actualException);
        assertEquals(1, actualException.getErrors().size());
        assertEquals("'nome' não pode ser nulo", actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).update(any());
    }

    @Test
    public void givenAnInvalidCpf_whenCallsUpdatePaciente_shouldReturnNotificationException() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final var expectedName = "João da Silva Atualizado";
        final var invalidCpf = "11111111111";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores Atualizada, 456";

        final var aPaciente = Paciente.with(
                expectedId,
                "João da Silva",
                java.time.LocalDate.of(1990, 1, 1),
                "39922782049",
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                java.time.Instant.now(),
                java.time.Instant.now());

        final var command = UpdatePacienteCommand.with(
                expectedId,
                expectedName,
                invalidCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco);

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.of(aPaciente));

        // when
        final var actualException = assertThrows(
                NotificationException.class, () -> useCase.execute(command));

        // then
        assertNotNull(actualException);
        assertEquals(1, actualException.getErrors().size());
        assertEquals("cpf não é valido", actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).update(any());
    }

    @Test
    public void givenAnEmptyName_whenCallsUpdatePaciente_shouldReturnNotificationException() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final var expectedName = "";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = "01/01/1990";
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores Atualizada, 456";

        final var aPaciente = Paciente.with(
                expectedId,
                "João da Silva",
                java.time.LocalDate.of(1990, 1, 1),
                expectedCpf,
                "Brasileiro",
                "12345-678",
                "Rua das Flores, 123",
                java.time.Instant.now(),
                java.time.Instant.now());

        final var command = UpdatePacienteCommand.with(
                expectedId,
                expectedName,
                expectedCpf,
                expectedDataNascimento,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco);

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.of(aPaciente));

        // when
        final var actualException = assertThrows(
                NotificationException.class, () -> useCase.execute(command));

        // then
        assertNotNull(actualException);
        assertEquals(1, actualException.getErrors().size());
        assertEquals("'nome' não pode ser vazio", actualException.getErrors().get(0).message());

        verify(pacienteGateway, times(0)).update(any());
    }
}
