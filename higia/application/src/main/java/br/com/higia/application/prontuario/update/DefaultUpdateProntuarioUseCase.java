package br.com.higia.application.prontuario.update;

import br.com.higia.domain.exceptions.NotFoundException;
import br.com.higia.domain.prontuario.Prontuario;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioID;

import java.util.Objects;

public class DefaultUpdateProntuarioUseCase extends UpdateProntuarioUseCase {

    private final ProntuarioGateway prontuarioGateway;

    public DefaultUpdateProntuarioUseCase(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Override
    public UpdateProntuarioOutput execute(UpdateProntuarioCommand input) {
        final var id = input.id();
        final var prontuarioId = ProntuarioID.from(id);

        final var prontuario = this.prontuarioGateway.findById(prontuarioId)
                .orElseThrow(() -> NotFoundException.with(Prontuario.class, prontuarioId));

        if (input.ativo() != null) {
            if (input.ativo()) {
                prontuario.ativar();
            } else {
                prontuario.desativar();
            }
        }

        return UpdateProntuarioOutput.from(this.prontuarioGateway.update(prontuario));
    }
}
