package client.game_process;

import java.util.Map;


public interface DaPaoGameProcessDao   {
	/**
	 * 游戏结束获取体力根据用户token 返回用户是否合法
	 * @return
	 */
	public  Map<Object,Object> selectGetTiliByUtoken(Map<? extends Object,? extends Object> params);
	
	/**
	 * 游戏结束获取体力根据用户token 返回用户是否合法
	 * @param params
	 * @return
	 */
	public  Map<Object,Object> selectGameEndByUtoken(Map<? extends Object,? extends Object> params);
	/**
	 * 游戏结束 筛选返回字段
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectGameEndByUID(Map<? extends Object,? extends Object> params);
	/**
	 * 游戏开始筛选返回字段
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectGameStartByUID(Map<? extends Object,? extends Object> params);
	
	/**
	 * 游戏开始根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map<Object,Object> selectGameStartByUtoken(Map<? extends Object,? extends Object> params);
	/**
	 * 积分抽奖根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map<Object,Object> selectScoreLotteryByUtoken(Map<? extends Object,? extends Object> params);
	/**
	 * 更新用户最高成绩
	 * @param params
	 */
	public void updateRecordByUserGame(Map<? extends Object,? extends Object> params);
	/**
	 * 更新用户金币
	 * @param params
	 */
	public void updateGoldByUserGameAndDiamond(Map<? extends Object,? extends Object> params);
	
	/**
	 * 开始游戏更新当前参数
	 * @param params
	 */
	public void updateUserGameByCur(Map<? extends Object,? extends Object> params);
	/**
	 * 获取体力信息时 更新体力和最近获取体力的时间
	 * @param params
	 */
	public void updateGetTiliUserGameByTiliAndTime(Map<? extends Object,? extends Object> params);
	
	/**
	 * 任务完成更新
	 * @param params
	 */
	public void updateUserTaskFinish(Map<? extends Object,? extends Object> params);
	
	/**
	 * 任务执行中更新
	 * @param params
	 */
	public void updateUserTaskRunning(Map<? extends Object,? extends Object> params);
	
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
}
