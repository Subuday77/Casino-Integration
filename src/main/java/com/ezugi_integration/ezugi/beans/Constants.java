package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public enum DATA_TYPES {
		token, debit_transaction_id, rollback_transaction_id, credit_transaction_id
	}

	public static final long OPERATORID = requiredLong("CASINO_OPERATOR_ID");
	public static final String HASHKEY = required("CASINO_HASH_KEY");

	private static String required(String name) {
		String value = System.getenv(name);
		if (value == null || value.isBlank()) {
			throw new IllegalStateException(name + " must be configured");
		}
		return value;
	}

	private static long requiredLong(String name) {
		return Long.parseLong(required(name));
	}
}
