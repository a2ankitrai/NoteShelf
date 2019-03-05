package com.ank.noteshelf.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NsUniqueConstraintException extends RuntimeException {

    private static final long serialVersionUID = 2912684489546186555L;
    private String field;

    public NsUniqueConstraintException() {
	super();
    }

    public NsUniqueConstraintException(final String message, final Throwable cause) {
	super(message, cause);
    }

    public NsUniqueConstraintException(String field, final String message) {
	super(message);
	this.field = field;
    }

    public NsUniqueConstraintException(final Throwable cause) {
	super(cause);
    }
}
