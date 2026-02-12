package br.com.higia.domain.paciente;

import br.com.higia.domain.AggregateRoot;
import br.com.higia.domain.exceptions.NotificationException;
import br.com.higia.domain.utils.InstantUtils;
import br.com.higia.domain.validation.ValidationHandler;
import br.com.higia.domain.validation.handler.Notification;

import java.time.Instant;
import java.time.LocalDate;

public class Paciente extends AggregateRoot<PacienteID> {

    private String nome;

    private LocalDate dataNascimento;

    private CPF cpf;

    private String nacionalidade;

    private String cep;

    private String endereco;

    private final Instant createdAt;

    private Instant updatedAt;

    protected Paciente(
            final PacienteID pacienteID,
            final String nome,
            final LocalDate dataNascimento,
            final String cpf,
            final String nacionalidade,
            final String cep,
            final String endereco,
            final Instant createdAt,
            final Instant updatedAt
            ) {
        super(pacienteID);
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = CPF.with(cpf);
        this.nacionalidade = nacionalidade;
        this.cep = cep;
        this.endereco = endereco;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        selfValidate();
    }

    public static Paciente newPaciente(
            final String nome,
            final LocalDate dataNascimento,
            final String cpf,
            final String nacionalidade,
            final String cep,
            final String endereco
    ) {
        return new Paciente(
                PacienteID.unique(),
                nome,
                dataNascimento,
                cpf,
                nacionalidade,
                cep,
                endereco,
                InstantUtils.now(),
                InstantUtils.now()
        );
    }

    public static Paciente with(
            final String id,
            final String nome,
            final LocalDate dataNascimento,
            final String cpf,
            final String nacionalidade,
            final String cep,
            final String endereco,
            final Instant createdAt,
            final Instant updatedAt
    ) {
        return new Paciente(
                PacienteID.from(id),
                nome,
                dataNascimento,
                cpf,
                nacionalidade,
                cep,
                endereco,
                createdAt,
                updatedAt
        );
    }

    public static Paciente with(
            final Paciente paciente) {
        return new Paciente(
                paciente.id,
                paciente.nome,
                paciente.dataNascimento,
                paciente.cpf.getValue(),
                paciente.nacionalidade,
                paciente.cep,
                paciente.endereco,
                paciente.createdAt,
                paciente.updatedAt
        );
    }

    public Paciente update(
            final String nome,
            final LocalDate dataNascimento,
            final String cpf,
            final String nacionalidade,
            final String cep,
            final String endereco
    ) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = CPF.with(cpf);
        this.nacionalidade = nacionalidade;
        this.cep = cep;
        this.endereco = endereco;
        this.updatedAt = InstantUtils.now();
        selfValidate();
        return this;
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar paciente", notification);
        }
    }

    @Override
    public void validate(ValidationHandler handler) {
        new PacienteValidator(this, handler).validate();
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public CPF getCpf() {
        return cpf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public String getCep() {
        return cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


}
