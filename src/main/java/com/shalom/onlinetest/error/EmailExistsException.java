package com.shalom.onlinetest.error;

public final class EmailExistsException extends RuntimeException {
	
	private static final long serialVersionUID = -8595522842009704419L;

	public EmailExistsException() {
		super();
	}
	
	public EmailExistsException(final String errorMessage) {
		super(errorMessage);
	}
}
