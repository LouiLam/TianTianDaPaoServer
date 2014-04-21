package client.recharge_query;

import java.util.Map;


public interface DaPaoRechargeQueryDao   {
	/**
	 * 使用物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectDiamondByUtoken(Map params);
	
	
}
