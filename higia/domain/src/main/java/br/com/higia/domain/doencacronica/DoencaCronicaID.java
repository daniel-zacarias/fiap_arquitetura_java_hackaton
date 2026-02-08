package br.com.higia.domain.doencacronica;

import br.com.higia.domain.Identifier;
import br.com.higia.domain.utils.IdUtils;

import java.util.Objects;

public class DoencaCronicaID extends Identifier {
    private final String value;

    private DoencaCronicaID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static DoencaCronicaID unique() {
        return DoencaCronicaID.from(IdUtils.uuid());
    }

    public static DoencaCronicaID from(final String anId) {
        return new DoencaCronicaID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        DoencaCronicaID that = (DoencaCronicaID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
