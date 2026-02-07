package br.com.higia.domain.paciente;

import br.com.higia.domain.Identifier;
import br.com.higia.domain.utils.IdUtils;

import java.util.Objects;
import java.util.UUID;

public class PacienteID extends Identifier {
    private final String value;

    private PacienteID (final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static PacienteID unique() {
        return PacienteID.from(IdUtils.uuid());
    }

    public static PacienteID from(final String anId){
        return new PacienteID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PacienteID that = (PacienteID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
