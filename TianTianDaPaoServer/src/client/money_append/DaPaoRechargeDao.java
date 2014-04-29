package client.money_append;

import java.util.Map;


public interface DaPaoRechargeDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object, Object> selectRechargeByUID(Map<? extends Object, ? extends Object> params);
	
	/**
	 * 更新钻石
	 * @param params
	 */
	public void updateDiamondByUserGame(Map<? extends Object, ? extends Object> params);
	
	
	/**
	 * 插入RMB充值记录
	 * @return
	 */
	public void insertRMBrecord(Map<? extends Object, ? extends Object> params);
	
}
