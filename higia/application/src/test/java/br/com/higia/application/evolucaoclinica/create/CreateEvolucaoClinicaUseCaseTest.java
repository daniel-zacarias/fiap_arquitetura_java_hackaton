package br.com.higia.application.evolucaoclinica.create;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.prontuario.ProntuarioID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CreateEvolucaoClinicaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateEvolucaoClinicaUseCase useCase;

    @Mock
    private EvolucaoClinicaGateway evolucaoClinicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                evolucaoClinicaGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsCreateEvolucaoClinica_shouldReturnId() {
        final var expectedProntuarioId = ProntuarioID.unique().getValue();
        final var expectedDataAtendimento = "2024-01-01T10:00:00Z";

        final var command = CreateEvolucaoClinicaCommand.with(
                expectedProntuarioId,
                expectedDataAtendimento,
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "observacao");

        when(evolucaoClinicaGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(evolucaoClinicaGateway)
                .create(argThat(evolucao -> evolucao.getProntuarioId().getValue().equals(expectedProntuarioId) &&
                        Instant.parse(expectedDataAtendimento).equals(evolucao.getDataAtendimento())));
    }

    @Test
    public void givenAnInvalidCommand_whenCallsCreateEvolucaoClinica_shouldThrowNotificationException() {
        final var expectedProntuarioId = ProntuarioID.unique().getValue();

        final var command = CreateEvolucaoClinicaCommand.with(
                expectedProntuarioId,
                null,
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                null);

        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(command));

        assertNotNull(actualException);

        verify(evolucaoClinicaGateway, org.mockito.Mockito.never()).create(any());
    }
}
