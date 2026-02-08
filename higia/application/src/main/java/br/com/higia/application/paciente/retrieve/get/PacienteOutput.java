package br.com.higia.application.paciente.retrieve.get;

import br.com.higia.domain.paciente.Paciente;

import static br.com.higia.domain.utils.DateUtils.format;

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
                format(paciente.getDataNascimento()),
                paciente.getCpf().getValue(),
                paciente.getNacionalidade(),
                paciente.getCep(),
                paciente.getEndereco(),
                paciente.getCreatedAt().toString(),
                 paciente.getUpdatedAt().toString());
    }
}
