package util;

/**
 * ����ת��
 * @author Mercury
 *
 */
public class IntegerToXXX {
	
	  /**二进制转十六进制
     * @param buf  
     * @return  
     */  
    public static String parseByte2HexStr(byte buf[]) {  
            StringBuffer sb = new StringBuffer();  
            for (int i = 0; i < buf.length; i++) {  
                    String hex = Integer.toHexString(buf[i] & 0xFF);  
                    if (hex.length() == 1) {  
                            hex = '0' + hex;  
                    }  
                    sb.append(hex.toUpperCase());  
            }  
            return sb.toString();  
    }  

    /**十六进制转二进制
     * @param hexStr  
     * @return  
     */  
    public static byte[] parseHexStr2Byte(String hexStr) {  
            if (hexStr.length() < 1)  
                    return null;  
            byte[] result = new byte[hexStr.length()/2];  
            for (int i = 0;i< hexStr.length()/2; i++) {  
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                    result[i] = (byte) (high * 16 + low);  
            }  
            return result;  
    }  
//    /**
//     * 备用 （其他互换）
//     */
//	public void IntegerToXXX()
//	   {int n1 = 14;
//	    //10换16
//	    Integer.toHexString(n1);
//	    //10 转8
//	    Integer.toOctalString(n1);
//	    //10 转2
//	    Integer.toBinaryString(12);
//	    
//	    //16转10
//	    Integer.valueOf("FFFF",16).toString();
//	    //16转2
//	    Integer.toBinaryString(Integer.valueOf("FFFF",16));
//	    //16转8
//	    Integer.toOctalString(Integer.valueOf("FFFF",16));
//	    
//	    //8转10
//	    Integer.valueOf("576",8).toString();
//	    //8转2
//	    Integer.toBinaryString(Integer.valueOf("23",8));
//	    //8转16
//	    Integer.toHexString(Integer.valueOf("23",8));
//
//
//	    //2转10
//	    Integer.valueOf("0101",2).toString();
//	    //2转8
//	    Integer.toOctalString(Integer.parseInt("0101", 2));
//	    //2转16
//	    Integer.toHexString(Integer.parseInt("0101", 2));}
}
