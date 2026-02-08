package br.com.higia.application.pacientedoenca.create;

public record CreatePacienteDoencaCommand(
        String pacienteId,
        String doencaCronicaId,
        String dataDiagnostico) {
    public static CreatePacienteDoencaCommand with(
            final String pacienteId,
            final String doencaCronicaId,
            final String dataDiagnostico) {
        return new CreatePacienteDoencaCommand(
                pacienteId,
                doencaCronicaId,
                dataDiagnostico);
    }
}
