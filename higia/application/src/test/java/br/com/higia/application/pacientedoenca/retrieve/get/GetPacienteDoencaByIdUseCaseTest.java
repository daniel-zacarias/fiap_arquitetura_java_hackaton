package br.com.higia.application.pacientedoenca.retrieve.get;

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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetPacienteDoencaByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetPacienteDoencaByIdUseCase useCase;

    @Mock
    private PacienteDoencaGateway pacienteDoencaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                pacienteDoencaGateway
        };
    }

    @Test
    public void givenAValidId_whenCallsGetPacienteDoencaById_shouldReturnPacienteDoenca() {
        final var expectedId = PacienteDoencaID.unique().getValue();
        final var expectedPacienteId = PacienteID.unique();
        final var expectedDoencaId = DoencaCronicaID.unique();
        final var expectedDataDiagnostico = LocalDate.of(2020, 1, 1);
        final var expectedCreatedAt = Instant.parse("2024-01-01T10:00:00Z");

        final var pacienteDoenca = PacienteDoenca.with(
                expectedId,
                expectedPacienteId,
                expectedDoencaId,
                expectedDataDiagnostico,
                true,
                expectedCreatedAt);

        when(pacienteDoencaGateway.findById(PacienteDoencaID.from(expectedId)))
                .thenReturn(Optional.of(pacienteDoenca));

        final var actualOutput = useCase.execute(expectedId);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedPacienteId.getValue(), actualOutput.pacienteId());
        assertEquals(expectedDoencaId.getValue(), actualOutput.doencaCronicaId());
        assertEquals("01/01/2020", actualOutput.dataDiagnostico());
        assertEquals(expectedCreatedAt.toString(), actualOutput.createdAt());

        verify(pacienteDoencaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetPacienteDoencaById_shouldReturnNotFoundException() {
        final var expectedId = PacienteDoencaID.unique().getValue();

        when(pacienteDoencaGateway.findById(PacienteDoencaID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId));

        assertNotNull(actualException);

        verify(pacienteDoencaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
