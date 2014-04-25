package util;

import java.util.HashSet;

/**
 * 服务端数据统计工具
 * @author Administrator
 *
 */
public class Statistics {
	/**
	 * 独立IP set
	 */
	public static volatile HashSet<String> INDEPENDENT_IP_SET=new HashSet<String>();
	/**
	 * 当天独立ip访问人数
	 */
	public static volatile long INDEPENDENT_IP_BY_DAY;
	/**
	 * 累计独立ip访问人数
	 */
	public static volatile long INDEPENDENT_IP_TOTAL;
	
}
