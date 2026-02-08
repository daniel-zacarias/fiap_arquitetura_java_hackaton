package br.com.higia.application.doencacronica.retrieve.get;

import br.com.higia.application.UseCaseTest;
import br.com.higia.domain.doencacronica.DoencaCronica;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetDoencaCronicaByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetDoencaCronicaByIdUseCase useCase;

    @Mock
    private DoencaCronicaGateway doencaCronicaGateway;

    @Override
    protected Object[] getMocks() {
        return new Object[] {
                doencaCronicaGateway
        };
    }

    @Test
    public void givenAValidId_whenCallsGetDoencaCronicaById_shouldReturnDoencaCronica() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";
        final var expectedNome = "Diabetes Mellitus";
        final var expectedCid10 = "E10";

        final var aDoenca = DoencaCronica.with(
                expectedId,
                expectedNome,
                expectedCid10,
                Instant.now(),
                Instant.now());

        when(doencaCronicaGateway.findById(DoencaCronicaID.from(expectedId)))
                .thenReturn(Optional.of(aDoenca));

        // when
        final var actualOutput = useCase.execute(expectedId);

        // then
        assertNotNull(actualOutput);
        assertEquals(expectedId, actualOutput.id());
        assertEquals(expectedNome, actualOutput.nome());
        assertEquals(expectedCid10, actualOutput.cid10());

        verify(doencaCronicaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetDoencaCronicaById_shouldReturnNotFoundException() {
        // given
        final var expectedId = "550e8400-e29b-41d4-a716-446655440000";

        when(doencaCronicaGateway.findById(DoencaCronicaID.from(expectedId)))
                .thenReturn(Optional.empty());

        // when
        final var actualException = assertThrows(
                NotFoundException.class, () -> useCase.execute(expectedId));

        // then
        assertNotNull(actualException);
        assertTrue(actualException.getMessage().contains("DoencaCronica with ID " + expectedId + " was not found"));

        verify(doencaCronicaGateway).findById(argThat(id -> id.getValue().equals(expectedId)));
    }
}
