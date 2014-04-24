package client.role_upgrade;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoRoleUpgradeCheck extends Check {
	public DaPaoRoleUpgradeCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRoleUpgradeDao loginDao = (DaPaoRoleUpgradeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("9"));
			
			Map selectMap = loginDao.selectRoleUpgradeByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED));
				U.infoQueue("角色升级请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id =(String) selectMap.get("id");
			int item_id;
			if(params.get("item_id")==null) //丢失参数
			{
			
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_MISS_ARG));
				U.infoQueue(id+"角色升级请求失败,缺少参数item_id"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			try {
				 item_id=Integer.parseInt(params.get("item_id"));
				
			} catch (Exception e) {
				//参数值非法
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID));
				U.infoQueue(id+"角色升级请求失败,item_id参数值非法---非整形"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(item_id<1||item_id>4)
			{
				//参数值非法
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID));
				U.infoQueue(id+"角色升级请求失败,item_id参数值非法---1~4之外的范围"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String temp="select prop"+(item_id-1)+" from userprop where  userprop.uid="+selectMap.get("uid");
			int level=loginDao.selectRoleLevelByGameProp(temp);
			
			if(level==0)//没有拥有此角色 不能升级
			{
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_ARG_INVALID));
				U.infoQueue(id+"角色升级请求失败,item_id参数值非法---没有拥有此角色"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			RoleUpgrade obj=RoleUpgradeConfigMgr.getInstance().taskObjMap.get(item_id);
			if(level>=obj.max_level)
			{
				//最高级不能在升了
				jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_MAX_LEVEL);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_MAX_LEVEL));
				U.infoQueue(id+"角色升级请求失败,已经是最高等级"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			//当前等级对应消耗
			int consume=obj.map.get(level);
			if(level<31)//消耗金钱
			{
				if((long)selectMap.get("ugold")<consume)//金币不够升级
				{
					jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_MONEY_NOT_ENOUGH);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_MONEY_NOT_ENOUGH));
					U.infoQueue(id+"角色升级请求失败,货币不够升级--金币"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
//				prop"+(item_id-1)
				StringBuffer sql=new StringBuffer("update usergame,userprop set ");
				sql.append("usergame.ugold=usergame.ugold-"+consume);
				sql.append(",userprop.prop"+(item_id-1)+"=userprop.prop"+(item_id-1)+"+1");
				sql.append(" where  usergame.uid="+selectMap.get("uid")+" and userprop.uid="+selectMap.get("uid"));
				loginDao.updateRoleUpgradeByMuch(sql.toString());
				sqlSession.commit();
			
			}
			else//消耗钻石
			{
				if((long)selectMap.get("diamond")<consume)//钻石不够升级
				{
					jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_FAILED_MONEY_NOT_ENOUGH);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_ROLE_UPGRADE_FAILED_MONEY_NOT_ENOUGH));
					U.infoQueue(id+"角色升级请求失败,货币不够升级--钻石"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
				StringBuffer sql=new StringBuffer("update usergame,userprop set ");
				sql.append("usergame.diamond=usergame.diamond-"+consume);
				sql.append(",userprop.prop"+(item_id-1)+"=userprop.prop"+(item_id-1)+"+1");
				sql.append(" where  usergame.uid="+selectMap.get("uid")+" and userprop.uid="+selectMap.get("uid"));
				loginDao.updateRoleUpgradeByMuch(sql.toString());
				sqlSession.commit();
			}
			
			U.infoQueue(id + "角色升级请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			selectMap=loginDao.selectRoleUpgradeByUID(selectMap);
			jsonObject.put("userInfo", selectMap);
			jsonObject.put(Constant.RET, Constant.RET_ROLE_UPGRADE_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_ROLE_UPGRADE_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}

}
