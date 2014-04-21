package client.money_append;

import java.util.Map;


public interface DaPaoRechargeDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectRechargeByUID(Map params);
	
	/**
	 * 更新钻石
	 * @param params
	 */
	public void updateDiamondByUserGame(Map params);
	
}
