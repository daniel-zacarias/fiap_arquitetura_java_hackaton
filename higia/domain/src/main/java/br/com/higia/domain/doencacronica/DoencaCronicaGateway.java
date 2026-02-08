package br.com.higia.domain.doencacronica;

import java.util.Optional;

public interface DoencaCronicaGateway {

    Optional<DoencaCronica> findById(DoencaCronicaID id);

}
