package br.com.higia.application.evolucaoclinica.update;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;

public record UpdateEvolucaoClinicaOutput(String id) {
    public static UpdateEvolucaoClinicaOutput from(final String id) {
        return new UpdateEvolucaoClinicaOutput(id);
    }

    public static UpdateEvolucaoClinicaOutput from(final EvolucaoClinica evolucaoClinica) {
        return from(evolucaoClinica.getId().getValue());
    }
}
