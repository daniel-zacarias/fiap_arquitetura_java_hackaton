package br.com.higia.application.evolucaoclinica.update;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaID;
import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.prontuario.ProntuarioID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UpdateEvolucaoClinicaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateEvolucaoClinicaUseCase useCase;

    @Mock
    private EvolucaoClinicaGateway evolucaoClinicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                evolucaoClinicaGateway
        };
    }

    @Test
    public void givenAValidCommand_whenCallsUpdateEvolucaoClinica_shouldReturnId() {
        final var expectedId = EvolucaoClinicaID.unique().getValue();
        final var expectedDataAtendimento = "2024-02-01T10:00:00Z";
        final var expectedPressaoSistolica = 130;
        final var expectedPressaoDiastolica = 85;
        final var expectedGlicemia = 90;
        final var expectedPeso = BigDecimal.valueOf(82.1);
        final var expectedObservacoes = "observacao atualizada";

        final var evolucao = EvolucaoClinica.with(
                expectedId,
                ProntuarioID.unique(),
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "observacao",
                Instant.now());

        final var command = UpdateEvolucaoClinicaCommand.with(
                expectedId,
                expectedDataAtendimento,
                expectedPressaoSistolica,
                expectedPressaoDiastolica,
                expectedGlicemia,
                expectedPeso,
                expectedObservacoes);

        when(evolucaoClinicaGateway.findById(EvolucaoClinicaID.from(expectedId)))
                .thenReturn(Optional.of(evolucao));

        when(evolucaoClinicaGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());

        verify(evolucaoClinicaGateway).update(argThat(updated -> updated.getId().getValue().equals(expectedId) &&
                Instant.parse(expectedDataAtendimento).equals(updated.getDataAtendimento()) &&
                expectedPressaoSistolica == updated.getPressaoSistolica() &&
                expectedPressaoDiastolica == updated.getPressaoDiastolica() &&
                expectedGlicemia == updated.getGlicemia() &&
                expectedPeso.equals(updated.getPeso()) &&
                expectedObservacoes.equals(updated.getObservacoes())));
    }

    @Test
    public void givenAnInvalidId_whenCallsUpdateEvolucaoClinica_shouldReturnNotFoundException() {
        final var expectedId = EvolucaoClinicaID.unique().getValue();

        final var command = UpdateEvolucaoClinicaCommand.with(
                expectedId,
                "2024-02-01T10:00:00Z",
                130,
                85,
                90,
                BigDecimal.valueOf(82.1),
                "observacao atualizada");

        when(evolucaoClinicaGateway.findById(EvolucaoClinicaID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(command));

        assertNotNull(actualException);

        verify(evolucaoClinicaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
        verify(evolucaoClinicaGateway, never()).update(any());
    }

    @Test
    public void givenAnInvalidCommand_whenCallsUpdateEvolucaoClinica_shouldReturnNotificationException() {
        final var expectedId = EvolucaoClinicaID.unique().getValue();
        final var expectedProntuarioId = ProntuarioID.unique();

        final var evolucao = EvolucaoClinica.with(
                expectedId,
                expectedProntuarioId,
                Instant.parse("2024-01-01T10:00:00Z"),
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "observacao",
                Instant.now());

        final var command = UpdateEvolucaoClinicaCommand.with(
                expectedId,
                null,
                130,
                85,
                90,
                BigDecimal.valueOf(82.1),
                "observacao atualizada");

        when(evolucaoClinicaGateway.findById(EvolucaoClinicaID.from(expectedId)))
                .thenReturn(Optional.of(evolucao));

        final var actualException = assertThrows(
                NotificationException.class,
                () -> useCase.execute(command));

        assertNotNull(actualException);

        verify(evolucaoClinicaGateway, never()).update(any());
    }
}
