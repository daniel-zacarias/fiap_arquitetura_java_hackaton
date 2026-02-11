package br.com.higia.application.doencacronica.retrieve.list;

import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListDoencasCronicasUseCase extends ListDoencasCronicasUseCase {

    private final DoencaCronicaGateway doencaCronicaGateway;

    public DefaultListDoencasCronicasUseCase(final DoencaCronicaGateway doencaCronicaGateway) {
        this.doencaCronicaGateway = Objects.requireNonNull(doencaCronicaGateway);
    }

    @Override
    public Pagination<ListDoencaCronicaOutput> execute(final SearchQuery input) {
        return doencaCronicaGateway.findAll(input)
                .map(ListDoencaCronicaOutput::from);
    }
}
