package client.use_items;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoUseItemsDao   {
	/**
	 * 使用物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectUseItemsByUtoken(Map params);
	
	/**
	 * 使用物品更新物品数量
	 * @param params
	 */
	public void updateItemByUserProp(@Param("paramSQL")String sql);
	
	/**
	 * 购买金币
	 * @param params
	 */
	public void updateGoldByUserGame(Map params);
	/**
	 * 购买体力
	 * @param params
	 */
	public void updateTiliByUserGame(Map params);
	/**
	 * 扣除金币
	 * @param params
	 */
	public void updateGoldSubByUserGame(Map params);
	/**
	 * 返回各种货币字段
	 * @param params
	 * @return
	 */
	public Map selectBuyItemByMuch(Map params);
}
