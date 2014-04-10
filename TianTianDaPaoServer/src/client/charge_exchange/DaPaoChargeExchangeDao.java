package client.charge_exchange;

import java.util.Map;

public interface DaPaoChargeExchangeDao {
	/**
	 * 查询用户相关信息
	 * @return
	 */
	public  Map selectUserByUToken(Map params);
	/**
	 * d请求成功后返回相关信息
	 * @return
	 */
	public  Map selectUserByUID(Map params);
	/**
	 * 查询当前记录条数
	 * @return
	 */
	public int selectCountByChargerecord();
	/**
	 * 更新话费点字段
	 * @return
	 */
	public void updateChargeByUsergame(Map params);
	/**
	 * 插入话费点字段
	 * @return
	 */
	public void insertChargerecordByUsergame(Map params);
}
