package br.com.higia.domain.prontuario;

import br.com.higia.domain.validation.Error;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Validator;

public class ProntuarioValidator extends Validator {

    private final Prontuario prontuario;

    public ProntuarioValidator(final Prontuario prontuario, final ValidationHandler handler) {
        super(handler);
        this.prontuario = prontuario;
    }

    @Override
    public void validate() {
        checkPacienteIdConstraints();
    }

    private void checkPacienteIdConstraints() {
        if (this.prontuario.getPacienteId() == null) {
            this.validationHandler().append(new Error("'pacienteId' nao pode ser nulo"));
        }
    }
}
