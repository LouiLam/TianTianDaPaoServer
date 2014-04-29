package client.role_upgrade;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface DaPaoRoleUpgradeDao   {
	/**
	 * 角色升级根据用户token 返回用户是否合法
	 * @param upuid
	 * @return
	 */
	public Map<Object,Object> selectRoleUpgradeByUtoken(Map<? extends Object,? extends Object> params);
	/**
	 * 查询角色等级
	 * @param params
	 */
	public int selectRoleLevelByGameProp(@Param("paramSQL")String sql);
	
	/**
	 * 更新角色等级和用户货币
	 * @param params
	 */
	public void updateRoleUpgradeByMuch(@Param("paramSQL")String sql);
	
	/**
	 * 角色升级完成后，查询需要的返回字段
	 * @param params
	 * @return
	 */
	public Map<Object,Object> selectRoleUpgradeByUID(Map<? extends Object,? extends Object> params);
}
