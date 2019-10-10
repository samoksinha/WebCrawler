package com.sam.model;

public enum Response {
	
	SUCCESS("Success:"), 
	SKIP("Skipped:"),
	ERROR("Error:");
	
	private final String value;
	
	private Response(final String value) {
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
