package client.system_info;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoGetSystemInfoRewardDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object,Object> selectSystemInfoByUtoken(Map<? extends Object,? extends Object> params);
	
	/**
	 * 结束操作后返回用户数据
	 * @return
	 */
	public Map<Object,Object> selectSystemInfoByUID(Map<? extends Object,? extends Object> params);
	
	/**
	 * 更新奖励
	 * @param params
	 */
	public void updateReward(@Param("paramSQL")String sql);
	
}
