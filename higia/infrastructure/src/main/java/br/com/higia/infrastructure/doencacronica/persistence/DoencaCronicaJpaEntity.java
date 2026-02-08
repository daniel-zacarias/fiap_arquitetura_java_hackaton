package br.com.higia.infrastructure.doencacronica.persistence;

import br.com.higia.domain.doencacronica.DoencaCronica;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Entity(name = "DoencaCronica")
@Table(name = "doenca_cronica")
public class DoencaCronicaJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cid10", nullable = false, unique = true)
    private String cid10;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public DoencaCronicaJpaEntity() {
    }

    public DoencaCronicaJpaEntity(
            final String id,
            final String nome,
            final String cid10,
            final Instant createdAt,
            final Instant updatedAt) {
        this.id = uuid(id);
        this.nome = nome;
        this.cid10 = cid10;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static DoencaCronicaJpaEntity from(final DoencaCronica doenca) {
        return new DoencaCronicaJpaEntity(
                doenca.getId().getValue(),
                doenca.getNome(),
                doenca.getCid10(),
                doenca.getCreatedAt(),
                doenca.getUpdatedAt());
    }

    public DoencaCronica toAggregate() {
        return DoencaCronica.with(
                String.valueOf(this.id),
                this.nome,
                this.cid10,
                this.createdAt,
                this.updatedAt);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCid10() {
        return cid10;
    }

    public void setCid10(String cid10) {
        this.cid10 = cid10;
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
