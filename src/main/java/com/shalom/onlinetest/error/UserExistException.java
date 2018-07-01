package com.shalom.onlinetest.error;

public final class UserExistException extends RuntimeException {
	
	private static final long serialVersionUID = -8595522842009704419L;

	public UserExistException() {
		super();
	}
	
	public UserExistException(final String errorMessage) {
		super(errorMessage);
	}
}
