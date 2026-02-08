package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.validation.Error;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Validator;

public class PacienteDoencaValidator extends Validator {

    private final PacienteDoenca pacienteDoenca;

    public PacienteDoencaValidator(final PacienteDoenca pacienteDoenca, final ValidationHandler handler) {
        super(handler);
        this.pacienteDoenca = pacienteDoenca;
    }

    @Override
    public void validate() {
        checkPacienteIdConstraints();
        checkDoencaCronicaIdConstraints();
    }

    private void checkPacienteIdConstraints() {
        if (this.pacienteDoenca.getPacienteId() == null) {
            this.validationHandler().append(new Error("'pacienteId' nao pode ser nulo"));
        }
    }

    private void checkDoencaCronicaIdConstraints() {
        if (this.pacienteDoenca.getDoencaCronicaId() == null) {
            this.validationHandler().append(new Error("'doencaCronicaId' nao pode ser nulo"));
        }
    }
}
