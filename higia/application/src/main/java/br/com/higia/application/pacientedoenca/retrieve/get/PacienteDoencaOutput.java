package br.com.higia.application.pacientedoenca.retrieve.get;

import br.com.higia.domain.pacientedoenca.PacienteDoenca;

import static br.com.higia.domain.utils.DateUtils.format;

public record PacienteDoencaOutput(
        String id,
        String pacienteId,
        String doencaCronicaId,
        String dataDiagnostico,
        boolean ativo,
        String createdAt) {
    public static PacienteDoencaOutput from(final PacienteDoenca pacienteDoenca) {
        return new PacienteDoencaOutput(
                pacienteDoenca.getId().getValue(),
                pacienteDoenca.getPacienteId().getValue(),
                pacienteDoenca.getDoencaCronicaId().getValue(),
                format(pacienteDoenca.getDataDiagnostico()),
                pacienteDoenca.isAtivo(),
                pacienteDoenca.getCreatedAt() != null ? pacienteDoenca.getCreatedAt().toString() : null);
    }
}
