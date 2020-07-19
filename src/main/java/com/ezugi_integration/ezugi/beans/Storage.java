package com.ezugi_integration.ezugi.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ezugi_integration.ezugi.beans.Constants.DATA_TYPES;

import static com.ezugi_integration.ezugi.beans.Constants.DATA_TYPES;

@Entity
@Table(name = "Storage")
@Component
@Scope(value = "prototype")
public class Storage {

	private long id;
	private String uid;
	private String data;
	@Enumerated(EnumType.STRING)
	private DATA_TYPES dataType;
	private double amount;
	private boolean credited = false;
	private long timestamp;

	public Storage() {
		// TODO Auto-generated constructor stub
	}

	public Storage(long id, String uid, String data, DATA_TYPES dataType, double amount, boolean credited,
			long timestamp) {
		this.id = id;
		this.uid = uid;
		this.data = data;
		this.dataType = dataType;
		this.amount = amount;
		this.credited = credited;
		this.timestamp = timestamp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column
	@Enumerated(EnumType.STRING)
	public DATA_TYPES getDataType() {
		return dataType;
	}

	public void setDataType(DATA_TYPES dataType) {
		this.dataType = dataType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isCredited() {
		return credited;
	}

	public void setCredited(boolean credited) {
		this.credited = credited;
	}

	@Column
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
