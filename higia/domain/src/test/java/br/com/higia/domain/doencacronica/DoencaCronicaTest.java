package br.com.higia.domain.doencacronica;

import br.com.higia.domain.UnitTest;
import br.com.higia.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class DoencaCronicaTest extends UnitTest {

    @Test
    public void validParameters_whenCreateDoencaCronica_thenDoencaCronicaInstantiatedSuccessfully() {
        final String expectedNome = "Diabetes Mellitus";
        final String expectedCid10 = "E10";

        final var doenca = DoencaCronica.newDoencaCronica(
                expectedNome,
                expectedCid10);

        assertNotNull(doenca);
        assertNotNull(doenca.getId());
        assertNotNull(doenca.getCreatedAt());
        assertNotNull(doenca.getUpdatedAt());
        assertEquals(expectedNome, doenca.getNome());
        assertEquals(expectedCid10, doenca.getCid10());
    }

    @Test
    public void validParameters_whenUpdateDoencaCronica_thenDoencaCronicaUpdatedSuccessfully() {
        final var doenca = DoencaCronica.newDoencaCronica(
                "Diabetes Mellitus",
                "E10");

        final String expectedNome = "Hipertensão";
        final String expectedCid10 = "I10";
        final Instant actualUpdatedAt = doenca.getUpdatedAt();

        doenca.update(expectedNome, expectedCid10);

        assertNotNull(doenca);
        assertNotNull(doenca.getId());
        assertNotNull(doenca.getCreatedAt());
        assertNotNull(doenca.getUpdatedAt());
        assertEquals(expectedNome, doenca.getNome());
        assertEquals(expectedCid10, doenca.getCid10());
        assertTrue(actualUpdatedAt.isBefore(doenca.getUpdatedAt()));
    }

    @Test
    public void invalidNullName_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = null;
        final String expectedCid10 = "E10";
        final var expectedErrorMessage = "'nome' não pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidEmptyName_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = "";
        final String expectedCid10 = "E10";
        final var expectedErrorMessage = "'nome' não pode ser vazio";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNameMoreThan100Chars_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
        final String expectedCid10 = "E10";
        final var expectedErrorMessage = "'nome' não pode conter mais de 100 caracteres";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNullCid10_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = "Diabetes Mellitus";
        final String expectedCid10 = null;
        final var expectedErrorMessage = "'cid10' não pode ser nulo";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidEmptyCid10_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = "Diabetes Mellitus";
        final String expectedCid10 = "";
        final var expectedErrorMessage = "'cid10' não pode ser vazio";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidCid10MoreThan10Chars_whenCreateDoencaCronica_thenValidationError() {
        final String expectedNome = "Diabetes Mellitus";
        final String expectedCid10 = "ABCDEFGHIJK";
        final var expectedErrorMessage = "'cid10' não pode conter mais de 10 caracteres";
        final var expectedErrorCount = 1;

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> DoencaCronica.newDoencaCronica(
                        expectedNome,
                        expectedCid10));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }
}
