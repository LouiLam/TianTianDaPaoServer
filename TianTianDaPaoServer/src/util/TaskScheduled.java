package util;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TaskScheduled {

	private static ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1000);
	
	/**
	 * 以固定速率执行周期任务
	 */
	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable command,long initialDelay,long period,TimeUnit unit){
		return scheduler.scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	/**
	 * 执行启动延时任务
	 */
	public static ScheduledFuture<?> schedule(Runnable command,long delay,TimeUnit unit){
		return scheduler.schedule(command, delay, unit);
	}
	
	/**
	 * 取消定时任务
	 */
	public static boolean cancel(ScheduledFuture<?> scheduled){
		if(scheduled == null) return false;
		return scheduled.cancel(false);
	}
	
	
}
