package client.login;

import java.util.Map;

public interface DaPaoLoginDao {
	
	/**
	 * 登陆时查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectUserByLogin(Map<? extends Object,? extends Object> params);

	public  Map<Object,Object> selectUserByLoginMac(Map<? extends Object,? extends Object> params);

	
	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserInfo(Map<? extends Object,? extends Object> params);



	/**
	 * 登陆后更新token和Ultime
	 * @param params
	 */
	public  void updateUserInfoUltime(Map<Object,Object> params);

	/**
	 * 平台登陆后更新uname
	 * @param params
	 */
	public  void updateUserinfoSetUname(Map<String, String> userMap);

	/**
	 * 登录奖励
	 * @param params
	 */
	public void login_reward(Map<? extends Object,? extends Object> params);
	
	
	/**
	 * 登录奖励  根据用户token 返回用户是否合法
	 * @param params
	 * @return
	 */
	public  Map<Object,Object> selectLoginRewardByUtoken(Map<? extends Object,? extends Object> params);
	
}
