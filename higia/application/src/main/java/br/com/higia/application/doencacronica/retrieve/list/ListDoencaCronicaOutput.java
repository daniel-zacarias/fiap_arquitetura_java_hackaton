package br.com.higia.application.doencacronica.retrieve.list;

import br.com.higia.domain.doencacronica.DoencaCronica;

public record ListDoencaCronicaOutput(
        String id,
        String nome,
        String cid10,
        String createdAt,
        String updatedAt) {
    public static ListDoencaCronicaOutput from(final DoencaCronica doenca) {
        return new ListDoencaCronicaOutput(
                doenca.getId().getValue(),
                doenca.getNome(),
                doenca.getCid10(),
                doenca.getCreatedAt() != null ? doenca.getCreatedAt().toString() : null,
                doenca.getUpdatedAt() != null ? doenca.getUpdatedAt().toString() : null);
    }
}
