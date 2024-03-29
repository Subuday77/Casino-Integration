package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.ezugi_integration.ezugi.beans.Constants.OPERATORID;

@Component
@JsonInclude(Include.NON_DEFAULT)
public class AuthResponse {
	@JsonInclude(Include.ALWAYS)
	private long operatorId = OPERATORID;
	private String uid;
	private String nickName;
	private String token;
	private String playerTokenAtLaunch;
	private BigDecimal balance;
	private String currency;
	private String VIP;
	private int errorCode=-99;
	private String errorDescription;
	private long timestamp;

	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(long operatorId, String uid, String nickName, String token, String playerTokenAtLaunch,
			BigDecimal balance, String currency, String VIP, int errorCode, String errorDescription, long timestamp) {
		this.operatorId = operatorId;
		this.uid = uid;
		this.nickName = nickName;
		this.token = token;
		this.playerTokenAtLaunch = playerTokenAtLaunch;
		this.balance = balance;
		this.currency = currency;
		this.VIP = VIP;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPlayerTokenAtLaunch() {
		return playerTokenAtLaunch;
	}

	public void setPlayerTokenAtLaunch(String playerTokenAtLaunch) {
		this.playerTokenAtLaunch = playerTokenAtLaunch;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance.setScale(2, RoundingMode.HALF_DOWN);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getVIP() {
		return VIP;
	}

	public void setVIP(String VIP) {
		this.VIP = VIP;
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
