package client.modify_user_profile;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoModifyUserProfileDao   {
	/**
	 * 购买物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectModifyByUtoken(Map params);
	
	
	/**
	 * 修改昵称
	 * @param params
	 */
	public void updateIDByUserInfo(Map params);
}
