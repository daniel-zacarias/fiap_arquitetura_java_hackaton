package br.com.higia.application.doencacronica.retrieve.list;

import br.com.higia.application.UseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

public abstract class ListDoencasCronicasUseCase
        extends UseCase<SearchQuery, Pagination<ListDoencaCronicaOutput>> {
}
