package br.com.higia.domain.prontuario;

import br.com.higia.domain.AggregateRoot;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.validation.Error;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.handler.Notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Prontuario extends AggregateRoot<ProntuarioID> {

    private final PacienteID pacienteId;

    private boolean ativo;

    private final Instant createdAt;

    private Instant updatedAt;

    private final List<EvolucaoClinica> evolucoes;

    protected Prontuario(
            final ProntuarioID id,
            final PacienteID pacienteId,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt,
            final List<EvolucaoClinica> evolucoes) {
        super(id);
        this.pacienteId = pacienteId;
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.evolucoes = evolucoes == null ? new ArrayList<>() : new ArrayList<>(evolucoes);
        selfValidate();
    }

    public static Prontuario newProntuario(final PacienteID pacienteId) {
        final var now = Instant.now();
        return new Prontuario(
                ProntuarioID.unique(),
                pacienteId,
                true,
                now,
                now,
                List.of());
    }

    public static Prontuario with(
            final String id,
            final PacienteID pacienteId,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt,
            final List<EvolucaoClinica> evolucoes) {
        return new Prontuario(
                ProntuarioID.from(id),
                pacienteId,
                ativo,
                createdAt,
                updatedAt,
                evolucoes);
    }

    public static Prontuario with(final Prontuario prontuario) {
        return new Prontuario(
                prontuario.id,
                prontuario.pacienteId,
                prontuario.ativo,
                prontuario.createdAt,
                prontuario.updatedAt,
                prontuario.evolucoes);
    }

    public Prontuario ativar() {
        this.ativo = true;
        this.updatedAt = Instant.now();
        selfValidate();
        return this;
    }

    public Prontuario desativar() {
        this.ativo = false;
        this.updatedAt = Instant.now();
        selfValidate();
        return this;
    }

    public EvolucaoClinica registrarEvolucao(final EvolucaoClinica evolucao) {
        ensureAtivo();
        if (evolucao == null) {
            throw notificationExceptionWithError("evolucao clinica nao pode ser nula");
        }
        if (!this.id.equals(evolucao.getProntuarioId())) {
            throw notificationExceptionWithError("evolucao clinica nao pertence ao prontuario");
        }
        this.evolucoes.add(evolucao);
        this.updatedAt = Instant.now();
        return evolucao;
    }

    public EvolucaoClinica registrarEvolucao(
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final java.math.BigDecimal peso,
            final String observacoes) {
        final var evolucao = EvolucaoClinica.newEvolucaoClinica(
                this.id,
                dataAtendimento,
                pressaoSistolica,
                pressaoDiastolica,
                glicemia,
                peso,
                observacoes);
        return registrarEvolucao(evolucao);
    }

    private void ensureAtivo() {
        if (!this.ativo) {
            throw notificationExceptionWithError("prontuario deve estar ativo");
        }
    }

    private NotificationException notificationExceptionWithError(final String message) {
        return new NotificationException("Falha ao registrar evolucao clinica",
                Notification.create(new Error(message)));
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar prontuario", notification);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {
        new ProntuarioValidator(this, handler).validate();
    }

    public PacienteID getPacienteId() {
        return pacienteId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public List<EvolucaoClinica> getEvolucoes() {
        return List.copyOf(evolucoes);
    }
}
