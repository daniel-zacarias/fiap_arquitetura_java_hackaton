package br.com.higia.application.paciente.retrieve.list;

import br.com.higia.domain.paciente.Paciente;
import br.com.higia.domain.pagination.Pagination;

public record ListPacientesOutput(
        int currentPage,
        int perPage,
        long total,
        java.util.List<PacienteItemOutput> items) {
    public static ListPacientesOutput from(final Pagination<Paciente> pagination) {
        return new ListPacientesOutput(
                pagination.currentPage(),
                pagination.perPage(),
                pagination.total(),
                pagination.items().stream()
                        .map(PacienteItemOutput::from)
                        .toList());
    }

    public record PacienteItemOutput(
            String id,
            String nome,
            String cpf,
            String nacionalidade) {
        public static PacienteItemOutput from(final Paciente paciente) {
            return new PacienteItemOutput(
                    paciente.getId().getValue(),
                    paciente.getNome(),
                    paciente.getCpf() != null ? paciente.getCpf().getValue() : null,
                    paciente.getNacionalidade());
        }
    }
}
