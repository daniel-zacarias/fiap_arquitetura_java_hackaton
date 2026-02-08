package br.com.higia.application.pacientedoenca.update;

public record UpdatePacienteDoencaCommand(
        String id,
        String dataDiagnostico) {
    public static UpdatePacienteDoencaCommand with(
            final String id,
            final String dataDiagnostico) {
        return new UpdatePacienteDoencaCommand(id, dataDiagnostico);
    }
}
