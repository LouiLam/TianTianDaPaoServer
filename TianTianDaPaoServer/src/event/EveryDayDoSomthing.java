package event;

import java.util.concurrent.TimeUnit;

import util.TaskScheduled;

public class EveryDayDoSomthing {
//	5000话费点 每天分配
//
//	竞技场 写死 每天为750 525 225 写到数据库中
//	BOSS 2000
//	抽奖 1500
	public static int BOSSChargeRemain=2000;
	public static int LotteryChargeRemain=1500;
	public EveryDayDoSomthing(){
		
	}
	public static void configure()
	{
		TaskScheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				BOSSChargeRemain=2000;
				LotteryChargeRemain=1500;
				
			}
		}, 0, 1, TimeUnit.DAYS);
	}

}
