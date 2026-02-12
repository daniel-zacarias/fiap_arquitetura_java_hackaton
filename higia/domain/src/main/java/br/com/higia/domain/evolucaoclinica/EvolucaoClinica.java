package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.AggregateRoot;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.prontuario.ProntuarioID;
import br.com.higia.domain.utils.InstantUtils;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.handler.Notification;

import java.math.BigDecimal;
import java.time.Instant;

public class EvolucaoClinica extends AggregateRoot<EvolucaoClinicaID> {

    private final ProntuarioID prontuarioId;

    private  Instant dataAtendimento;

    private Integer pressaoSistolica;

    private  Integer pressaoDiastolica;

    private  Integer glicemia;

    private  BigDecimal peso;

    private  String observacoes;

    private final Instant createdAt;

    protected EvolucaoClinica(
            final EvolucaoClinicaID id,
            final ProntuarioID prontuarioId,
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes,
            final Instant createdAt) {
        super(id);
        this.prontuarioId = prontuarioId;
        this.dataAtendimento = dataAtendimento;
        this.pressaoSistolica = pressaoSistolica;
        this.pressaoDiastolica = pressaoDiastolica;
        this.glicemia = glicemia;
        this.peso = peso;
        this.observacoes = observacoes;
        this.createdAt = createdAt;
        selfValidate();
    }

    public static EvolucaoClinica newEvolucaoClinica(
            final ProntuarioID prontuarioId,
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes) {
        return new EvolucaoClinica(
                EvolucaoClinicaID.unique(),
                prontuarioId,
                dataAtendimento,
                pressaoSistolica,
                pressaoDiastolica,
                glicemia,
                peso,
                observacoes,
                InstantUtils.now());
    }

    public static EvolucaoClinica with(
            final String id,
            final ProntuarioID prontuarioId,
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes,
            final Instant createdAt) {
        return new EvolucaoClinica(
                EvolucaoClinicaID.from(id),
                prontuarioId,
                dataAtendimento,
                pressaoSistolica,
                pressaoDiastolica,
                glicemia,
                peso,
                observacoes,
                createdAt);
    }

    public EvolucaoClinica update(
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes) {
        this.dataAtendimento = dataAtendimento;
        this.pressaoSistolica = pressaoSistolica;
        this.pressaoDiastolica = pressaoDiastolica;
        this.glicemia = glicemia;
        this.peso = peso;
        this.observacoes = observacoes;
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar evolucao clinica", notification);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {
        new EvolucaoClinicaValidator(this, handler).validate();
    }

    public ProntuarioID getProntuarioId() {
        return prontuarioId;
    }

    public Instant getDataAtendimento() {
        return dataAtendimento;
    }

    public Integer getPressaoSistolica() {
        return pressaoSistolica;
    }

    public Integer getPressaoDiastolica() {
        return pressaoDiastolica;
    }

    public Integer getGlicemia() {
        return glicemia;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
