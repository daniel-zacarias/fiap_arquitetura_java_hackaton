package br.com.higia.domain.prontuario;

import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;

import java.util.Optional;

public interface ProntuarioGateway {

    Prontuario create(Prontuario prontuario);

    Optional<Prontuario> findById(ProntuarioID id);

    Optional<Prontuario> findAtivoByPacienteId(PacienteID pacienteId);

    Pagination<Prontuario> findAll(ProntuarioSearchQuery searchQuery);

    Prontuario update(Prontuario prontuario);
}
