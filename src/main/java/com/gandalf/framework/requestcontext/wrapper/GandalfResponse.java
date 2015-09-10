/*
 * Copyright 2010-2014 onsean.com All right reserved. This software is the confidential and proprietary information of
 * onsean.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with onsean.com.
 */
package com.gandalf.framework.requestcontext.wrapper;

import java.io.IOException;

/**
 * 类GandalfResponse.java的实现描述：自定义Response接口
 * 
 * @author gandalf 2014-8-4 下午05:07:57
 */
public interface GandalfResponse {

    /**
     * 设置是否采用buffering机制
     * 
     * @param buffering
     */
    public void setBuffering(boolean buffering);

    /**
     * 提交response
     */
    public void commit() throws IOException;
}
