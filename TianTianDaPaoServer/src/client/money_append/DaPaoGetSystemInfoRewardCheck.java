package client.money_append;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import config.GetSystemInfo;
import config.GetSystemInfoConfigMgr;
import database.DatabaseConnector;

public class DaPaoGetSystemInfoRewardCheck extends Check {

	public DaPaoGetSystemInfoRewardCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		try {
			DaPaoGetSystemInfoRewardDao loginDao = (DaPaoGetSystemInfoRewardDao) sqlSession
					.getMapper(ConfigFactory.getClazz("18"));
			Map<Object,Object> selectMap = loginDao.selectSystemInfoByUtoken(params);
			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED));
				U.infoQueue("获取系统信息奖励请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			long uid = (long) selectMap.get("uid");
			int item_id=0;
			try {
				 item_id = Integer.parseInt(params.get("item_id"));
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR));
				U.infoQueue("获取系统信息奖励请求失败：缺少item_id或者item_id格式非法 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			GetSystemInfo obj=GetSystemInfoConfigMgr.getInstance().getSystemInfoMap.get(item_id);
			if(obj==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR));
				U.infoQueue("获取系统信息奖励请求失败：item_id值范围非法 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			selectMap.put("diamond", obj.diamond);
			selectMap.put("ucharge", obj.ucharge);
			selectMap.put("score", obj.score);
			selectMap.put("ugold", obj.ugold);
			if(selectMap.get("reward"+item_id)==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_ARG_ERROR));
				U.infoQueue("获取系统信息奖励请求失败：item_id值范围非法---策划配置文档与数据库字段配置不匹配 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			boolean isReward=(boolean) selectMap.get("reward"+item_id);
			
			if (!isReward) // 没有领取一次
			{
				StringBuffer sql=new StringBuffer("update usergame,userjjc,user_system_reward set ");
				sql.append("usergame.ucharge=usergame.ucharge+"+obj.ucharge);
				sql.append(",usergame.diamond=usergame.diamond+"+obj.diamond);
				sql.append(",usergame.ugold=usergame.ugold+"+obj.ugold);
				sql.append(",userjjc.score=userjjc.score+"+obj.score);
				sql.append(",user_system_reward.reward"+item_id+"=1");
				sql.append(" where  usergame.uid="+selectMap.get("uid")+" and userjjc.uid="+selectMap.get("uid")+" and user_system_reward.uid="+selectMap.get("uid"));
				
						 
				loginDao.updateReward(sql.toString());
				selectMap=loginDao.selectSystemInfoByUID(selectMap);
				U.infoQueue("id:" + id + "获取系统信息奖励请求成功"
						+ channel.getRemoteAddress().toString());
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET,
						Constant.RET_GET_SYSTEM_INFO_REWARD_SUCCESS);
				jsonObject
						.put(Constant.MSG,
								ConfigFactory
										.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_SUCCESS));
			} 
			else // 领取过了系统奖励了，奖励时间段内 不能领取了
			{
				U.infoQueue("id:" + id + "获取系统信息奖励请求失败： 该用户领取过了此系统奖励了"
						+ channel.getRemoteAddress().toString());
				jsonObject.put(Constant.RET,
						Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_REPEAT_GET);
				jsonObject
						.put(Constant.MSG,
								ConfigFactory
										.getRetMsg(Constant.RET_GET_SYSTEM_INFO_REWARD_FAILED_REPEAT_GET));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
