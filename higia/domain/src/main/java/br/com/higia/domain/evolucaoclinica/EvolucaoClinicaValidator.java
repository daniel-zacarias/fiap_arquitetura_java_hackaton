package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.validation.Error;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Validator;

import java.math.BigDecimal;

public class EvolucaoClinicaValidator extends Validator {

    private final EvolucaoClinica evolucaoClinica;

    public EvolucaoClinicaValidator(final EvolucaoClinica evolucaoClinica, final ValidationHandler handler) {
        super(handler);
        this.evolucaoClinica = evolucaoClinica;
    }

    @Override
    public void validate() {
        checkProntuarioIdConstraints();
        checkDataAtendimentoConstraints();
        checkPressaoSistolicaConstraints();
        checkPressaoDiastolicaConstraints();
        checkGlicemiaConstraints();
        checkPesoConstraints();
    }

    private void checkProntuarioIdConstraints() {
        if (this.evolucaoClinica.getProntuarioId() == null) {
            this.validationHandler().append(new Error("'prontuarioId' nao pode ser nulo"));
        }
    }

    private void checkDataAtendimentoConstraints() {
        if (this.evolucaoClinica.getDataAtendimento() == null) {
            this.validationHandler().append(new Error("'dataAtendimento' nao pode ser nulo"));
        }
    }

    private void checkPressaoSistolicaConstraints() {
        if (isNotPositive(this.evolucaoClinica.getPressaoSistolica())) {
            this.validationHandler().append(new Error("'pressaoSistolica' deve ser positivo"));
        }
    }

    private void checkPressaoDiastolicaConstraints() {
        if (isNotPositive(this.evolucaoClinica.getPressaoDiastolica())) {
            this.validationHandler().append(new Error("'pressaoDiastolica' deve ser positivo"));
        }
    }

    private void checkGlicemiaConstraints() {
        if (isNotPositive(this.evolucaoClinica.getGlicemia())) {
            this.validationHandler().append(new Error("'glicemia' deve ser positivo"));
        }
    }

    private void checkPesoConstraints() {
        final var peso = this.evolucaoClinica.getPeso();
        if (peso != null && peso.compareTo(BigDecimal.ZERO) <= 0) {
            this.validationHandler().append(new Error("'peso' deve ser positivo"));
        }
    }

    private boolean isNotPositive(final Integer value) {
        return value != null && value <= 0;
    }
}
