package br.com.higia.infrastructure.configuration.usecases;

import br.com.higia.application.doencacronica.retrieve.get.DefaultGetDoencaCronicaByIdUseCase;
import br.com.higia.application.doencacronica.retrieve.get.GetDoencaCronicaByIdUseCase;
import br.com.higia.application.doencacronica.retrieve.list.DefaultListDoencasCronicasUseCase;
import br.com.higia.application.doencacronica.retrieve.list.ListDoencasCronicasUseCase;
import br.com.higia.domain.doencacronica.DoencaCronicaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class DoencaCronicaUseCaseConfig {

    private final DoencaCronicaGateway doencaCronicaGateway;

    public DoencaCronicaUseCaseConfig(final DoencaCronicaGateway doencaCronicaGateway) {
        this.doencaCronicaGateway = Objects.requireNonNull(doencaCronicaGateway);
    }

    @Bean
    public GetDoencaCronicaByIdUseCase getDoencaCronicaByIdUseCase() {
        return new DefaultGetDoencaCronicaByIdUseCase(doencaCronicaGateway);
    }

    @Bean
    public ListDoencasCronicasUseCase listDoencasCronicasUseCase() {
        return new DefaultListDoencasCronicasUseCase(doencaCronicaGateway);
    }
}
