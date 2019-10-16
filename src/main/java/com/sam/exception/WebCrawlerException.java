package com.sam.exception;

public class WebCrawlerException extends RuntimeException {

	private static final long serialVersionUID = -1959239616023797151L;
	
	private ExceptionCodes exceptionCode;
	private Throwable exception;

	public WebCrawlerException(ExceptionCodes exceptionCode) {
		super(exceptionCode.getValue());
		this.exceptionCode = exceptionCode;
	}
	
	public WebCrawlerException(ExceptionCodes exceptionCode, Throwable t) {
		super(exceptionCode.getValue(), t);
		this.exceptionCode = exceptionCode;
		this.exception = t;
	}

	public ExceptionCodes getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(ExceptionCodes exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	
}
