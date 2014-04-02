package util;


import java.security.NoSuchAlgorithmException;
 
import javax.crypto.Cipher;  

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;  

  
public class AES {  
	private static SecretKey key;
	private static String keyStr;
	static {
		
	}
	public static String  generateSessionKey()
	{
		try {
			key=KeyGenerator.getInstance("AES").generateKey();
			keyStr=IntegerToXXX.parseByte2HexStr(key.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return keyStr;
	}
    /** 
     * 加密
     */  
    public static String encryption(String str){  
        byte [] output=null;
        try {  
            Cipher cipher=Cipher.getInstance("AES"); 
            cipher.init(Cipher.ENCRYPT_MODE, key); 

           
            output= cipher.doFinal(str.getBytes());  

        } catch (Exception e) {  
            e.printStackTrace();  
        }
        return  IntegerToXXX.parseByte2HexStr(output);
    }  
    /** 
     * 解密
     */  
    public static String  decryption(String str){   
        byte [] output=null;
           
            try {
            	  Cipher cipher = Cipher.getInstance("AES");  
            	  cipher.init(Cipher.DECRYPT_MODE,key);  
            	  output= cipher.doFinal(IntegerToXXX.parseHexStr2Byte(str));  
            	
			} catch (Exception e) {
				e.printStackTrace();
			}  
            return  new String(output);
    }  
  


}  
