package com.sam.exception;

public enum ExceptionCodes {

	INVALID_RUNTIME_ARGUMENTS("INVALID_RUNTIME_ARGUMENTS"), 
	INVALID_START_PAGE("INVALID_START_PAGE:"),
	INVALID_FILE_PATH("INVALID_FILE_PATH:"),
	PROCESSING_INTERNAL_ERROR("PROCESSING_INTERNAL_ERROR"),
	JSON_READER_ERROR("JSON_READER_ERROR"),
	JSON_CONVERION_ERROR("JSON_CONVERION_ERROR"),
	CRAWL_INTERNAL_ERROR("CRAWL_INTERNAL_ERROR"),
	CRAWL_DEPTH_INTERNAL_ERROR("CRAWL_DEPTH_INTERNAL_ERROR"),
	SECURITY_ERROR("SECURITY_ERROR"),
	THREAD_INTERRUPT_ERROR("THREAD_INTERRUPT_ERROR"),
	SHUTDOWN_SERVICE_ERROR("SHUTDOWN_SERVICE_ERROR");
	
	private final String value;
	
	private ExceptionCodes(final String value) {
        this.value = value;
    }

	public String getValue() {
		return value;
	}
	
	@Override
    public String toString() {
        return this.getValue();
    }
}
