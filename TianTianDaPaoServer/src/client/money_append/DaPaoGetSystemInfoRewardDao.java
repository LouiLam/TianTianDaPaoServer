package client.money_append;

import java.util.Map;


public interface DaPaoGetSystemInfoRewardDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectSystemInfoByUtoken(Map params);
	
	/**
	 * 结束操作后返回用户数据
	 * @return
	 */
	public Map selectSystemInfoByUID(Map params);
	
	/**
	 * 更新奖励
	 * @param params
	 */
	public void updateReward(Map params);
	
}
