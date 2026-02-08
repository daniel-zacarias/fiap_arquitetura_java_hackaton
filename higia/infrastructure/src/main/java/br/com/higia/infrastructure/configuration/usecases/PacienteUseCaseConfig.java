package br.com.higia.infrastructure.configuration.usecases;

import br.com.higia.application.paciente.create.CreatePacienteUseCase;
import br.com.higia.application.paciente.create.DefaultCreatePacienteUseCase;
import br.com.higia.application.paciente.retrieve.get.DefaultGetPacienteByIdUseCase;
import br.com.higia.application.paciente.retrieve.get.GetPacienteByIdUseCase;
import br.com.higia.application.paciente.retrieve.list.DefaultListPacientesUseCase;
import br.com.higia.application.paciente.retrieve.list.ListPacientesUseCase;
import br.com.higia.application.paciente.update.DefaultUpdatePacienteUseCase;
import br.com.higia.application.paciente.update.UpdatePacienteUseCase;
import br.com.higia.domain.paciente.PacienteGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class PacienteUseCaseConfig {

    private final PacienteGateway pacienteGateway;

    public PacienteUseCaseConfig(final PacienteGateway pacienteGateway) {
        this.pacienteGateway = Objects.requireNonNull(pacienteGateway);
    }

    @Bean
    public CreatePacienteUseCase createPacienteUseCase() {
        return new DefaultCreatePacienteUseCase(pacienteGateway);
    }

    @Bean
    public GetPacienteByIdUseCase getPacienteByIdUseCase() {
        return new DefaultGetPacienteByIdUseCase(pacienteGateway);
    }

    @Bean
    public UpdatePacienteUseCase updatePacienteUseCase() {
        return new DefaultUpdatePacienteUseCase(pacienteGateway);
    }

    @Bean
    public ListPacientesUseCase listPacientesUseCase() {
        return new DefaultListPacientesUseCase(pacienteGateway);
    }

}
