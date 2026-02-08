package br.com.higia.application.paciente.retrieve.get;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.paciente.PacienteGateway;
import br.com.higia.domain.paciente.PacienteID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetPacienteByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetPacienteByIdUseCase useCase;

    @Mock
    private PacienteGateway pacienteGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteGateway
        };
    }

    @Test
    public void givenAValidId_whenCallsGetPacienteById_shouldReturnPaciente() {
        // given
        final var expectedId = PacienteID.unique().getValue();
        final var expectedName = "João da Silva";
        final var expectedCpf = "39922782049";
        final var expectedDataNascimento = java.time.LocalDate.of(1990, 1, 1);
        final var expectedNacionalidade = "Brasileiro";
        final var expectedCep = "12345-678";
        final var expectedEndereco = "Rua das Flores, 123";

        final var aPaciente = Paciente.with(
                expectedId,
                expectedName,
                expectedDataNascimento,
                expectedCpf,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco,
                java.time.Instant.now(),
                java.time.Instant.now());

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.of(aPaciente));

        // when
        final var actualOutput = useCase.execute(expectedId);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedName, actualOutput.nome());
        assertEquals(expectedCpf, actualOutput.cpf());
        assertEquals(expectedNacionalidade, actualOutput.nacionalidade());
        assertEquals(expectedCep, actualOutput.cep());
        assertEquals(expectedEndereco, actualOutput.endereco());

        verify(pacienteGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetPacienteById_shouldReturnNotFoundException() {
        // given
        final var expectedId = PacienteID.unique().getValue();

        when(pacienteGateway.findById(PacienteID.from(expectedId)))
                .thenReturn(Optional.empty());

        // when
        final var actualException = assertThrows(
                NotFoundException.class, () -> useCase.execute(expectedId));

        // then
        assertNotNull(actualException);
        assertTrue(actualException.getMessage().contains("Paciente with ID " + expectedId + " was not found"));

        verify(pacienteGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
