package br.com.higia.application.paciente.retrieve.get;

import br.com.higia.domain.paciente.Paciente;

public record PacienteOutput(
        String id,
        String nome,
        String dataNascimento,
        String cpf,
        String nacionalidade,
        String cep,
        String endereco,
        String createdAt,
        String updatedAt) {
    public static PacienteOutput from(final Paciente paciente) {
        return new PacienteOutput(
                paciente.getId().getValue(),
                paciente.getNome(),
                paciente.getDataNascimento() != null ? paciente.getDataNascimento().toString() : null,
                paciente.getCpf() != null ? paciente.getCpf().getValue() : null,
                paciente.getNacionalidade(),
                paciente.getCep(),
                paciente.getEndereco(),
                paciente.getCreatedAt() != null ? paciente.getCreatedAt().toString() : null,
                paciente.getUpdatedAt() != null ? paciente.getUpdatedAt().toString() : null);
    }
}
