package br.com.higia.application.doencacronica.retrieve.get;

import br.com.higia.domain.doencacronica.DoencaCronica;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.exceptions.NotFoundException;

import java.util.Objects;

public class DefaultDoencaCronicaGetByIdUseCase extends GetDoencaCronicaByIdUseCase {

    private final DoencaCronicaGateway doencaCronicaGateway;

    public DefaultDoencaCronicaGetByIdUseCase(final DoencaCronicaGateway doencaCronicaGateway) {
        this.doencaCronicaGateway = Objects.requireNonNull(doencaCronicaGateway);
    }

    @Override
    public DoencaCronicaOutput execute(String input) {
        final var doencaId = DoencaCronicaID.from(input);

        final var doenca = doencaCronicaGateway.findById(doencaId)
                .orElseThrow(() -> NotFoundException.with(DoencaCronica.class, doencaId));

        return DoencaCronicaOutput.from(doenca);
    }
}
