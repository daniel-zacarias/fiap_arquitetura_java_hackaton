package br.com.higia.infrastructure.configuration.usecases;

import br.com.higia.application.evolucaoclinica.create.CreateEvolucaoClinicaUseCase;
import br.com.higia.application.evolucaoclinica.create.DefaultCreateEvolucaoClinicaUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.get.DefaultGetEvolucaoClinicaByIdUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.get.GetEvolucaoClinicaByIdUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.list.DefaultListEvolucoesClinicasUseCase;
import br.com.higia.application.evolucaoclinica.retrieve.list.ListEvolucoesClinicasUseCase;
import br.com.higia.application.evolucaoclinica.update.DefaultUpdateEvolucaoClinicaUseCase;
import br.com.higia.application.evolucaoclinica.update.UpdateEvolucaoClinicaUseCase;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinicaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class EvolucaoClinicaUseCaseConfig {

    private final EvolucaoClinicaGateway evolucaoClinicaGateway;

    public EvolucaoClinicaUseCaseConfig(final EvolucaoClinicaGateway evolucaoClinicaGateway) {
        this.evolucaoClinicaGateway = Objects.requireNonNull(evolucaoClinicaGateway);
    }

    @Bean
    public CreateEvolucaoClinicaUseCase createEvolucaoClinicaUseCase() {
        return new DefaultCreateEvolucaoClinicaUseCase(evolucaoClinicaGateway);
    }

    @Bean
    public GetEvolucaoClinicaByIdUseCase getEvolucaoClinicaByIdUseCase() {
        return new DefaultGetEvolucaoClinicaByIdUseCase(evolucaoClinicaGateway);
    }

    @Bean
    public UpdateEvolucaoClinicaUseCase updateEvolucaoClinicaUseCase() {
        return new DefaultUpdateEvolucaoClinicaUseCase(evolucaoClinicaGateway);
    }

    @Bean
    public ListEvolucoesClinicasUseCase listEvolucoesClinicasUseCase() {
        return new DefaultListEvolucoesClinicasUseCase(evolucaoClinicaGateway);
    }
}
