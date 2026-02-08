package br.com.higia.domain.utils;

import br.com.higia.domain.exceptions.DomainException;
import br.com.higia.domain.exceptions.InternalErrorException;
import br.com.higia.domain.validation.Error;

import java.util.UUID;

public class IdUtils {
    private IdUtils() {

    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static UUID uuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (Exception e) {
            throw DomainException.with(new Error("UUID inválido: " + id));
        }
    }
}
