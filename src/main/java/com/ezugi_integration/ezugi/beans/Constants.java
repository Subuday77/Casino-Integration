package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	public enum DATA_TYPES {
		token, debit_transaction_id, rollback_transaction_id, credit_transaction_id
	};
	
	public static final long OPERATORID = 13000100;
	public static final String HASHKEY = "e225095c-e113-4b27-bc0a-141d599b2748";

}
