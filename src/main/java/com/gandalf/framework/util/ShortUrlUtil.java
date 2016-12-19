package com.gandalf.framework.util;

import com.gandalf.framework.encrypt.MD5Util;

/**
 * 将长域名转换为短域名
 * @author gandalf
 *
 */
public class ShortUrlUtil {
	
	private static final String key = "gandalf" ;  
	
	private static final String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,  
	  "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,  
	  "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,  
	  "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,  
	  "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,  
	  "U" , "V" , "W" , "X" , "Y" , "Z"
	};  
	       
	public static String[] shortUrl(String url) {
	   // 对传入网址进行 MD5 加密  
       String sMD5EncryptResult =MD5Util.md5Hex(key + url);
   
       String[] resUrl = new String[4];
       
       for ( int i = 0; i < 4; i++) {
           // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算  
           String sTempSubString = sMD5EncryptResult.substring(i * 8, i * 8 + 8);
   
           // 这里需要使用 long 型来转换，因为 Inteper.parseInt() 只能处理 31位 , 首位为符号位 , 如果不用 long ，则会越界  
           long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
           StringBuilder sb = new StringBuilder();
           for ( int j = 0; j < 6; j++) {
        	   // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引  
        	   long index = 0x0000003D & lHexLong;
        	   // 把取得的字符相加 
        	   sb.append(chars[( int ) index]);
        	   // 每次循环按位右移 5 位
        	   lHexLong = lHexLong >> 5;  
           }
           // 把字符串存入对应索引的输出数组  
	       resUrl[i] = sb.toString();  
	   }
	   return resUrl;  
    }
	
}
