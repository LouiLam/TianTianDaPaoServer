package event;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;

import util.DateUtil;
import util.Statistics;
import util.TaskScheduled;
import config.ConfigFactory;
import database.DatabaseConnector;

public class EveryDayDoSomthing {
//	5000话费点 每天分配
//
//	竞技场 写死 每天为750 525 225 写到数据库中
//	BOSS 2000
//	抽奖 1500
//	public static volatile int LotteryChargeRemain=1500;
	public EveryDayDoSomthing(){
		
	}
	public static void configure()
	{
		TaskScheduled.clear();
		TaskScheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
				try {
					DaPaoStatisticsDao loginDao = (DaPaoStatisticsDao) sqlSession.getMapper(
							ConfigFactory.getClazz("19"));
					HashMap<Object, Object>map =new HashMap<Object, Object>();
					map.put("independent_ip_by_day", Statistics.INDEPENDENT_IP_BY_DAY+"");
					map.put("time", DateUtil.getCurDate());
					loginDao.updateSystemStatistics(map);
					sqlSession.commit();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} finally {
					sqlSession.close();
				}
				System.out.println("刷新EveryDayDoSomthing的时间点为："+DateUtil.getCurDate());
				Statistics.INDEPENDENT_IP_BY_DAY=0;
				Statistics.INDEPENDENT_IP_SET.clear();
			}
		}, 0, 1, TimeUnit.DAYS);
		TaskScheduled.toCount();
	}

}
