package client.jjc;

import java.util.List;
import java.util.Map;


public interface DaPaoJJCDao   {
	/**
	 * 根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public  Map selectJJCUserByUtoken(Map params);
	
	/**
	 * 返回排名数据
	 */
	public  List selectJJCUserByNum(Map params);
	
	
	/**
	 * 查询挑战请求是否合法
	 */
	public  Map selectJJC_PKUser(Map params);
	
	/**
	 * 发起者挑战胜利
	 * @param params
	 */
	public void updateJJC_RankWin(Map params);
	
	/**
	 * 发起者挑战失败
	 * @param params
	 */
	public void updateJJC_RankLose(Map params);
	/**
	 * 发起者挑战平局
	 * @param params
	 */
	public void updateJJC_RankDraw(Map params);
	
	/**
	 * 更新被挑战者的排名（在挑战者胜利的情况下更新）
	 */
	public void updateJJC_RankBeidong(Map params);
	
	
	/**
	 * 查询3day积分  看用户有没有领过积分
	 * @param params
	 * @return
	 */
	public Map selectJJC_GetScore(Map params);
	/**
	 * 获取积分
	 * @param params
	 */
	public void updateJJC_GetScore(Map params);
}
