package com.ezugi_integration.ezugi.beans;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import static com.ezugi_integration.ezugi.beans.Constants.OPERATORID;

@Component
@JsonInclude(Include.NON_DEFAULT)
public class AuthResponse {
	@JsonInclude(Include.ALWAYS)
	private long operatorId = OPERATORID;
	private int uid;
	private String nickName;
	private String token;
	private String playerTokenAtLaunch;
	private double balance;
	private String currency;
	private int vip;
	private int errorCode=-99;
	private String errorDescription;
	private long timestamp;

	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public AuthResponse(long operatorId, int uid, String nickName, String token, String playerTokenAtLaunch,
			double balance, String currency, int vip, int errorCode, String errorDescription, long timestamp) {
		this.operatorId = operatorId;
		this.uid = uid;
		this.nickName = nickName;
		this.token = token;
		this.playerTokenAtLaunch = playerTokenAtLaunch;
		this.balance = balance;
		this.currency = currency;
		this.vip = vip;
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

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
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

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
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
