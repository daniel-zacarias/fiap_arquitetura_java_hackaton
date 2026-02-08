package br.com.higia.domain.evolucaoclinica;

import br.com.higia.domain.Identifier;
import br.com.higia.domain.utils.IdUtils;

import java.util.Objects;

public class EvolucaoClinicaID extends Identifier {
    private final String value;

    private EvolucaoClinicaID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static EvolucaoClinicaID unique() {
        return EvolucaoClinicaID.from(IdUtils.uuid());
    }

    public static EvolucaoClinicaID from(final String anId) {
        return new EvolucaoClinicaID(anId);
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
        EvolucaoClinicaID that = (EvolucaoClinicaID) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
