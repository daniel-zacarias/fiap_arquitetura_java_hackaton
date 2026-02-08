package br.com.higia.application.pacientedoenca.retrieve.list;

import br.com.higia.application.UseCase;
import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pacientedoenca.PacienteDoencaSearchQuery;

public abstract class ListPacienteDoencasUseCase extends UseCase<PacienteDoencaSearchQuery, Pagination<ListPacienteDoencaOutput>> {
}
