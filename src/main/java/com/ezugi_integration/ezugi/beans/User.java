package com.ezugi_integration.ezugi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ezugi_integration.ezugi.response.rest.ResponseController;

@Entity
@Table(name = "Users")
@Component
@Scope(value = "prototype")

public class User {
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private long uid;
	private String initialToken;
	private long initialTokenTimestamp;
	private String sessionToken;
	private long sessionTokenTimestamp;
	//private boolean sessionTokenExpired;
	private double balance;
	private String currency;
	private String language;
	private String VIP;
	private double bonusAmount;
	// private boolean bjAllowed;

	public User() {

	}

	public User(String firstName, String lastName, String userName, String password, long uid, String initialToken,
			long initialTokenTimestamp, String sessionToken, long sessionTokenTimestamp, 
			double balance, String currency, String language, String VIP, double bonusAmount) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.password = password;
		this.uid = uid;
		this.initialToken = initialToken;
		this.initialTokenTimestamp = initialTokenTimestamp;
		this.sessionToken = sessionToken;
		this.sessionTokenTimestamp = sessionTokenTimestamp;
		//this.sessionTokenExpired = sessionTokenExpired;
		this.balance = balance;
		this.currency = currency;
		this.language = language;
		this.VIP = VIP;
		this.bonusAmount = bonusAmount;
		// this.bjAllowed = bjAllowed;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	@Column
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getInitialToken() {
		return initialToken;
	}

	public void setInitialToken(String initialToken) {
		this.initialToken = initialToken;
	}

	@Column
	public long getInitialTokenTimestamp() {
		return initialTokenTimestamp;
	}

	public void setInitialTokenTimestamp(long initialTokenTimestamp) {
		this.initialTokenTimestamp = initialTokenTimestamp;
	}

	@Column
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@Column
	public long getSessionTokenTimestamp() {
		return sessionTokenTimestamp;
	}

	public void setSessionTokenTimestamp(long sessionTokenTimestamp) {
		this.sessionTokenTimestamp = sessionTokenTimestamp;
	}

//	@Column
//	public boolean isSessionTokenExpired() {
//		return sessionTokenExpired;
//	}
//
//	public void setSessionTokenExpired(boolean sessionTokenExpired) {
//		this.sessionTokenExpired = sessionTokenExpired;
//	}

	@Column
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = Double.parseDouble(ResponseController.formatMyDouble(balance));
	}

	@Column
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column
	public String getVIP() {
		return VIP;
	}

	public void setVIP(String VIP) {
		this.VIP = VIP;
	}

	@Column
	public double getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

//	@Column
//	public boolean isBjAllowed() {
//		return bjAllowed;
//	}
//
//	public void setBjAllowed(boolean bjAllowed) {
//		this.bjAllowed = bjAllowed;
//	}

}
