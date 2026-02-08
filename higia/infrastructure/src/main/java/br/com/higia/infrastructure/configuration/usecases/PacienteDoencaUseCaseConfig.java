package br.com.higia.infrastructure.configuration.usecases;

import br.com.higia.application.pacientedoenca.create.CreatePacienteDoencaUseCase;
import br.com.higia.application.pacientedoenca.create.DefaultCreatePacienteDoencaUseCase;
import br.com.higia.application.pacientedoenca.retrieve.get.DefaultGetPacienteDoencaByIdUseCase;
import br.com.higia.application.pacientedoenca.retrieve.get.GetPacienteDoencaByIdUseCase;
import br.com.higia.application.pacientedoenca.retrieve.list.DefaultListPacienteDoencasUseCase;
import br.com.higia.application.pacientedoenca.retrieve.list.ListPacienteDoencasUseCase;
import br.com.higia.application.pacientedoenca.update.DefaultUpdatePacienteDoencaUseCase;
import br.com.higia.application.pacientedoenca.update.UpdatePacienteDoencaUseCase;
import br.com.higia.domain.pacientedoenca.PacienteDoencaGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PacienteDoencaUseCaseConfig {

    private final PacienteDoencaGateway pacienteDoencaGateway;

    public PacienteDoencaUseCaseConfig(final PacienteDoencaGateway pacienteDoencaGateway) {
        this.pacienteDoencaGateway = Objects.requireNonNull(pacienteDoencaGateway);
    }

    @Bean
    public CreatePacienteDoencaUseCase createPacienteDoencaUseCase() {
        return new DefaultCreatePacienteDoencaUseCase(pacienteDoencaGateway);
    }

    @Bean
    public UpdatePacienteDoencaUseCase updatePacienteDoencaUseCase() {
        return new DefaultUpdatePacienteDoencaUseCase(pacienteDoencaGateway);
    }

    @Bean
    public GetPacienteDoencaByIdUseCase getPacienteDoencaByIdUseCase() {
        return new DefaultGetPacienteDoencaByIdUseCase(pacienteDoencaGateway);
    }

    @Bean
    public ListPacienteDoencasUseCase listPacienteDoencasUseCase() {
        return new DefaultListPacienteDoencasUseCase(pacienteDoencaGateway);
    }
}
