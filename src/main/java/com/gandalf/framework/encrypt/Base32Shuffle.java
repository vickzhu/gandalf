package com.gandalf.framework.encrypt;

/**
 * 将数字转换为32进制
 * @author gandalf
 *
 */
public class Base32Shuffle {
	
	final static char[] digits = { '0', 'Z','1', 'U','2','E','Y','M', 'W','3','V', '4', 'F',
		'T','5','P', '6','H', 'R','Q', '7', 'X', '8', '9','A',  'C', 'D', 'G',  'I', 'J', 
		'K', 'L', 'N', 'O', 'B', 'S' }; 

	/** 
	 * 把10进制的数字转换成32进制 
	 *  
	 * @param source
	 * @return 
	 */  
	public static String encode(long source) {  
	    char[] buf = new char[32];  
	    int charPos = 32;  
	    int radix = 1 << 5;  
	    long mask = radix - 1;  
	    do {  
	        buf[--charPos] = digits[(int) (source & mask)];  
	        source >>>= 5;  
	    } while (source != 0);  
	    return new String(buf, charPos, (32 - charPos));  
	}  
	
	/** 
	 *	把32进制的字符串转换成10进制 
	 *	@param	source
	 *	@return 
	 */  
	public static long decode(String source) {  
	    long result = 0;  
	    for (int i = source.length() - 1; i >= 0; i--) {  
	        for (int j = 0; j < digits.length; j++) {  
	            if (source.charAt(i) == digits[j]) {  
	                result += ((long) j) << 5 * (source.length() - 1 - i);  
	            }  
	        }  
	    }  
	    return result;  
	}

}
