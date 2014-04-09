package client.score_exchange_items;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoScoreExchangeItemsDao   {
	/**
	 * 积分兑换物品根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map selectScoreExchangeItemsByUtoken(Map params);
	
//	update usergame,userjjc set usergame.ugold=usergame.ugold+#{ugold},userjjc.score=userjjc.score-#{consume}
//	where  usergame.uid=#{uid} and userjjc.uid=#{uid}
	/**
	 * 兑换物品更新物品信息和减去积分
	 * @param params
	 */
	public void updateItemByUserJJCAndProp(@Param("paramSQL")String sql);
	
	/**
	 * 返回各种更新字段（金币，道具，角色 飞船 宠物等）
	 * @param params
	 * @return
	 */
	public Map selectScoreLotteryByMuch(Map params);
}
