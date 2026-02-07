CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE paciente (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(150) NOT NULL,
    data_nascimento DATE NOT NULL,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F', 'O')),
    cpf VARCHAR(11) UNIQUE NOT NULL,
    nacionalidade VARCHAR(50),
    cep VARCHAR(8),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE doenca_cronica (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    cid10 VARCHAR(10) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);


CREATE TABLE paciente_doenca (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    doenca_cronica_id UUID NOT NULL,
    data_diagnostico DATE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_paciente_doenca_paciente
        FOREIGN KEY (paciente_id) REFERENCES paciente(id),

    CONSTRAINT fk_paciente_doenca_doenca
        FOREIGN KEY (doenca_cronica_id) REFERENCES doenca_cronica(id),

    CONSTRAINT uk_paciente_doenca UNIQUE (paciente_id, doenca_cronica_id)
);


CREATE TABLE prontuario (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    paciente_id UUID NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_prontuario_paciente
        FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);

CREATE TABLE evolucao_clinica (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    prontuario_id UUID NOT NULL,
    data_atendimento TIMESTAMP NOT NULL,

    pressao_sistolica INTEGER CHECK (pressao_sistolica > 0),
    pressao_diastolica INTEGER CHECK (pressao_diastolica > 0),

    glicemia INTEGER CHECK (glicemia > 0),

    peso NUMERIC(5,2) CHECK (peso > 0),
    observacoes TEXT,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_evolucao_prontuario
        FOREIGN KEY (prontuario_id) REFERENCES prontuario(id)
);

CREATE INDEX idx_paciente_cpf ON paciente(cpf);
CREATE INDEX idx_paciente_doenca_paciente ON paciente_doenca(paciente_id);
CREATE INDEX idx_evolucao_prontuario ON evolucao_clinica(prontuario_id);
CREATE INDEX idx_evolucao_data ON evolucao_clinica(data_atendimento);