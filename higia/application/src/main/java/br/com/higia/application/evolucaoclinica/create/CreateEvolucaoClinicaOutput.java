package br.com.higia.application.evolucaoclinica.create;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;

public record CreateEvolucaoClinicaOutput(String id) {
    public static CreateEvolucaoClinicaOutput from(final String id) {
        return new CreateEvolucaoClinicaOutput(id);
    }

    public static CreateEvolucaoClinicaOutput from(final EvolucaoClinica evolucaoClinica) {
        return from(evolucaoClinica.getId().getValue());
    }
}
