package br.com.higia.application.evolucaoclinica.retrieve.list;

import br.com.higia.application.UseCase;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaSearchQuery;
import br.com.higia.domain.pagination.Pagination;

public abstract class ListEvolucoesClinicasUseCase
        extends UseCase<EvolucaoClinicaSearchQuery, Pagination<ListEvolucaoClinicaOutput>> {
}
