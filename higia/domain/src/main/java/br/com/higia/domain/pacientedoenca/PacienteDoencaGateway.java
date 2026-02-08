package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pagination.Pagination;

import java.util.Optional;

public interface PacienteDoencaGateway {

    PacienteDoenca create(PacienteDoenca pacienteDoenca);

    Optional<PacienteDoenca> findById(PacienteDoencaID id);

    Pagination<PacienteDoenca> findAll(PacienteDoencaSearchQuery searchQuery);

    PacienteDoenca update(PacienteDoenca pacienteDoenca);
}
