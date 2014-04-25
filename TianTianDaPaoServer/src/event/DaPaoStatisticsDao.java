package event;

import java.util.Map;

public interface DaPaoStatisticsDao {
	

	/**
	 * 更新系统统计信息
	 * @param params
	 */
	public  void updateSystemStatistics(Map userMap);

	
}
