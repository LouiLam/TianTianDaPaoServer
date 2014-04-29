package client.charge_exchange;

import java.util.Map;

public interface DaPaoChargeExchangeDao {
	/**
	 * 查询用户相关信息
	 * @return
	 */
	public  Map<Object,Object> selectUserByUToken(Map<? extends Object,? extends Object> params);
	/**
	 * d请求成功后返回相关信息
	 * @return
	 */
	public  Map<Object,Object> selectUserByUID(Map<? extends Object,? extends Object> params);
	/**
	 * 查询当前记录条数
	 * @return
	 */
	public int selectCountByChargerecord();
	/**
	 * 更新话费点字段
	 * @return
	 */
	public void updateChargeByUsergame(Map<? extends Object,? extends Object> params);
	/**
	 * 插入话费点字段
	 * @return
	 */
	public void insertChargerecordByUsergame(Map<? extends Object,? extends Object> params);
}
