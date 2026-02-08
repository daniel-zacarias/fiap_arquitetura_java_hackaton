package br.com.higia.application.pacientedoenca.retrieve.list;

import br.com.higia.domain.pacientedoenca.PacienteDoenca;

import static br.com.higia.domain.utils.DateUtils.format;

public record ListPacienteDoencaOutput(
        String id,
        String pacienteId,
        String doencaCronicaId,
        String dataDiagnostico,
        boolean ativo,
        String createdAt
) {
    public static ListPacienteDoencaOutput from(final PacienteDoenca pacienteDoenca) {
        return new ListPacienteDoencaOutput(
                pacienteDoenca.getId().getValue(),
                pacienteDoenca.getPacienteId().getValue(),
                pacienteDoenca.getDoencaCronicaId().getValue(),
                format(pacienteDoenca.getDataDiagnostico()),
                pacienteDoenca.isAtivo(),
                pacienteDoenca.getCreatedAt() != null ? pacienteDoenca.getCreatedAt().toString() : null
        );
    }
}
