package br.com.higia.application.pacientedoenca.update;

public record UpdatePacienteDoencaCommand(
        String id,
        String dataDiagnostico,
        Boolean ativo) {
    public static UpdatePacienteDoencaCommand with(
            final String id,
            final String dataDiagnostico,
            final Boolean ativo) {
        return new UpdatePacienteDoencaCommand(id, dataDiagnostico, ativo);
    }
}
