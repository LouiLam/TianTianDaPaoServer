package client.reg;

import java.util.Map;

public interface DaPaoRegDao {
	
	/**
	 * 注册查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object> selectUserByUid(Map<? extends Object,? extends Object> params);

	/**
	 * 更新一条记录，并使某个字段的值为自动递增值
	 * @param params
	 */
	public  void updateIDIntoUserInfo(Map<? extends Object,? extends Object> params);
	/**
	 * 注册查询用户是否存在
	 * @param upuid
	 * @return
	 */
	public  Map<Object,Object>  selectUserByMac(Map<? extends Object,? extends Object> params);
	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserInfo(Map<? extends Object,? extends Object> params);
	/**
	 * 第一次游客登录插入一条纪录到UserInfo表中 MAC地址
	 * @param udevice
	 * @return
	 */
	public  void insertUserInfoByMac(Map<? extends Object,? extends Object> params);
	/**
	 * 注册插入一条纪录到UserJJC表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserJJC(Map<? extends Object,? extends Object> params);
	/**
	 * 注册插入一条纪录到UserProp表中
	 * @param udevice
	 * @return
	 */
	public  void insertUserIntoUserProp(Map<? extends Object,? extends Object> params);

	/**
	 * 更新一条记录，并使某个字段的值为自动递增值
	 * @param params
	 */
	public  void updateRankIntoUserJJC(Map<? extends Object,? extends Object> params);
	/**
	 * 登陆后更新token和Ultime
	 * @param params
	 */
	public  void updateUserInfoUltime(Map<? extends Object,? extends Object> params);

	/**
	 * 平台登陆后更新uname
	 * @param params
	 */
	public  void updateUserinfoSetUname(Map<? extends Object,? extends Object> userMap);

	/**
	 * 插入任务记录，并随机任务ID
	 * @param params
	 */
	public void insertUserIntoTaskUser(Map<? extends Object,? extends Object> params);
	
	/**
	 * 插入游戏表
	 * @param params
	 */
	public void insertUserIntoUserGame(Map<? extends Object,? extends Object> params);
	/**
	 * 注册设置3Day score 调用存储过程
	 * @param params
	 */
	public void insert_score_3day(Map<? extends Object,? extends Object> params);

	/**
	 * 注册插入系统奖励表
	 * @param params
	 */
	public void insertUserIntoUserSystemReward(Map<String, String> params);
}
