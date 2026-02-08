package br.com.higia.infrastructure.pacientedoenca.presenters;

import br.com.higia.application.pacientedoenca.retrieve.get.PacienteDoencaOutput;
import br.com.higia.application.pacientedoenca.retrieve.list.ListPacienteDoencaOutput;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaListResponse;
import br.com.higia.infrastructure.pacientedoenca.models.PacienteDoencaResponse;

public interface PacienteDoencaAPIPresenter {

    static PacienteDoencaResponse present(final PacienteDoencaOutput output) {
        return new PacienteDoencaResponse(
                output.id(),
                output.pacienteId(),
                output.doencaCronicaId(),
                output.dataDiagnostico(),
                output.ativo(),
                output.createdAt());
    }

    static PacienteDoencaListResponse present(final ListPacienteDoencaOutput output) {
        return new PacienteDoencaListResponse(
                output.id(),
                output.pacienteId(),
                output.doencaCronicaId(),
                output.dataDiagnostico(),
                output.ativo(),
                output.createdAt());
    }
}
