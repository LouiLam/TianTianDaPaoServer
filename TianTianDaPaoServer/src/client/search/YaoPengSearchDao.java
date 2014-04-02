package client.search;

import java.util.Map;

public interface YaoPengSearchDao {
	/**
	 * 登陆时查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map selectUserByUid(Map params);


	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserInfo(Map params);
	/**
	 * 第一次游客登录插入一条纪录到UserGame表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoGameTable(Map params);


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

}
