package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.pagination.Pagination;

import java.util.Optional;

public interface EvolucaoClinicaGateway {

    EvolucaoClinica create(EvolucaoClinica evolucaoClinica);

    Optional<EvolucaoClinica> findById(EvolucaoClinicaID id);

    Pagination<EvolucaoClinica> findAll(EvolucaoClinicaSearchQuery searchQuery);

    EvolucaoClinica update(EvolucaoClinica evolucaoClinica);
}
