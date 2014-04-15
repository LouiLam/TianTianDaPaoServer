package client.task;

import java.util.Map;


public interface DaPaoTaskRunningDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectTaskUserByUtoken(Map params);
	
}
