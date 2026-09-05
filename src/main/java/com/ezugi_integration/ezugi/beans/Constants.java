package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

@Component
public class Constants {

	public enum DATA_TYPES {
		token, debit_transaction_id, rollback_transaction_id, credit_transaction_id
	}

	public static long operatorId() {
		String value = System.getenv("CASINO_OPERATOR_ID");
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("CASINO_OPERATOR_ID must be configured");
		}
		return Long.parseLong(value);
	}

	public static String hashKey() {
		String value = System.getenv("CASINO_HASH_KEY");
		if (value == null || value.isBlank()) {
			throw new IllegalStateException("CASINO_HASH_KEY must be configured");
		}
		return value;
	}
}
