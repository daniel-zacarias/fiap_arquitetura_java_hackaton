package br.com.higia.application.prontuario.retrieve.list;

import br.com.higia.application.UseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.prontuario.ProntuarioSearchQuery;

public abstract class ListProntuariosUseCase extends UseCase<ProntuarioSearchQuery, Pagination<ListProntuarioOutput>> {
}
