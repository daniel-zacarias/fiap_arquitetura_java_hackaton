package br.com.higia.domain.paciente;

import br.com.higia.domain.UnitTest;
import br.com.higia.domain.exceptions.NotificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PacienteTest extends UnitTest {

    @Test
    public void validParameters_whenCreatePaciente_thenPacienteInstantiatedSuccessfully() {
        final String expectedNome = "João da Silva";
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";

        final var paciente = Paciente.newPaciente(
                expectedNome,
                expectedDataNascimento,
                expectedCpf,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco
        );

        assertNotNull(paciente);
        assertNotNull(paciente.getId());
        assertNotNull(paciente.getCreatedAt());
        assertNotNull(paciente.getUpdatedAt());
        assertEquals(expectedNome, paciente.getNome());
        assertEquals(expectedCpf, paciente.getCpf().getValue());
        assertEquals(expectedDataNascimento, paciente.getDataNascimento());
        assertEquals(expectedNacionalidade, paciente.getNacionalidade());
        assertEquals(expectedCep, paciente.getCep());
        assertEquals(expectedEndereco, paciente.getEndereco());

    }

    @Test
    public void validMaskedCpf_whenCreatePaciente_thenCpfNormalized() {
        final var paciente = Paciente.newPaciente(
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "170.481.980-60",
                "Brasileiro",
                "12345-678",
                "Rua Exemplo, 123"
        );

        Assertions.assertEquals("17048198060", paciente.getCpf().getValue());
    }

    @Test
    public void invalidNullName_whenCreatePaciente_thenValidationError() {
        final String expectedNome = null;
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' não pode ser nulo";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Paciente.newPaciente(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                ));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidEmptyName_whenCreatePaciente_thenValidationError() {
        final String expectedNome = "";
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' não pode ser vazio";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Paciente.newPaciente(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                ));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidNameMoreThan100Chars_whenCreatePaciente_thenValidationError() {
        final String expectedNome = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' não pode conter mais de 100 caracteres";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Paciente.newPaciente(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                ));

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidCpf_whenCreatePaciente_thenValidationError() {
        final String expectedNome = "João da Silva";
        final String expectedCpf = "11111111111";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "cpf não é valido";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Paciente.newPaciente(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                )
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(
                expectedErrorMessage,
                actualException.getErrors().get(0).message()
        );
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }


    @Test
    public void validParameters_whenUpdatePaciente_thenPacienteUpdatedSuccessfully() {
        final var paciente = Paciente.newPaciente(
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "17048198060",
                "Brasileiro",
                "12345-678",
                "Rua Exemplo, 123"
        );

        final String expectedNome = "Maria Souza";
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1985, 5, 15);
        final String expectedNacionalidade = "Brasileira";
        final String expectedCep = "98765-432";
        final String expectedEndereco = "Rua Exemplo, 123";
        final Instant actualUpdatedAt = paciente.getUpdatedAt();

        paciente.update(
                expectedNome,
                expectedDataNascimento,
                expectedCpf,
                expectedNacionalidade,
                expectedCep,
                expectedEndereco
        );

        assertNotNull(paciente);
        assertNotNull(paciente.getId());
        assertNotNull(paciente.getCreatedAt());
        assertNotNull(paciente.getUpdatedAt());
        assertEquals(expectedNome, paciente.getNome());
        assertEquals(expectedCpf, paciente.getCpf().getValue());
        assertEquals(expectedDataNascimento, paciente.getDataNascimento());
        assertEquals(expectedNacionalidade, paciente.getNacionalidade());
        assertEquals(expectedCep, paciente.getCep());
        assertEquals(expectedEndereco, paciente.getEndereco());
        assertTrue(actualUpdatedAt.isBefore(paciente.getUpdatedAt()));
    }

    @Test
    public void invalidNullName_whenUpdatePaciente_thenValidationError() {
        final var paciente = Paciente.newPaciente(
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "17048198060",
                "Brasileiro",
                "12345-678",
                "Rua Exemplo, 123"
        );

        final String expectedNome = null;
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' não pode ser nulo";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> paciente.update(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                )
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidEmptyName_whenUpdatePaciente_thenValidationError() {
        final var paciente = Paciente.newPaciente(
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "17048198060",
                "Brasileiro",
                "12345-678",
                "Rua Exemplo, 123"
        );

        final String expectedNome = "";
        final String expectedCpf = "17048198060";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedCep = "12345-678";
        final String expectedEndereco = "Rua Exemplo, 123";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'nome' não pode ser vazio";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> paciente.update(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                )
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
    }

    @Test
    public void invalidCpf_whenUpdatePaciente_thenValidationError() {
        final var paciente = Paciente.newPaciente(
                "João da Silva",
                LocalDate.of(1990, 1, 1),
                "17048198060",
                "Brasileiro",
                "12345-678",
                "Rua Exemplo, 123"
        );

        final String expectedNome = "João da Silva";
        final String expectedCpf = "12345678900";
        final LocalDate expectedDataNascimento = LocalDate.of(1990, 1, 1);
        final String expectedNacionalidade = "Brasileiro";
        final String expectedEndereco = "Rua Exemplo, 123";
        final String expectedCep = "12345-678";

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "cpf não é valido";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> paciente.update(
                        expectedNome,
                        expectedDataNascimento,
                        expectedCpf,
                        expectedNacionalidade,
                        expectedCep,
                        expectedEndereco
                )
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(
                expectedErrorMessage,
                actualException.getErrors().get(0).message()
        );
    }

    @Test
    public void invalidNullCpf_whenCreatePaciente_thenValidationError() {
        final var expectedErrorMessage = "cpf não é valido";

        final var actualException = Assertions.assertThrows(
                NotificationException.class,
                () -> Paciente.newPaciente(
                        "João da Silva",
                        LocalDate.of(1990, 1, 1),
                        null,
                        "Brasileiro",
                        "12345-678",
                        "Rua Exemplo, 123"
                )
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }


}