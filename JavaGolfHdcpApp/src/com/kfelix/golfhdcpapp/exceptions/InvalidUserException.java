package com.kfelix.golfhdcpapp.exceptions;

public class InvalidUserException extends Exception {

	static final long serialVersionUID = 1L;
	
	public InvalidUserException() {
		// TODO Auto-generated constructor stub
	}

	public InvalidUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
