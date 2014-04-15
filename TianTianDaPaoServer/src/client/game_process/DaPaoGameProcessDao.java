package client.game_process;

import java.util.Map;


public interface DaPaoGameProcessDao   {
	/**
	 * 游戏结束获取体力根据用户token 返回用户是否合法
	 * @return
	 */
	public  Map selectGetTiliByUtoken(Map params);
	
	/**
	 * 游戏结束获取体力根据用户token 返回用户是否合法
	 * @param params
	 * @return
	 */
	public  Map selectGameEndByUtoken(Map params);
	/**
	 * 游戏结束 筛选返回字段
	 * @param upuid
	 * @return
	 */
	public  Map selectGameEndByUID(Map params);
	/**
	 * 游戏开始筛选返回字段
	 * @param upuid
	 * @return
	 */
	public  Map selectGameStartByUID(Map params);
	
	/**
	 * 游戏开始根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map selectGameStartByUtoken(Map params);
	/**
	 * 积分抽奖根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map selectScoreLotteryByUtoken(Map params);
	/**
	 * 更新用户最高成绩
	 * @param params
	 */
	public void updateRecordByUserGame(Map params);
	/**
	 * 更新用户金币
	 * @param params
	 */
	public void updateGoldByUserGame(Map params);
	
	/**
	 * 开始游戏更新体力
	 * @param params
	 */
	public void updateUserGameByTili(Map params);
	/**
	 * 获取体力信息时 更新体力和最近获取体力的时间
	 * @param params
	 */
	public void updateGetTiliUserGameByTiliAndTime(Map params);
	
	/**
	 * 任务完成更新
	 * @param params
	 */
	public void updateUserTaskFinish(Map params);
	
	/**
	 * 任务执行中更新
	 * @param params
	 */
	public void updateUserTaskRunning(Map params);
	
}
