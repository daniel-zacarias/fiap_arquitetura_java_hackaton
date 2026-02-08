package br.com.higia.domain.prontuario;

import br.com.higia.domain.UnitTest;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.paciente.PacienteID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProntuarioTest extends UnitTest {

    @Test
    public void validParameters_whenCreateProntuario_thenProntuarioInstantiatedSuccessfully() {
        final var expectedPacienteId = PacienteID.unique();

        final var prontuario = Prontuario.newProntuario(expectedPacienteId);

        assertNotNull(prontuario);
        assertNotNull(prontuario.getId());
        assertNotNull(prontuario.getCreatedAt());
        assertNotNull(prontuario.getUpdatedAt());
        assertEquals(expectedPacienteId, prontuario.getPacienteId());
        assertTrue(prontuario.isAtivo());
        assertEquals(0, prontuario.getEvolucoes().size());
    }

    @Test
    public void validParameters_whenDeactivateProntuario_thenProntuarioDeactivatedSuccessfully() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());
        final var updatedAt = prontuario.getUpdatedAt();

        prontuario.desativar();

        Assertions.assertFalse(prontuario.isAtivo());
        assertTrue(updatedAt.isBefore(prontuario.getUpdatedAt()));
    }

    @Test
    public void validParameters_whenActivateProntuario_thenProntuarioActivatedSuccessfully() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());
        prontuario.desativar();

        prontuario.ativar();

        Assertions.assertTrue(prontuario.isAtivo());
    }

    @Test
    public void validParameters_whenRegisterEvolucao_thenEvolucaoAddedSuccessfully() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());
        final var updatedAt = prontuario.getUpdatedAt();

        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                prontuario.getId(),
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "observacao");

        final var registrado = prontuario.registrarEvolucao(evolucao);

        assertEquals(evolucao, registrado);
        assertEquals(1, prontuario.getEvolucoes().size());
        assertTrue(updatedAt.isBefore(prontuario.getUpdatedAt()));
    }

    @Test
    public void invalidInactiveProntuario_whenRegisterEvolucao_thenValidationError() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());
        prontuario.desativar();

        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                prontuario.getId(),
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                null);

        final var expectedErrorMessage = "prontuario deve estar ativo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> prontuario.registrarEvolucao(evolucao));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNullEvolucao_whenRegisterEvolucao_thenValidationError() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());

        final var expectedErrorMessage = "evolucao clinica nao pode ser nula";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> prontuario.registrarEvolucao((EvolucaoClinica) null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidEvolucaoFromOtherProntuario_whenRegisterEvolucao_thenValidationError() {
        final var prontuario = Prontuario.newProntuario(PacienteID.unique());

        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                ProntuarioID.unique(),
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                null);

        final var expectedErrorMessage = "evolucao clinica nao pertence ao prontuario";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> prontuario.registrarEvolucao(evolucao));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNullPacienteId_whenCreateProntuario_thenValidationError() {
        final PacienteID expectedPacienteId = null;
        final var expectedErrorMessage = "'pacienteId' nao pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Prontuario.newProntuario(expectedPacienteId));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
