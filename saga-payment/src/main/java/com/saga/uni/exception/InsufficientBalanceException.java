package com.saga.uni.exception;

public class InsufficientBalanceException extends Exception {

	private static final long serialVersionUID = 1L;

    public InsufficientBalanceException(Double balance, double value) {
        super(String.format(
            "Account has a balance of %.2f, but it tried to withdraw %.2f", 
            balance, value));
	}
}
