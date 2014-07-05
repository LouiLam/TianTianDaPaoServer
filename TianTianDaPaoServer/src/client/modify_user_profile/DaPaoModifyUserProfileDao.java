package client.modify_user_profile;

import java.util.Map;


public interface DaPaoModifyUserProfileDao   {
	/**
	 * 购买物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object,Object> selectModifyByUtoken(Map<? extends Object,? extends Object> params);
	
	
	/**
	 * 修改昵称
	 * @param params
	 */
	public void updateIDByUserInfo(Map<? extends Object,? extends Object> params);
	
	/**
	 * 查昵称是否重复
	 * @param params
	 * @return 
	 */
	public Map<Object,Object> selectIDByUserInfo(Map<? extends Object,? extends Object> params);
	
	public Map<Object,Object> updateByUserGame(Map<? extends Object,? extends Object> params);
	
}
