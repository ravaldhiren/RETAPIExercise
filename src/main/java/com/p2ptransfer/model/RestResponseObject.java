package com.p2ptransfer.model;

public class RestResponseObject {

	private String code;
	private Object data;
	private String details;

	public RestResponseObject(User data, String error, String code) {
		this.data = data;
		this.details = error;
		this.code = code;

	}

	public String getCode() {
		return code;
	}

	public String getDetails() {
		return details;
	}

	public Object getData() {
		return data;
	}
}
