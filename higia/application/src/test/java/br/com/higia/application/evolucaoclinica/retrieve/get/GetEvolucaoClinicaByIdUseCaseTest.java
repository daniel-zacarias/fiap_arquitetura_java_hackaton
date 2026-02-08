package br.com.higia.application.evolucaoclinica.retrieve.get;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaID;
import br.com.higia.domain.exceptions.NotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetEvolucaoClinicaByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetEvolucaoClinicaByIdUseCase useCase;

    @Mock
    private EvolucaoClinicaGateway evolucaoClinicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                evolucaoClinicaGateway
        };
    }

    @Test
    public void givenAValidId_whenCallsGetEvolucaoClinicaById_shouldReturnEvolucao() {
        final var expectedId = EvolucaoClinicaID.unique().getValue();
        final var expectedProntuarioId = ProntuarioID.unique();
        final var expectedDataAtendimento = Instant.parse("2024-01-01T10:00:00Z");

        final var evolucao = EvolucaoClinica.with(
                expectedId,
                expectedProntuarioId,
                expectedDataAtendimento,
                120,
                80,
                95,
                BigDecimal.valueOf(80.5),
                "obs",
                Instant.parse("2024-01-01T11:00:00Z"));

        when(evolucaoClinicaGateway.findById(EvolucaoClinicaID.from(expectedId)))
                .thenReturn(Optional.of(evolucao));

        final var actualOutput = useCase.execute(expectedId);

        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedProntuarioId.getValue(), actualOutput.prontuarioId());
        assertEquals(expectedDataAtendimento.toString(), actualOutput.dataAtendimento());

        verify(evolucaoClinicaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetEvolucaoClinicaById_shouldReturnNotFoundException() {
        final var expectedId = EvolucaoClinicaID.unique().getValue();

        when(evolucaoClinicaGateway.findById(EvolucaoClinicaID.from(expectedId)))
                .thenReturn(Optional.empty());

        final var actualException = assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId));

        assertNotNull(actualException);
        assertTrue(actualException.getMessage().contains("EvolucaoClinica with ID " + expectedId + " was not found"));

        verify(evolucaoClinicaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
