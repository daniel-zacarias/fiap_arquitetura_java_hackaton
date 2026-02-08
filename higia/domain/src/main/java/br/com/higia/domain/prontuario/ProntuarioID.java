package br.com.higia.domain.prontuario;

import br.com.higia.domain.Identifier;
import br.com.higia.domain.utils.IdUtils;

import java.util.Objects;

public class ProntuarioID extends Identifier {
    private final String value;

    private ProntuarioID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ProntuarioID unique() {
        return ProntuarioID.from(IdUtils.uuid());
    }

    public static ProntuarioID from(final String anId) {
        return new ProntuarioID(anId);
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
        ProntuarioID that = (ProntuarioID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
