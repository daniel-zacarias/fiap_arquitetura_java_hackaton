package br.com.higia.application.doencacronica.retrieve.get;

import br.com.higia.domain.doencacronica.DoencaCronicaGateway;

public class DefaultGetDoencaCronicaByIdUseCase extends DefaultDoencaCronicaGetByIdUseCase {

    public DefaultGetDoencaCronicaByIdUseCase(final DoencaCronicaGateway doencaCronicaGateway) {
        super(doencaCronicaGateway);
    }
}
