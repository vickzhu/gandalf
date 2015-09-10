package com.gandalf.framework.ibatis;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Types;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * 处理有可能写入中文的字段
 * 
 * @author gandalf
 */
public class CnStringTypeHandler implements TypeHandlerCallback {

    // 中文数据被保存到数据库所使用的字符集
    private static final String STORE_CHARSET  = "GBK";

    // 系统使用的字符集
    private String              systemEncoding = "iso-8859-1";

    /**
     * 从数据库中取数据
     */
    public Object getResult(ResultGetter getter) throws SQLException {
        String value = getter.getString();

        if (value == null) {
            return null;
        } else {
            try {
                return new ChineseString(new String(value.getBytes(systemEncoding), STORE_CHARSET));
            } catch (UnsupportedEncodingException ue) {
                return value;
            }
        }
    }

    /**
     * 往数据库写数据
     */
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter != null) {
            ChineseString value = (ChineseString) parameter;

            if (value.getValue() != null) {
                try {
                    setter.setString(new String(value.getValue().getBytes(STORE_CHARSET), systemEncoding));
                } catch (UnsupportedEncodingException ue) {
                    setter.setString(value.getValue());
                }

                return;
            }
        }

        setter.setNull(Types.VARCHAR);
    }

    public Object valueOf(String s) {
        return s;
    }

}
