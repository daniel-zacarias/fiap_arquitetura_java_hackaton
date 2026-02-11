package br.com.higia.domain.doencacronica;

import java.util.Optional;

import br.com.higia.domain.pagination.Pagination;
import br.com.higia.domain.pagination.SearchQuery;

public interface DoencaCronicaGateway {

    Optional<DoencaCronica> findById(DoencaCronicaID id);

    Pagination<DoencaCronica> findAll(SearchQuery query);

}
