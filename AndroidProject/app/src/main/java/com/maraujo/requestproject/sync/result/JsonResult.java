package com.maraujo.requestproject.sync.result;

public class JsonResult<T> {

	private T result;
	private boolean status;
	
	public JsonResult(){
		super();
	}
	
	public JsonResult(T result) {
		super();
		this.result = result;
		this.status = true;
	}
	
	
	public boolean getStatus() {
		return status;
	}

	protected void setStatus(boolean status) {
		this.status = status;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	
	// Helpers
	public static <T> JsonResult<T> success(T result){
		return new JsonResult<T>(result); 
	}

	
	
}
