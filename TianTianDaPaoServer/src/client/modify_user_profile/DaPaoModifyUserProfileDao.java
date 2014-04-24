package client.modify_user_profile;

import java.util.Map;


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
	
	/**
	 * 查昵称是否重复
	 * @param params
	 * @return 
	 */
	public Map selectIDByUserInfo(Map params);
}
