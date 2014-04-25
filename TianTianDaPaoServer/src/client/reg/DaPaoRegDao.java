package client.reg;

import java.util.Map;

public interface DaPaoRegDao {
	
	/**
	 * 注册查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map selectUserByUid(Map params);

	/**
	 * 更新一条记录，并使某个字段的值为自动递增值
	 * @param params
	 */
	public  void updateIDIntoUserInfo(Map params);
	/**
	 * 注册查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map  selectUserByMac(Map params);
	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserInfo(Map params);
	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中 MAC地址
	 * @param udevice
	 * @return
	 */
	public  void insertUserInfoByMac(Map params);
	/**
	 * 注册插入一条纪录到UserJJC表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserJJC(Map params);
	/**
	 * 注册插入一条纪录到UserProp表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserProp(Map params);

	/**
	 * 更新一条记录，并使某个字段的值为自动递增值
	 * @param params
	 */
	public  void updateRankIntoUserJJC(Map params);
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
	 * 插入任务记录，并随机任务ID
	 * @param params
	 */
	public void insertUserIntoTaskUser(Map<String, String> params);
	
	/**
	 * 插入游戏表
	 * @param params
	 */
	public void insertUserIntoUserGame(Map<String, String> params);
	/**
	 * 注册设置3Day score 调用存储过程
	 * @param params
	 */
	public void insert_score_3day(Map params);

	/**
	 * 注册插入系统奖励表
	 * @param params
	 */
	public void insertUserIntoUserSystemReward(Map<String, String> params);
}
