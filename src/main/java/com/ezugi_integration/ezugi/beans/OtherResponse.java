package com.ezugi_integration.ezugi.beans;

import static com.ezugi_integration.ezugi.beans.Constants.OPERATORID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@JsonInclude(Include.NON_DEFAULT)
public class OtherResponse {

	@JsonInclude(Include.ALWAYS)
	private long operatorId = OPERATORID;
	private String uid;
	private String transactionId;
	private String token;
	private long roundId;
	private BigDecimal balance = BigDecimal.valueOf(-99).setScale(2,RoundingMode.HALF_DOWN);
	private String currency;
	private int gameId;
	private int tableId;
	private BigDecimal bonusAmount = BigDecimal.valueOf(-99).setScale(2,RoundingMode.HALF_DOWN);
	private int errorCode = -99;
	private String errorDescription;
	private long timestamp;

	public OtherResponse() {
		// TODO Auto-generated constructor stub
	}

	public OtherResponse(long operatorId, String uid, String transactionId, String token, long roundId, BigDecimal balance,
			String currency, int gameId, int tableId, BigDecimal bonusAmount, int errorCode, String errorDescription,
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

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {this.balance = balance.setScale(2,RoundingMode.HALF_DOWN);
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

	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount.setScale(2,RoundingMode.HALF_DOWN);
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
