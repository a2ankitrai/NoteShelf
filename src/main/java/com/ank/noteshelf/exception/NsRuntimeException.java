package com.ank.noteshelf.exception;

public class NsRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public NsRuntimeException() {
	super();
    }

    public NsRuntimeException(final String message, final Throwable cause) {
	super(message, cause);
    }

    public NsRuntimeException(final String message) {
	super(message);
    }

    public NsRuntimeException(final Throwable cause) {
	super(cause);
    }
}
