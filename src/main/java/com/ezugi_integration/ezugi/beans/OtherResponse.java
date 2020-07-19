package com.ezugi_integration.ezugi.beans;

import static com.ezugi_integration.ezugi.beans.Constants.OPERATORID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_DEFAULT)
public class OtherResponse {

	@JsonInclude(Include.ALWAYS)
	private long operatorId = OPERATORID;
	private String uid;
	private String transactionId;
	private String token;
	private long roundId;
	private double balance = -99;
	private String currency;
	private int gameId;
	private int tableId;
	private double bonusAmount = -99;
	private int errorCode = -99;
	private String errorDescription;
	private long timestamp;

	public OtherResponse() {
		// TODO Auto-generated constructor stub
	}

	public OtherResponse(long operatorId, String uid, String transactionId, String token, long roundId, double balance,
			String currency, int gameId, int tableId, double bonusAmount, int errorCode, String errorDescription,
			long timestamp) {
		this.operatorId = operatorId;
		this.uid = uid;
		this.transactionId = transactionId;
		this.token = token;
		this.roundId = roundId;
		this.balance = balance;
		this.currency = currency;
		this.gameId = gameId;
		this.tableId = tableId;
		this.bonusAmount = bonusAmount;
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.timestamp = timestamp;
	}

	public long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(long operatorId) {
		this.operatorId = operatorId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getRoundId() {
		return roundId;
	}

	public void setRoundId(long roundId) {
		this.roundId = roundId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
