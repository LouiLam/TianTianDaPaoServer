package client.jjc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoJJCDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectJJCUserByUtoken(Map params);
	/**
	 * 返回时，筛选字段
	 * @param upuid
	 * @return
	 */
	public  Map selectJJCUserByUID(Map params);
	
	/**
	 * 获取积分刷新时间
	 * @param upuid
	 * @return
	 */
	public  Map selectLastExecutedByResetScore(@Param("paramSQL")String sql);
	
	/**
	 * 返回组合排名数据
	 */
	public  List selectJJCUserByRank(Map params);
	/**
	 * 返回世界排名数据
	 */
	public  List selectJJCUserByWorldRank(Map params);
	
	/**
	 * 返回我的排名数据
	 */
	public  List selectJJCUserByMyRank(Map params);
	
	/**
	 * 查询挑战请求是否合法
	 */
	public  Map selectJJC_PKUser(Map params);
	
	/**
	 * 发起者挑战胜利
	 * @param params
	 */
	public void updateJJC_RankWin(Map params);
	
	/**
	 * 发起者挑战失败
	 * @param params
	 */
	public void updateJJC_RankLose(Map params);
	/**
	 * 发起者挑战平局
	 * @param params
	 */
	public void updateJJC_RankDraw(Map params);
	
	/**
	 * 更新被挑战者的排名（在挑战者胜利的情况下更新）
	 */
	public void updateJJC_RankBeidong(Map params);
	
	
	/**
	 * 查询3day积分  看用户有没有领过积分
	 * @param params
	 * @return
	 */
	public Map selectJJC_GetScore(Map params);
	/**
	 * 获取积分
	 * @param params
	 */
	public void updateJJC_GetScore(Map params);

	
	/**
	 * 更新用户金币
	 * @param params
	 */
	public void updateGoldByUserGame(Map params);
	/**
	 * 任务执行中更新
	 * @param params
	 */
	public void updateUserTaskRunning(Map params);
	/**
	 * 任务完成更新
	 * @param params
	 */
	public void updateUserTaskFinish(Map params);
}
