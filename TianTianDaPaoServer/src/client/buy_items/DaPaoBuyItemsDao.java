package client.buy_items;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoBuyItemsDao   {
	/**
	 * 购买物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectBuyItemsByUtoken(Map params);
	
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
