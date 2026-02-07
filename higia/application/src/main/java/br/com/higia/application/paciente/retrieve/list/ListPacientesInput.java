package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.domain.pagination.SearchQuery;

public record ListPacientesInput(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction) {
    public static ListPacientesInput with(
            final int page,
            final int perPage,
            final String terms,
            final String sort,
            final String direction) {
        return new ListPacientesInput(page, perPage, terms, sort, direction);
    }

    public SearchQuery toSearchQuery() {
        return new SearchQuery(page, perPage, terms, sort, direction);
    }
}
