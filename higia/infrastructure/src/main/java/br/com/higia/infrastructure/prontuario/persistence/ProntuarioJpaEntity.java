package br.com.higia.infrastructure.prontuario.persistence;

import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.prontuario.Prontuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Entity(name = "Prontuario")
@Table(name = "prontuario")
public class ProntuarioJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "paciente_id", nullable = false, columnDefinition = "uuid")
    private UUID pacienteId;

    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public ProntuarioJpaEntity() {
    }

    public ProntuarioJpaEntity(
            final String id,
            final String pacienteId,
            final boolean ativo,
            final Instant createdAt,
            final Instant updatedAt) {
        this.id = uuid(id);
        this.pacienteId = uuid(pacienteId);
        this.ativo = ativo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProntuarioJpaEntity from(final Prontuario prontuario) {
        return new ProntuarioJpaEntity(
                prontuario.getId().getValue(),
                prontuario.getPacienteId().getValue(),
                prontuario.isAtivo(),
                prontuario.getCreatedAt(),
                prontuario.getUpdatedAt());
    }

    public Prontuario toAggregate() {
        return Prontuario.with(
                String.valueOf(this.id),
                PacienteID.from(String.valueOf(this.pacienteId)),
                this.ativo,
                this.createdAt,
                this.updatedAt,
                List.of());
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
