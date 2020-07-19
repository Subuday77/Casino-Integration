package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Component
@JsonInclude(Include.NON_DEFAULT)

public class EzugiRequest {
	private int operatorId;
	private String token;
	private int platformId = -99;
	private long timestamp;
	private int serverId;
	private String uid;
	private String transactionId;
	private long roundId;
	private int gameId;
	private int tableId;
	private String currency;
	private double debitAmount = -99;
	private double rollbackAmount = -99;
	private double creditAmount = -99;
	private int betTypeId;
	private String seatId;
	private String debitTrtansactionId;
	private int returnReason = -99;
	public boolean isEndRound;
	private String gameDataString;
	private String creditIndex;
	
	public EzugiRequest() {
		// TODO Auto-generated constructor stub
	}

	public EzugiRequest(int operatorId, String token, int platformId, long timestamp, int serverId, String uid,
			String transactionId, long roundId, int gameId, int tableId, String currency, double debitAmount,
			double rollbackAmount, double creditAmount, int betTypeId, String seatId, String debitTrtansactionId,
			int returnReason, boolean isEndRound, String gameDataString, String creditIndex) {
		this.operatorId = operatorId;
		this.token = token;
		this.platformId = platformId;
		this.timestamp = timestamp;
		this.serverId = serverId;
		this.uid = uid;
		this.transactionId = transactionId;
		this.roundId = roundId;
		this.gameId = gameId;
		this.tableId = tableId;
		this.currency = currency;
		this.debitAmount = debitAmount;
		this.rollbackAmount = rollbackAmount;
		this.creditAmount = creditAmount;
		this.betTypeId = betTypeId;
		this.seatId = seatId;
		this.debitTrtansactionId = debitTrtansactionId;
		this.returnReason = returnReason;
		this.isEndRound = isEndRound;
		this.gameDataString = gameDataString;
		this.creditIndex = creditIndex;
	}

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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

	public long getRoundId() {
		return roundId;
	}

	public void setRoundId(long roundId) {
		this.roundId = roundId;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}

	public double getRollbackAmount() {
		return rollbackAmount;
	}

	public void setRollbackAmount(double rollbackAmount) {
		this.rollbackAmount = rollbackAmount;
	}

	public double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public int getBetTypeId() {
		return betTypeId;
	}

	public void setBetTypeId(int betTypeId) {
		this.betTypeId = betTypeId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getDebitTrtansactionId() {
		return debitTrtansactionId;
	}

	public void setDebitTrtansactionId(String debitTrtansactionId) {
		this.debitTrtansactionId = debitTrtansactionId;
	}

	public int getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(int returnReason) {
		this.returnReason = returnReason;
	}

	
	public String getGameDataString() {
		return gameDataString;
	}

	public void setGameDataString(String gameDataString) {
		this.gameDataString = gameDataString;
	}

	public String getCreditIndex() {
		return creditIndex;
	}

	public void setCreditIndex(String creditIndex) {
		this.creditIndex = creditIndex;
	}
	
	
	
}
