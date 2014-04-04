package client.login;

import java.util.Map;

public interface DaPaoLoginDao {
	/**
	 * 登陆时查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map selectUserByLogin(Map params);


	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserInfo(Map params);



	/**
	 * 登陆后更新token和Ultime
	 * @param params
	 */
	public  void updateUserInfoUltime(Map<String, String> params);

	/**
	 * 平台登陆后更新uname
	 * @param params
	 */
	public  void updateUserinfoSetUname(Map<String, String> userMap);

	/**
	 * 登录奖励
	 * @param params
	 */
	public void login_reward(Map params);
	
	/**
	 * 根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectJJCUserByUtoken(Map params);
	
	/**
	 * 登录奖励  根据用户token 返回用户是否合法
	 * @param params
	 * @return
	 */
	public  Map selectLoginRewardByUtoken(Map params);
	
}
