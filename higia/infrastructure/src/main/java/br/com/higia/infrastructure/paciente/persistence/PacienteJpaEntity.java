package br.com.higia.infrastructure.paciente.persistence;

import br.com.higia.domain.paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static br.com.higia.domain.utils.IdUtils.uuid;

@Entity(name = "Paciente")
@Table(name = "paciente")
public class PacienteJpaEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "cep")
    private String cep;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    public PacienteJpaEntity() {
    }

    public PacienteJpaEntity(
            String id,
            String nome,
            String cpf,
            LocalDate dataNascimento,
            String nacionalidade,
            String cep,
            String endereco,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = uuid(id);
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.cep = cep;
        this.endereco = endereco;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static PacienteJpaEntity from(final Paciente paciente) {
        return new PacienteJpaEntity(
                paciente.getId().getValue(),
                paciente.getNome(),
                paciente.getCpf().getValue(),
                paciente.getDataNascimento(),
                paciente.getNacionalidade(),
                paciente.getCep(),
                paciente.getEndereco(),
                paciente.getCreatedAt(),
                paciente.getUpdatedAt()
        );
    }

    public Paciente toAggregate() {
        return Paciente.with(
                String.valueOf(this.id),
                this.nome,
                this.dataNascimento,
                this.cpf,
                this.nacionalidade,
                this.cep,
                this.endereco,
                this.createdAt,
                this.updatedAt
        );
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
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
