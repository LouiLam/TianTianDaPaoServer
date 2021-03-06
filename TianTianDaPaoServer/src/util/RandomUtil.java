package util;

import java.util.Random;

public class RandomUtil {
	static Random r = new Random();
	
	public static int getRan(int num) {
		return Math.abs(r.nextInt() % num);
	}

	/**
	 * 
	 * @param n  包含n
	 * @param m  不包含m
	 * @return
	 */
	public static int getRan(int n, int m) {
		return getRan(m - n) + n;
	}
	
	public static long getRan(long num) {
		return Math.abs(r.nextLong() % num);
	}

	/**
	 * 
	 * @param n  包含n
	 * @param m  不包含m
	 * @return
	 */
	public static long getRan(long n, long m) {
		return getRan(m - n) + n;
	}
}
