package client.score_lottery;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoScoreLotteryDao   {
	/**
	 * 积分抽奖根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map selectScoreLotteryByUtoken(Map params);
	/**
	 * 抽奖到物品
	 * @param params
	 */
	public void updateItemByUserProp(@Param("paramSQL")String sql);
	
	/**
	 * 抽奖到金币
	 * @param params
	 */
	public void updateUGoldByUserGame(Map params);
	
	/**
	 * 抽奖到话费点
	 * @param params
	 */
	public void updateUChargeByUserGame(Map params);
	
	/**
	 * 返回各种更新字段（金币，道具，角色 飞船 宠物等）
	 * @param params
	 * @return
	 */
	public Map selectScoreLotteryByMuch(Map params);
}
