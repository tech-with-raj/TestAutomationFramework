package com.api.constant;

public enum OEM {

	APPLE(1),GOOGLE(2);

	int code;

	OEM(int code) {
		
		this.code = code;
	}
	
	public int getcode() {
		
		return code;
	}
	
	
}
