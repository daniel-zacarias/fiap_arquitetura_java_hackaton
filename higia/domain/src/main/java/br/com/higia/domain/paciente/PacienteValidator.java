package br.com.higia.domain.paciente;

import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Validator;
import br.com.higia.domain.validation.Error;

public class PacienteValidator extends Validator {
    private final static int NAME_MAX_LENGTH = 100;
    private final Paciente paciente;

    public PacienteValidator(final Paciente paciente, final ValidationHandler aHandler) {
        super(aHandler);
        this.paciente = paciente;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkCpfConstraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.paciente.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio"));
            return;
        }
        if (nome.length() > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error(String.format("'nome' não pode conter mais de %d caracteres", NAME_MAX_LENGTH)));
        }
    }

    private void checkCpfConstraints() {
        final var cpf = this.paciente.getCpf();
        if (!cpf.isValid()) {
            this.validationHandler().append(new Error("cpf não é valido"));
        }
    }
}
