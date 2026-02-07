package br.com.higia.application.paciente.update;

public record UpdatePacienteCommand(
        String id,
        String nome,
        String cpf,
        String dataNascimento,
        String nacionalidade,
        String cep,
        String endereco) {
    public static UpdatePacienteCommand with(
            final String id,
            final String nome,
            final String cpf,
            final String dataNascimento,
            final String nacionalidade,
            final String cep,
            final String endereco) {
        return new UpdatePacienteCommand(
                id,
                nome,
                cpf,
                dataNascimento,
                nacionalidade,
                cep,
                endereco);
    }
}
