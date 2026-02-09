package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.UnitTest;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.paciente.PacienteID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PacienteDoencaTest extends UnitTest {

    @Test
    public void validParameters_whenCreatePacienteDoenca_thenPacienteDoencaInstantiatedSuccessfully() {
        final var expectedPacienteId = PacienteID.unique();
        final var expectedDoencaId = DoencaCronicaID.unique();
        final var expectedDataDiagnostico = LocalDate.of(2020, 1, 10);

        final var pacienteDoenca = PacienteDoenca.newPacienteDoenca(
                expectedPacienteId,
                expectedDoencaId,
                expectedDataDiagnostico);

        assertNotNull(pacienteDoenca);
        assertNotNull(pacienteDoenca.getId());
        assertNotNull(pacienteDoenca.getCreatedAt());
        assertEquals(expectedPacienteId, pacienteDoenca.getPacienteId());
        assertEquals(expectedDoencaId, pacienteDoenca.getDoencaCronicaId());
        assertEquals(expectedDataDiagnostico, pacienteDoenca.getDataDiagnostico());
        assertTrue(pacienteDoenca.isAtivo());
    }

    @Test
    public void validParameters_whenUpdatePacienteDoenca_thenPacienteDoencaUpdatedSuccessfully() {
        final var pacienteDoenca = PacienteDoenca.newPacienteDoenca(
                PacienteID.unique(),
                DoencaCronicaID.unique(),
                LocalDate.of(2020, 1, 10));

        final var expectedDataDiagnostico = LocalDate.of(2021, 5, 20);

        pacienteDoenca.update(expectedDataDiagnostico, true);

        assertEquals(expectedDataDiagnostico, pacienteDoenca.getDataDiagnostico());
    }

    @Test
    public void validParameters_whenDeactivatePacienteDoenca_thenPacienteDoencaDeactivatedSuccessfully() {
        final var pacienteDoenca = PacienteDoenca.newPacienteDoenca(
                PacienteID.unique(),
                DoencaCronicaID.unique(),
                LocalDate.of(2020, 1, 10));

        pacienteDoenca.desativar();

        Assertions.assertFalse(pacienteDoenca.isAtivo());
    }

    @Test
    public void validParameters_whenActivatePacienteDoenca_thenPacienteDoencaActivatedSuccessfully() {
        final var pacienteDoenca = PacienteDoenca.newPacienteDoenca(
                PacienteID.unique(),
                DoencaCronicaID.unique(),
                LocalDate.of(2020, 1, 10));

        pacienteDoenca.desativar();
        pacienteDoenca.ativar();

        Assertions.assertTrue(pacienteDoenca.isAtivo());
    }

    @Test
    public void invalidNullPacienteId_whenCreatePacienteDoenca_thenValidationError() {
        final PacienteID expectedPacienteId = null;
        final var expectedDoencaId = DoencaCronicaID.unique();
        final var expectedDataDiagnostico = LocalDate.of(2020, 1, 10);
        final var expectedErrorMessage = "'pacienteId' nao pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> PacienteDoenca.newPacienteDoenca(
                        expectedPacienteId,
                        expectedDoencaId,
                        expectedDataDiagnostico));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNullDoencaCronicaId_whenCreatePacienteDoenca_thenValidationError() {
        final var expectedPacienteId = PacienteID.unique();
        final DoencaCronicaID expectedDoencaId = null;
        final var expectedDataDiagnostico = LocalDate.of(2020, 1, 10);
        final var expectedErrorMessage = "'doencaCronicaId' nao pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> PacienteDoenca.newPacienteDoenca(
                        expectedPacienteId,
                        expectedDoencaId,
                        expectedDataDiagnostico));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
