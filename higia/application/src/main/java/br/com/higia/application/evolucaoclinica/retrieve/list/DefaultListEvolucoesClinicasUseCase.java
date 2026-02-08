package br.com.higia.application.evolucaoclinica.retrieve.list;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaSearchQuery;
import br.com.higia.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListEvolucoesClinicasUseCase extends ListEvolucoesClinicasUseCase {

    private final EvolucaoClinicaGateway evolucaoClinicaGateway;

    public DefaultListEvolucoesClinicasUseCase(final EvolucaoClinicaGateway evolucaoClinicaGateway) {
        this.evolucaoClinicaGateway = Objects.requireNonNull(evolucaoClinicaGateway);
    }

    @Override
    public Pagination<ListEvolucaoClinicaOutput> execute(final EvolucaoClinicaSearchQuery input) {
        return evolucaoClinicaGateway.findAll(input)
                .map(ListEvolucaoClinicaOutput::from);
    }
}
