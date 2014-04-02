package util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class DateUtil {
	public static String getDateByTimestamp(int myqslTimestamp) {
		Timestamp ts=new Timestamp(myqslTimestamp*1000l);
		return ts.toString();
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		int month = Calendar.getInstance().get(Calendar.MONTH);
//		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//		if(month+1>9)
//		{return ""+year + (month+1) + day ;}
//		else
//		{
//			return ""+year + "0"+(month+1) + day ;
//		}
	}
	public static String getCurDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts=new Timestamp(System.currentTimeMillis());
		return df.format(ts);
	}
}
