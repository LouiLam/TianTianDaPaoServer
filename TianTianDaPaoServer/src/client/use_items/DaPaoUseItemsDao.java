package client.use_items;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoUseItemsDao   {
	/**
	 * 使用物品根据用户token 返回用户是否合法
	 * @return
	 */
	public Map<Object,Object> selectUseItemsByUtoken(Map<? extends Object,? extends Object> params);
	
	/**
	 * 使用物品更新物品数量
	 * @param params
	 */
	public void updateItemByUserProp(@Param("paramSQL")String sql);
	
}
