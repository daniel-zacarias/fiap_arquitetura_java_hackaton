package br.com.higia.application.paciente.create;

public record CreatePacienteCommand(
        String nome,
        String cpf,
        String dataNascimento,
        String nacionalidade,
        String cep,
        String endereco
) {
    public static CreatePacienteCommand with(
            final String nome,
            final String cpf,
            final String dataNascimento,
            final String nacionalidade,
            final String cep,
            final String endereco
    ) {
        return new CreatePacienteCommand(
                nome,
                cpf,
                dataNascimento,
                nacionalidade,
                cep,
                endereco
        );
    }
}
