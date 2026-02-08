package br.com.higia.application.prontuario.retrieve.get;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioID;

import java.util.Objects;

public class DefaultGetProntuarioByIdUseCase extends GetProntuarioByIdUseCase {

    private final ProntuarioGateway prontuarioGateway;

    public DefaultGetProntuarioByIdUseCase(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Override
    public ProntuarioOutput execute(final String input) {
        final var prontuarioId = ProntuarioID.from(input);

        final var prontuario = prontuarioGateway.findById(prontuarioId)
                .orElseThrow(() -> NotFoundException.with(Prontuario.class, prontuarioId));

        return ProntuarioOutput.from(prontuario);
    }
}
