package br.com.higia.infrastructure.paciente.presenters;

import br.com.higia.application.paciente.retrieve.get.PacienteOutput;
import br.com.higia.application.paciente.retrieve.list.ListPacientesOutput;
import br.com.higia.infrastructure.paciente.models.PacienteListResponse;
import br.com.higia.infrastructure.paciente.models.PacienteResponse;

public interface PacienteAPIPresenter {
    static PacienteResponse present(PacienteOutput pacienteOutput) {
        return new PacienteResponse(
                pacienteOutput.id(),
                pacienteOutput.nome(),
                pacienteOutput.cpf(),
                pacienteOutput.dataNascimento(),
                pacienteOutput.nacionalidade(),
                pacienteOutput.cep(),
                pacienteOutput.endereco(),
                pacienteOutput.createdAt(),
                pacienteOutput.updatedAt()
        );
    }

    static PacienteListResponse present(ListPacientesOutput pacientesOutput) {
        return new PacienteListResponse(
                pacientesOutput.id(),
                pacientesOutput.nome(),
                pacientesOutput.cpf(),
                pacientesOutput.dataNascimento(),
                pacientesOutput.nacionalidade(),
                pacientesOutput.cep(),
                pacientesOutput.endereco(),
                pacientesOutput.createdAt(),
                pacientesOutput.updatedAt()
        );
    }
}
