package br.com.higia.domain.paciente;

import br.com.higia.domain.ValueObject;


public final class CPF extends ValueObject {

    private final String value;

    private CPF(final String value) {

        this.value = normalize(value);
    }

    public static CPF with(final String value) {
        return new CPF(value);
    }

    public String getValue() {
        return value;
    }

    private String normalize(final String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("\\D", "");
    }

    public boolean isValid() {

        if (this.value == null) return false;

        if (this.value.length() != 11) return false;

        if (this.value.matches("(\\d)\\1{10}")) return false;

        try {
            int digito1 = calcularDigito(this.value.substring(0, 9), 10);
            int digito2 = calcularDigito(this.value.substring(0, 9) + digito1, 11);

            return this.value.equals(this.value.substring(0, 9) + digito1 + digito2);
        } catch (Exception e) {
            return false;
        }
    }

    private int calcularDigito(final String base, final int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;

        for (char c : base.toCharArray()) {
            soma += Character.getNumericValue(c) * peso--;
        }

        int resto = (soma * 10) % 11;
        return (resto == 10) ? 0 : resto;
    }

}
