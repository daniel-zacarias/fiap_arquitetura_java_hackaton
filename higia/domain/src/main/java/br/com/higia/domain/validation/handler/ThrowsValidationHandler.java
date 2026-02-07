package br.com.higia.domain.validation.handler;

import br.com.higia.domain.exceptions.DomainException;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.Error;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(ValidationHandler aHandler) {
        throw DomainException.with(aHandler.getErrors());
    }

    @Override
    public <T> T validate(ValidationHandler.Validation<T> aValidation) {
        try {
            return aValidation.validate();
        } catch (final Exception ex){
            throw DomainException.with(new Error(ex.getMessage()));
        }
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
