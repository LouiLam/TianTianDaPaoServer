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
	public  Map<Object,Object> selectJJCUserByUtoken(Map<? extends Object,? extends Object> params);
	/**
	 * 三日奖励根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectJJC3DayUserByUtoken(Map<? extends Object,? extends Object> params);
	
	/**
	 * 返回时，筛选字段
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectJJCUserByUID(Map<? extends Object,? extends Object> params);
	
	/**
	 * 获取积分刷新时间
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectLastExecutedByResetScore(@Param("paramSQL")String sql);
	
	/**
	 * 返回组合排名数据
	 */
	public  List<Object> selectJJCUserByRank(Map<? extends Object,? extends Object> params);
	/**
	 * 返回世界排名数据
	 */
	public  List<Object> selectJJCUserByWorldRank(Map<? extends Object,? extends Object> params);
	
	/**
	 * 返回世界排名数据
	 */
	public  List<Object> selectJJCUserByWorldRank20140609(Map<? extends Object,? extends Object> params);
	
	
	/**
	 * 返回我的排名数据
	 */
	public  List<Object> selectJJCUserByMyRank(Map<? extends Object,? extends Object> params);
	
	/**
	 * 查询挑战请求是否合法
	 */
	public  Map<Object,Object> selectJJC_PKUser(Map<? extends Object,? extends Object> params);
	
	/**
	 * 发起者挑战胜利
	 * @param params
	 */
	public void updateJJC_RankWin(Map<? extends Object,? extends Object> params);
	
	/**
	 * 发起者挑战失败
	 * @param params
	 */
	public void updateJJC_RankLose(Map<? extends Object,? extends Object> params);
	/**
	 * 发起者挑战平局
	 * @param params
	 */
	public void updateJJC_RankDraw(Map<? extends Object,? extends Object> params);
	
	/**
	 * 更新被挑战者的排名（在挑战者胜利的情况下更新）
	 */
	public void updateJJC_RankBeidong(Map<? extends Object,? extends Object> params);
	
	
	/**
	 * 获取三日奖励
	 * @param params
	 */
	public void updateJJC_Get3DayReward(Map<? extends Object,? extends Object> params);

	
	/**
	 * 更新用户金币
	 * @param params
	 */
	public void updateGoldAndDiamondByUserGame(Map<? extends Object,? extends Object> params);
	/**
	 * 任务执行中更新
	 * @param params
	 */
	public void updateUserTaskRunning(Map<? extends Object,? extends Object> params);
	/**
	 * 任务完成更新
	 * @param params
	 */
	public void updateUserTaskFinish(Map<? extends Object,? extends Object> params);
	/**
	 * 获取记录条数
	 * @return
	 */
	public long selectJJCUserCount();
	
	
	/**
	 * 发起者挑战胜利
	 * @param params
	 */
	public void updateJJC_RankWin20140609(Map<? extends Object,? extends Object> params);
	/**
	 * 发起者挑战平局
	 * @param params
	 */
	public void updateJJC_RankDraw20140609(Map<? extends Object,? extends Object> params);
	/**
	 * 发起者挑战失败
	 * @param params
	 */
	public void updateJJC_RankLose20140609(Map<? extends Object,? extends Object> params);
	
	/**
	 * 返回时，筛选字段
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectJJCUserByUID20140609(Map<? extends Object,? extends Object> params);
	public Map<Object,Object> selectJJCPickUpUserByUID20140609(Map<? extends Object,? extends Object> params);
	
	/**
	 * 根据玩家分数，筛选相应分数段的玩家
	 * @param upuid
	 * @return
	 */
	public  List<Map<Object,Object> > selectJJCUserByMaxScore20140609(Map<? extends Object,? extends Object> params);
	
	public  List<Map<Object,Object> > selectJJCUserByMaxScoreNull20140609(Map<? extends Object,? extends Object> params);
	
	/**
	 * 插入战斗记录
	 * @param params
	 */
	public void insertUserFight20140609(Map<? extends Object,? extends Object> params);
	/**
	 * 更新战斗记录
	 * @param params
	 */
	public void updateUserFight20140609(Map<? extends Object,? extends Object> params);
	
	public long selectLastInsertID20140609();
	
	public void  updateUserGameByCur20140609(Map<? extends Object,? extends Object> params);
	
	/**
	 * 获取体力信息时 更新体力和最近获取体力的时间
	 * @param params
	 */
	public void updateGetTiliUserGameByTiliAndTime(Map<? extends Object,? extends Object> params);
	/**
	 * 开始游戏更新当前参数
	 * @param params
	 */
	public void updateUserGameByCur(Map<? extends Object,? extends Object> params);
}
