package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.application.UseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

public abstract class ListPacientesUseCase extends UseCase<SearchQuery, Pagination<ListPacientesOutput>> {

}
