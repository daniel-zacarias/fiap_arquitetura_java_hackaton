package br.com.higia.infrastructure.configuration.usecases;

import br.com.higia.application.prontuario.create.CreateProntuarioUseCase;
import br.com.higia.application.prontuario.create.DefaultCreateProntuarioUseCase;
import br.com.higia.application.prontuario.retrieve.get.DefaultGetProntuarioAtivoByPacienteIdUseCase;
import br.com.higia.application.prontuario.retrieve.get.DefaultGetProntuarioByIdUseCase;
import br.com.higia.application.prontuario.retrieve.get.GetProntuarioAtivoByPacienteIdUseCase;
import br.com.higia.application.prontuario.retrieve.get.GetProntuarioByIdUseCase;
import br.com.higia.application.prontuario.retrieve.list.DefaultListProntuariosUseCase;
import br.com.higia.application.prontuario.retrieve.list.ListProntuariosUseCase;
import br.com.higia.application.prontuario.update.DefaultUpdateProntuarioUseCase;
import br.com.higia.application.prontuario.update.UpdateProntuarioUseCase;
import br.com.higia.domain.prontuario.ProntuarioGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ProntuarioUseCaseConfig {

    private final ProntuarioGateway prontuarioGateway;

    public ProntuarioUseCaseConfig(final ProntuarioGateway prontuarioGateway) {
        this.prontuarioGateway = Objects.requireNonNull(prontuarioGateway);
    }

    @Bean
    public CreateProntuarioUseCase createProntuarioUseCase() {
        return new DefaultCreateProntuarioUseCase(prontuarioGateway);
    }

    @Bean
    public GetProntuarioByIdUseCase getProntuarioByIdUseCase() {
        return new DefaultGetProntuarioByIdUseCase(prontuarioGateway);
    }

    @Bean
    public GetProntuarioAtivoByPacienteIdUseCase getProntuarioAtivoByPacienteIdUseCase() {
        return new DefaultGetProntuarioAtivoByPacienteIdUseCase(prontuarioGateway);
    }

    @Bean
    public UpdateProntuarioUseCase updateProntuarioUseCase() {
        return new DefaultUpdateProntuarioUseCase(prontuarioGateway);
    }

    @Bean
    public ListProntuariosUseCase listProntuariosUseCase() {
        return new DefaultListProntuariosUseCase(prontuarioGateway);
    }
}
