package com.maraujo.requestproject.sync.result;

public class Outcome<T> {
	
	
	private T result;
	private String errorMessage;
	private int errorCode;
	private boolean status;
	
	public boolean hasSuccess() {
		return this.status == true;
	}

	public boolean hasError() {
		return this.status == false;
	}

	
	public T result() {
		return this.result;
	}
	
	public String errorMessage() {
		return this.errorMessage;
	}
	
	public int errorCode() {
		return this.errorCode;
	}

}
