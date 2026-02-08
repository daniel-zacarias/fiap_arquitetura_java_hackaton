package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.application.paciente.retrieve.get.PacienteOutput;
import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.pagination.Pagination;

import static br.com.higia.domain.utils.DateUtils.format;

public record ListPacientesOutput(
        String id,
        String nome,
        String dataNascimento,
        String cpf,
        String nacionalidade,
        String cep,
        String endereco,
        String createdAt,
        String updatedAt) {
    public static ListPacientesOutput from(final Paciente paciente) {
        return new ListPacientesOutput(
                paciente.getId().getValue(),
                paciente.getNome(),
                format(paciente.getDataNascimento()),
                paciente.getCpf() != null ? paciente.getCpf().getValue() : null,
                paciente.getNacionalidade(),
                paciente.getCep(),
                paciente.getEndereco(),
                paciente.getCreatedAt() != null ? paciente.getCreatedAt().toString() : null,
                paciente.getUpdatedAt() != null ? paciente.getUpdatedAt().toString() : null);
    }
}
