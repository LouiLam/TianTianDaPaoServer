package client.recharge;

import java.util.Map;


public interface DaPaoRechargeDao   {
	/**
	 * 根据用户uid 返回用户是否合法
	 * @return
	 */
	public Map<Object, Object> selectRechargeByUID(Map<? extends Object, ? extends Object> params);
	
	
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object, Object> selectRechargeByToken(Map<? extends Object, ? extends Object> params);
	
	
	/**
	 * 更新钻石
	 * @param params
	 */
	public void updateDiamondByUserGame(Map<? extends Object, ? extends Object> params);
	
	
	/**
	 * 生成订单号
	 * @return
	 */
	public void insertRMBrecord(Map<? extends Object, ? extends Object> params);
	
	/**
	 * 更新订单状态
	 * @param params
	 */
	public void updateRMBrecord(Map<? extends Object, ? extends Object> params);
	
	/**
	 * 查询订单号和生成时间
	 * @param params
	 * @return
	 */
	public Map<Object, Object> selectOrder(Map<? extends Object, ? extends Object> params);
	
	/**
	 * 查询订单状态
	 */
	public Map<Object, Object> selectOrderState(Map<? extends Object, ? extends Object> params);
	
	public Map<Object, Object> selectUIDByOrder(Map<? extends Object, ? extends Object> params);
	
}
