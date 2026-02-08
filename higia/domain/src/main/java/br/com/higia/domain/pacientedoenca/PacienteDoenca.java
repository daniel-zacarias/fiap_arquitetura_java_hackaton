package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.AggregateRoot;
import br.com.higia.domain.doencacronica.DoencaCronicaID;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.paciente.PacienteID;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.handler.Notification;

import java.time.Instant;
import java.time.LocalDate;

public class PacienteDoenca extends AggregateRoot<PacienteDoencaID> {

    private final PacienteID pacienteId;

    private final DoencaCronicaID doencaCronicaId;

    private LocalDate dataDiagnostico;

    private boolean ativo;

    private final Instant createdAt;

    protected PacienteDoenca(
            final PacienteDoencaID id,
            final PacienteID pacienteId,
            final DoencaCronicaID doencaCronicaId,
            final LocalDate dataDiagnostico,
            final boolean ativo,
            final Instant createdAt) {
        super(id);
        this.pacienteId = pacienteId;
        this.doencaCronicaId = doencaCronicaId;
        this.dataDiagnostico = dataDiagnostico;
        this.ativo = ativo;
        this.createdAt = createdAt;
        selfValidate();
    }

    public static PacienteDoenca newPacienteDoenca(
            final PacienteID pacienteId,
            final DoencaCronicaID doencaCronicaId,
            final LocalDate dataDiagnostico) {
        final var now = Instant.now();
        return new PacienteDoenca(
                PacienteDoencaID.unique(),
                pacienteId,
                doencaCronicaId,
                dataDiagnostico,
                true,
                now);
    }

    public static PacienteDoenca with(
            final String id,
            final PacienteID pacienteId,
            final DoencaCronicaID doencaCronicaId,
            final LocalDate dataDiagnostico,
            final boolean ativo,
            final Instant createdAt) {
        return new PacienteDoenca(
                PacienteDoencaID.from(id),
                pacienteId,
                doencaCronicaId,
                dataDiagnostico,
                ativo,
                createdAt);
    }

    public static PacienteDoenca with(final PacienteDoenca pacienteDoenca) {
        return new PacienteDoenca(
                pacienteDoenca.id,
                pacienteDoenca.pacienteId,
                pacienteDoenca.doencaCronicaId,
                pacienteDoenca.dataDiagnostico,
                pacienteDoenca.ativo,
                pacienteDoenca.createdAt);
    }

    public PacienteDoenca update(final LocalDate dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
        selfValidate();
        return this;
    }

    public PacienteDoenca ativar() {
        this.ativo = true;
        selfValidate();
        return this;
    }

    public PacienteDoenca desativar() {
        this.ativo = false;
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar paciente doenca", notification);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PacienteDoencaValidator(this, handler).validate();
    }

    public PacienteID getPacienteId() {
        return pacienteId;
    }

    public DoencaCronicaID getDoencaCronicaId() {
        return doencaCronicaId;
    }

    public LocalDate getDataDiagnostico() {
        return dataDiagnostico;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
