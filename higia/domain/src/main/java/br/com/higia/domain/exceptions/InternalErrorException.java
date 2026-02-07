package br.com.higia.domain.exceptions;


public class InternalErrorException extends NoStackTraceException {
    protected InternalErrorException(final String message, final Throwable t) {
        super(message, t);
    }

    public static InternalErrorException with(final String anError, final Throwable t){
        return new InternalErrorException(anError, t);
    }

}
