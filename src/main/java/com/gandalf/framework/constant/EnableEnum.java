/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.constant;

/**
 * 类EnableEnum.java的实现描述：是否启用
 * 
 * @author zhuxiaobing 2014-8-14 下午05:02:23
 */
public enum EnableEnum {

	Y(1, "是"), N(0, "否");

	private int enable;
	private String desc;

	private EnableEnum(int enable, String desc) {
		this.enable = enable;
		this.desc = desc;
	}

	/**
	 * @return the enable
	 */
	public int getEnable() {
		return enable;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	public static EnableEnum getEnable(int enable) {
		for (EnableEnum enableEnum : EnableEnum.values()) {
			if (enableEnum.getEnable() == enable) {
				return enableEnum;
			}
		}
		return null;
	}
	
}
