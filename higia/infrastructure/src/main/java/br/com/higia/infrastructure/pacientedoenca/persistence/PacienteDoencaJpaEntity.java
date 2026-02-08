package br.com.higia.infrastructure.pacientedoenca.persistence;

import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.pacientedoenca.PacienteDoenca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Entity(name = "PacienteDoenca")
@Table(name = "paciente_doenca")
public class PacienteDoencaJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "paciente_id", nullable = false, columnDefinition = "uuid")
    private UUID pacienteId;

    @Column(name = "doenca_cronica_id", nullable = false, columnDefinition = "uuid")
    private UUID doencaCronicaId;

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    public PacienteDoencaJpaEntity() {
    }

    public PacienteDoencaJpaEntity(
            final String id,
            final String pacienteId,
            final String doencaCronicaId,
            final LocalDate dataDiagnostico,
            final boolean ativo,
            final Instant createdAt) {
        this.id = uuid(id);
        this.pacienteId = uuid(pacienteId);
        this.doencaCronicaId = uuid(doencaCronicaId);
        this.dataDiagnostico = dataDiagnostico;
        this.ativo = ativo;
        this.createdAt = createdAt;
    }

    public static PacienteDoencaJpaEntity from(final PacienteDoenca pacienteDoenca) {
        return new PacienteDoencaJpaEntity(
                pacienteDoenca.getId().getValue(),
                pacienteDoenca.getPacienteId().getValue(),
                pacienteDoenca.getDoencaCronicaId().getValue(),
                pacienteDoenca.getDataDiagnostico(),
                pacienteDoenca.isAtivo(),
                pacienteDoenca.getCreatedAt());
    }

    public PacienteDoenca toAggregate() {
        return PacienteDoenca.with(
                String.valueOf(this.id),
                PacienteID.from(String.valueOf(this.pacienteId)),
                DoencaCronicaID.from(String.valueOf(this.doencaCronicaId)),
                this.dataDiagnostico,
                this.ativo,
                this.createdAt);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(UUID pacienteId) {
        this.pacienteId = pacienteId;
    }

    public UUID getDoencaCronicaId() {
        return doencaCronicaId;
    }

    public void setDoencaCronicaId(UUID doencaCronicaId) {
        this.doencaCronicaId = doencaCronicaId;
    }

    public LocalDate getDataDiagnostico() {
        return dataDiagnostico;
    }

    public void setDataDiagnostico(LocalDate dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
