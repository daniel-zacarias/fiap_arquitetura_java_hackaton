package br.com.higia.infrastructure.evolucaoclinica.presenters;

import br.com.higia.application.evolucaoclinica.retrieve.get.EvolucaoClinicaOutput;
import br.com.higia.application.evolucaoclinica.retrieve.list.ListEvolucaoClinicaOutput;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaListResponse;
import br.com.higia.infrastructure.evolucaoclinica.models.EvolucaoClinicaResponse;

public interface EvolucaoClinicaAPIPresenter {

    static EvolucaoClinicaResponse present(final EvolucaoClinicaOutput output) {
        return new EvolucaoClinicaResponse(
                output.id(),
                output.prontuarioId(),
                output.dataAtendimento(),
                output.pressaoSistolica(),
                output.pressaoDiastolica(),
                output.glicemia(),
                output.peso(),
                output.observacoes(),
                output.createdAt());
    }

    static EvolucaoClinicaListResponse present(final ListEvolucaoClinicaOutput output) {
        return new EvolucaoClinicaListResponse(
                output.id(),
                output.prontuarioId(),
                output.dataAtendimento(),
                output.pressaoSistolica(),
                output.pressaoDiastolica(),
                output.glicemia(),
                output.peso(),
                output.observacoes(),
                output.createdAt());
    }
}
