package br.com.higia.infrastructure.evolucaoclinica.persistence;

import br.com.higia.domain.evolucaoclinica.EvolucaoClinica;
import br.com.higia.domain.prontuario.ProntuarioID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Entity(name = "EvolucaoClinica")
@Table(name = "evolucao_clinica")
public class EvolucaoClinicaJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "prontuario_id", nullable = false, columnDefinition = "uuid")
    private UUID prontuarioId;

    @Column(name = "data_atendimento")
    private Instant dataAtendimento;

    @Column(name = "pressao_sistolica")
    private Integer pressaoSistolica;

    @Column(name = "pressao_diastolica")
    private Integer pressaoDiastolica;

    @Column(name = "glicemia")
    private Integer glicemia;

    @Column(name = "peso")
    private BigDecimal peso;

    @Column(name = "observacoes")
    private String observacoes;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    public EvolucaoClinicaJpaEntity() {
    }

    public EvolucaoClinicaJpaEntity(
            final String id,
            final String prontuarioId,
            final Instant dataAtendimento,
            final Integer pressaoSistolica,
            final Integer pressaoDiastolica,
            final Integer glicemia,
            final BigDecimal peso,
            final String observacoes,
            final Instant createdAt) {
        this.id = uuid(id);
        this.prontuarioId = uuid(prontuarioId);
        this.dataAtendimento = dataAtendimento;
        this.pressaoSistolica = pressaoSistolica;
        this.pressaoDiastolica = pressaoDiastolica;
        this.glicemia = glicemia;
        this.peso = peso;
        this.observacoes = observacoes;
        this.createdAt = createdAt;
    }

    public static EvolucaoClinicaJpaEntity from(final EvolucaoClinica evolucaoClinica) {
        return new EvolucaoClinicaJpaEntity(
                evolucaoClinica.getId().getValue(),
                evolucaoClinica.getProntuarioId().getValue(),
                evolucaoClinica.getDataAtendimento(),
                evolucaoClinica.getPressaoSistolica(),
                evolucaoClinica.getPressaoDiastolica(),
                evolucaoClinica.getGlicemia(),
                evolucaoClinica.getPeso(),
                evolucaoClinica.getObservacoes(),
                evolucaoClinica.getCreatedAt());
    }

    public EvolucaoClinica toAggregate() {
        return EvolucaoClinica.with(
                String.valueOf(this.id),
                ProntuarioID.from(String.valueOf(this.prontuarioId)),
                this.dataAtendimento,
                this.pressaoSistolica,
                this.pressaoDiastolica,
                this.glicemia,
                this.peso,
                this.observacoes,
                this.createdAt);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProntuarioId() {
        return prontuarioId;
    }

    public void setProntuarioId(UUID prontuarioId) {
        this.prontuarioId = prontuarioId;
    }

    public Instant getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(Instant dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Integer getPressaoSistolica() {
        return pressaoSistolica;
    }

    public void setPressaoSistolica(Integer pressaoSistolica) {
        this.pressaoSistolica = pressaoSistolica;
    }

    public Integer getPressaoDiastolica() {
        return pressaoDiastolica;
    }

    public void setPressaoDiastolica(Integer pressaoDiastolica) {
        this.pressaoDiastolica = pressaoDiastolica;
    }

    public Integer getGlicemia() {
        return glicemia;
    }

    public void setGlicemia(Integer glicemia) {
        this.glicemia = glicemia;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
