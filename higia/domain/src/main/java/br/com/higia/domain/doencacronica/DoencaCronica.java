package br.com.higia.domain.doencacronica;

import br.com.higia.domain.AggregateRoot;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.utils.InstantUtils;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.handler.Notification;

import java.time.Instant;

public class DoencaCronica extends AggregateRoot<DoencaCronicaID> {

    private String nome;
    private String cid10;
    private final Instant createdAt;
    private Instant updatedAt;

    protected DoencaCronica(
            final DoencaCronicaID id,
            final String nome,
            final String cid10,
            final Instant createdAt,
            final Instant updatedAt) {
        super(id);
        this.nome = nome;
        this.cid10 = cid10;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        selfValidate();
    }

    public static DoencaCronica newDoencaCronica(
            final String nome,
            final String cid10) {
        return new DoencaCronica(
                DoencaCronicaID.unique(),
                nome,
                cid10,
                InstantUtils.now(),
                InstantUtils.now());
    }

    public static DoencaCronica with(
            final String id,
            final String nome,
            final String cid10,
            final Instant createdAt,
            final Instant updatedAt) {
        return new DoencaCronica(
                DoencaCronicaID.from(id),
                nome,
                cid10,
                createdAt,
                updatedAt);
    }

    public static DoencaCronica with(final DoencaCronica doenca) {
        return new DoencaCronica(
                doenca.id,
                doenca.nome,
                doenca.cid10,
                doenca.createdAt,
                doenca.updatedAt);
    }

    public DoencaCronica update(
            final String nome,
            final String cid10) {
        this.nome = nome;
        this.cid10 = cid10;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar doenca cronica", notification);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {
        new DoencaCronicaValidator(this, handler).validate();
    }

    public String getNome() {
        return nome;
    }

    public String getCid10() {
        return cid10;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
