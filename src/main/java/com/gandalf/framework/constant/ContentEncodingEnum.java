package com.gandalf.framework.constant;

public enum ContentEncodingEnum {
	
	deflate, gzip, br;
	
	public static ContentEncodingEnum getEnum(String contentEncoding) {
		for (ContentEncodingEnum ceEnum : ContentEncodingEnum.values()) {
			if(ceEnum.name().equalsIgnoreCase(contentEncoding)) {
				return ceEnum;
			}
		}
		return null;
	}
	
}
