package br.com.higia.domain.pacientedoenca;

import br.com.higia.domain.Identifier;
import br.com.higia.domain.utils.IdUtils;

import java.util.Objects;

public class PacienteDoencaID extends Identifier {
    private final String value;

    private PacienteDoencaID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static PacienteDoencaID unique() {
        return PacienteDoencaID.from(IdUtils.uuid());
    }

    public static PacienteDoencaID from(final String anId) {
        return new PacienteDoencaID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PacienteDoencaID that = (PacienteDoencaID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
