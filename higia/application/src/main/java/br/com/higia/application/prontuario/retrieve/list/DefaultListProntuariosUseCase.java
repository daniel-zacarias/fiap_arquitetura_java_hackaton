package br.com.higia.application.prontuario.retrieve.list;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import br.com.higia.domain.prontuario.ProntuarioSearchQuery;

import java.util.Objects;

public class DefaultListProntuariosUseCase extends ListProntuariosUseCase {

    private final ProntuarioGateway prontuarioGateway;

    public DefaultListProntuariosUseCase(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Override
    public Pagination<ListProntuarioOutput> execute(final ProntuarioSearchQuery input) {
        return prontuarioGateway.findAll(input)
                .map(ListProntuarioOutput::from);
    }
}
