package br.com.higia.application.doencacronica.retrieve.get;

import br.com.higia.domain.doencacronica.DoencaCronica;

public record DoencaCronicaOutput(
        String id,
        String nome,
        String cid10,
        String createdAt,
        String updatedAt) {
    public static DoencaCronicaOutput from(final DoencaCronica doenca) {
        return new DoencaCronicaOutput(
                doenca.getId().getValue(),
                doenca.getNome(),
                doenca.getCid10(),
                doenca.getCreatedAt() != null ? doenca.getCreatedAt().toString() : null,
                doenca.getUpdatedAt() != null ? doenca.getUpdatedAt().toString() : null);
    }
}
