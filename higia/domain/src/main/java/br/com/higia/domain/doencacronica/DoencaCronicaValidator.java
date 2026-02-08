package br.com.higia.domain.doencacronica;

import br.com.higia.domain.validation.Error;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Validator;

public class DoencaCronicaValidator extends Validator {
    private static final int NAME_MAX_LENGTH = 100;
    private static final int CID10_MAX_LENGTH = 10;

    private final DoencaCronica doenca;

    public DoencaCronicaValidator(final DoencaCronica doenca, final ValidationHandler handler) {
        super(handler);
        this.doenca = doenca;
    }

    @Override
    public void validate() {
        checkNomeConstraints();
        checkCid10Constraints();
    }

    private void checkNomeConstraints() {
        final var nome = this.doenca.getNome();
        if (nome == null) {
            this.validationHandler().append(new Error("'nome' não pode ser nulo"));
            return;
        }
        if (nome.isBlank()) {
            this.validationHandler().append(new Error("'nome' não pode ser vazio"));
            return;
        }
        if (nome.length() > NAME_MAX_LENGTH) {
            this.validationHandler().append(
                    new Error(String.format("'nome' não pode conter mais de %d caracteres", NAME_MAX_LENGTH)));
        }
    }

    private void checkCid10Constraints() {
        final var cid10 = this.doenca.getCid10();
        if (cid10 == null) {
            this.validationHandler().append(new Error("'cid10' não pode ser nulo"));
            return;
        }
        if (cid10.isBlank()) {
            this.validationHandler().append(new Error("'cid10' não pode ser vazio"));
            return;
        }
        if (cid10.length() > CID10_MAX_LENGTH) {
            this.validationHandler().append(
                    new Error(String.format("'cid10' não pode conter mais de %d caracteres", CID10_MAX_LENGTH)));
        }
    }
}
