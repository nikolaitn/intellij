package com.devopsbuddy.exceptions;

public class StripeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StripeException(Throwable th) {
		super(th);
	}
	
}
