package com.gandalf.framework.constant;

import com.gandalf.framework.util.StringUtil;

public enum DeviceEnum {

	PC(1, "PC"), MOBILE(2, "移动端"), APP(3, "APP"), WECHAT(4, "微信");

	private int code;
	private String desc;

	private DeviceEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static String getDesc(int code) {
		for (DeviceEnum sourceEnum : DeviceEnum.values()) {
			if(sourceEnum.getCode() == code) {
				return sourceEnum.getDesc();
			}
		}
		return StringUtil.EMPTY;
	}
	
}
