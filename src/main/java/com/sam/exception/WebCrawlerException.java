package com.sam.exception;

public class WebCrawlerException extends RuntimeException {

	private static final long serialVersionUID = -1959239616023797151L;
	
	private String exceptionCode;
	private String exceptionMessage;
	private Throwable exception;

	public WebCrawlerException(String exceptionCode, String exceptionMessage) {
		super(exceptionMessage);
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
	}
	
	public WebCrawlerException(String exceptionCode, String exceptionMessage, Throwable t) {
		super(exceptionMessage, t);
		this.exceptionCode = exceptionCode;
		this.exceptionMessage = exceptionMessage;
		this.exception = t;
	}

	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
