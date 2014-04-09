package client.config_info;

import java.util.Map;

public interface DaPaoConfigInfoDao {
	/**
	 * 登陆后查询用户相关配置信息
	 * @param upuid
	 * @return
	 */
	public  Map selectUserByUToken(Map params);



}
