package client.money_append;

import java.util.Map;


public interface DaPaoBossOverDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @return
	 */
	public Map selectBossOverByUtoken(Map params);
	
	/**
	 * 更新话费点
	 * @param params
	 */
	public void updateChargeByUserGame(Map params);
	
}
