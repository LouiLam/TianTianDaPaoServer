package client.game_process;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.DateUtil;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoGameStartCheck extends Check {
	public DaPaoGameStartCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoGameProcessDao loginDao = (DaPaoGameProcessDao) sqlSession.getMapper(ConfigFactory
					.getClazz("4"));
			Map<Object,Object> gameMap = loginDao.selectGameStartByUtoken(params);

			if (gameMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED));
				U.infoQueue("游戏正常开始请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(params.get("cur_role")==null||params.get("cur_pet")==null||params.get("cur_airship")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED_MISS_ARG));
				U.infoQueue("游戏正常开始请求失败：缺少参数 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			try {
				int cur_role=Integer.parseInt(params.get("cur_role"));
				int cur_pet=Integer.parseInt(params.get("cur_pet"));
				int cur_airship=Integer.parseInt(params.get("cur_airship"));
				if(cur_role<1||cur_role>4||cur_pet<1||cur_pet>4||cur_airship<1||cur_airship>4)
				{
					jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_ARG_INVALID);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_GAME_START_FAILED_ARG_INVALID));
					U.infoQueue("游戏正常开始请求失败：参数值非法--1~4之外的范围"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
				long role_value=(long) gameMap.get("prop"+(cur_role-1));
				long pet_value=(long) gameMap.get("prop"+(4+cur_pet-1));
				long airship_value=(long) gameMap.get("prop"+(8+cur_airship-1));
				if(role_value==0||pet_value==0||airship_value==0)
				{
					jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_ARG_INVALID);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_GAME_START_FAILED_ARG_INVALID));
					U.infoQueue("游戏正常开始请求失败：参数值非法--此角色/飞艇/宠物还没有购买"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
				
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED_ARG_INVALID));
				U.infoQueue("游戏正常开始请求失败：参数值非法--非整形 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			
			long last_tili_send_time=(long) gameMap.get("last_tili_send_time");
			
			long hours=DateUtil.getHoursBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long minutes=DateUtil.getMinutesBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long sec=DateUtil.getSecondsBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			//10分钟一颗星
			long tili=(long) gameMap.get("tili");
			int heart=(int) (minutes/10);
			long result=tili+heart;
			if(result>=5) 
			{
				result=5;
			}
			else
			{
				//10分钟为一个单位 取余
				sec=60*10-sec%(60*10);
			}
			
			System.out.println("heart:"+heart+"hours:"+hours+",minutes:"+minutes+",剩余minutes:"+sec/60 );
			String id = (String) gameMap.get("id");
			params.put("uid", gameMap.get("uid") + "");
			params.put("tili", result+"");
			params.put("last_tili_send_time", System.currentTimeMillis()/1000+"");
			
		
			
			
			
			
			
			if (result <= 0)// 体力不够了
			{
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED));
				U.infoQueue("游戏正常开始请求失败：体力不够"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
			params.put("uid", gameMap.get("uid")+"");
			if(heart>0&&tili<5)//如果有人买体力的情况下不用更新数据库中的体力值
			{
				loginDao.updateGetTiliUserGameByTiliAndTime(params);
				sqlSession.commit();
			}
			//更新数据库last_tili_send_time和tili字段
			loginDao.updateUserGameByTili(params);
			sqlSession.commit();
			
			U.infoQueue("id:" + id + "游戏正常开始请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGameStartByUID(params);
			if(result<5)
			{
				gameMap.put("remain", sec);
			}
			jsonObject.put("userInfo", gameMap);
			jsonObject.put(Constant.RET, Constant.RET_GAME_START_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_GAME_START_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
