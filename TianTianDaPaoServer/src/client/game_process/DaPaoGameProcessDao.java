package client.game_process;

import java.util.Map;


public interface DaPaoGameProcessDao   {
	/**
	 * 获取体力根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectGetTiliByUtoken(Map params);
	/**
	 * 游戏结束根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectGameOverByUtoken(Map params);
	
	/**
	 * 游戏开始根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map selectGameStartByUtoken(Map params);
	/**
	 * 更新挑战者成绩
	 * @param params
	 */
	public void updateUserGameByRecord(Map params);
	
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
	
}
