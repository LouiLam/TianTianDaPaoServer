package client.money_consume;

import java.util.Map;


public interface DaPaoRebirthDao   {
	/**
	 * 使用物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object,Object> selectUseItemsByUtoken(Map<? extends Object,? extends Object> params);
	
	/**
	 * 使用物品更新物品数量
	 * @param params
	 */
	public void updateDiamondByUserGame(Map<? extends Object,? extends Object> params);
	
}
