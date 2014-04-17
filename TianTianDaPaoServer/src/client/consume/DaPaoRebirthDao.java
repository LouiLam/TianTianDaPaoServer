package client.consume;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoRebirthDao   {
	/**
	 * 使用物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectUseItemsByUtoken(Map params);
	
	/**
	 * 使用物品更新物品数量
	 * @param params
	 */
	public void updateDiamondByUserGame(Map params);
	
}
