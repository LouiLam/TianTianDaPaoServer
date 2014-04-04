package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	public static String getDateByTimestamp(int myqslTimestamp) {
		Timestamp ts = new Timestamp(myqslTimestamp * 1000l);
		return ts.toString();
	}
	public static String getDateByTimestamp(long Timestamp) {
		Timestamp ts = new Timestamp(Timestamp);
		return ts.toString();
	}
	public static String getCurDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return df.format(ts);
	}

	public static void main(String[] args) {
		System.out.println(getDateByTimestamp(1396575405));

	}

	/**
	 * 天数差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDaysBetween(long startDate, long endDate) {
	
			Calendar fromCalendar = Calendar.getInstance();
			fromCalendar.setTimeInMillis(startDate);
			// fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
			// fromCalendar.set(Calendar.MINUTE, 0);
			// fromCalendar.set(Calendar.SECOND, 0);
			// fromCalendar.set(Calendar.MILLISECOND, 0);

			Calendar toCalendar = Calendar.getInstance();

			
			return (toCalendar.getTimeInMillis() - fromCalendar
					.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		

	}

	/**
	 * 分钟差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getMinutesBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 * 60 );
	
	}
	/**
	 * 秒钟差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getSecondsBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 );
	
	}
	/**
	 * 小时差值
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getHoursBetween(long startDate, long endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTimeInMillis(startDate);
		Calendar toCalendar = Calendar.getInstance();
		return (toCalendar.getTimeInMillis() - fromCalendar
				.getTimeInMillis()) / (1000 * 60*60 );
	}
}
