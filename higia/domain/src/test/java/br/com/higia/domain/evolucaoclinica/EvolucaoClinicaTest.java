package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.UnitTest;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.prontuario.ProntuarioID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EvolucaoClinicaTest extends UnitTest {

    @Test
    public void validParameters_whenCreateEvolucaoClinica_thenEvolucaoClinicaInstantiatedSuccessfully() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedPressaoSistolica = 120;
        final var expectedPressaoDiastolica = 80;
        final var expectedGlicemia = 95;
        final var expectedPeso = BigDecimal.valueOf(80.5);
        final var expectedObservacoes = "observacao";

        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                expectedProntuarioId,
                expectedDataAtendimento,
                expectedPressaoSistolica,
                expectedPressaoDiastolica,
                expectedGlicemia,
                expectedPeso,
                expectedObservacoes);

        assertNotNull(evolucao);
        assertNotNull(evolucao.getId());
        assertNotNull(evolucao.getCreatedAt());
        assertEquals(expectedProntuarioId, evolucao.getProntuarioId());
        assertEquals(expectedDataAtendimento, evolucao.getDataAtendimento());
        assertEquals(expectedPressaoSistolica, evolucao.getPressaoSistolica());
        assertEquals(expectedPressaoDiastolica, evolucao.getPressaoDiastolica());
        assertEquals(expectedGlicemia, evolucao.getGlicemia());
        assertEquals(expectedPeso, evolucao.getPeso());
        assertEquals(expectedObservacoes, evolucao.getObservacoes());
    }

    @Test
    public void invalidNullProntuarioId_whenCreateEvolucaoClinica_thenValidationError() {
        final ProntuarioID expectedProntuarioId = null;
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedErrorMessage = "'prontuarioId' nao pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        120,
                        80,
                        95,
                        BigDecimal.valueOf(80.5),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNullDataAtendimento_whenCreateEvolucaoClinica_thenValidationError() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final Instant expectedDataAtendimento = null;
        final var expectedErrorMessage = "'dataAtendimento' nao pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        120,
                        80,
                        95,
                        BigDecimal.valueOf(80.5),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNonPositivePressaoSistolica_whenCreateEvolucaoClinica_thenValidationError() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedErrorMessage = "'pressaoSistolica' deve ser positivo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        0,
                        80,
                        95,
                        BigDecimal.valueOf(80.5),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNonPositivePressaoDiastolica_whenCreateEvolucaoClinica_thenValidationError() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedErrorMessage = "'pressaoDiastolica' deve ser positivo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        120,
                        -1,
                        95,
                        BigDecimal.valueOf(80.5),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNonPositiveGlicemia_whenCreateEvolucaoClinica_thenValidationError() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedErrorMessage = "'glicemia' deve ser positivo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        120,
                        80,
                        0,
                        BigDecimal.valueOf(80.5),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNonPositivePeso_whenCreateEvolucaoClinica_thenValidationError() {
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");
        final var expectedErrorMessage = "'peso' deve ser positivo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> EvolucaoClinica.newEvolucaoClinica(
                        expectedProntuarioId,
                        expectedDataAtendimento,
                        120,
                        80,
                        95,
                        BigDecimal.valueOf(0),
                        null));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
