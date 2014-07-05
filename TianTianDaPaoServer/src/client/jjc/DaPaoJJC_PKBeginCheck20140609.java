package client.jjc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.DateUtil;
import util.RandomUtil;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoJJC_PKBeginCheck20140609 extends Check {
	public DaPaoJJC_PKBeginCheck20140609() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoJJCDao loginDao = (DaPaoJJCDao) sqlSession.getMapper(ConfigFactory
					.getClazz("3"));
			Map<Object,Object> jjcMap = loginDao.selectJJCUserByUtoken(params);
			if (jjcMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_JJC_PK_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_PK_FAILED));
				U.infoQueue("竞技场挑战请求失败：utoken非法或不存在 "
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
					U.infoQueue("竞技场开始请求失败：参数值非法--1~4之外的范围"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
				long role_value=(long) jjcMap.get("prop"+(cur_role-1));
				if(role_value==0)
				{
					jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_ARG_INVALID);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_GAME_START_FAILED_ARG_INVALID));
					U.infoQueue("游戏正常开始请求失败：参数值非法--此角色/飞艇/宠物还没有购买"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
				params.put("cur_role_level", role_value+"");
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED_ARG_INVALID));
				U.infoQueue("游戏正常开始请求失败：参数值非法--非整形 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			
			
			String id=(String)jjcMap.get("id");
			int day_cur_times=(int) jjcMap.get("day_cur_times");
			int day_max_times=(int) jjcMap.get("day_max_times");
			if(day_cur_times>=day_max_times)
			{
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_COUNT_LIMIT);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_COUNT_LIMIT));
				U.infoQueue("id:"+id+"竞技场非法请求：今天挑战次数已到上限    ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
	long last_tili_send_time=(long) jjcMap.get("last_tili_send_time");
			
			long hours=DateUtil.getHoursBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long minutes=DateUtil.getMinutesBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long sec=DateUtil.getSecondsBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			//10分钟一颗星
			long tili=(long) jjcMap.get("tili");
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
			params.put("uid", jjcMap.get("uid") + "");
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
			params.put("uid", jjcMap.get("uid")+"");
			if(heart>0&&tili<5)//如果有人买体力的情况下不用更新数据库中的体力值
			{
				loginDao.updateGetTiliUserGameByTiliAndTime(params);
				sqlSession.commit();
			}
			//更新数据库last_tili_send_time和tili字段
			loginDao.updateUserGameByCur(params);
			
			
			 List<Map<Object,Object> >pickUpList=loginDao.selectJJCUserByMaxScore20140609(jjcMap);
			 long pickUpUid = 0;
			 
			if(pickUpList==null||pickUpList.size()<=10)//没有比自己分数高的玩家或着为前10的玩家,选取前10名分数高的玩家随机
			{
				pickUpList=loginDao.selectJJCUserByMaxScoreNull20140609(jjcMap);
				int index=RandomUtil.getRan(0, pickUpList.size());
				pickUpUid=(long) pickUpList.get(index).get("uid");
				 System.out.println("pickUpUid:"+pickUpUid);
			}
			else  //有比自己分数高的玩家，随机选取
			{
				int index=RandomUtil.getRan(0, pickUpList.size());
				pickUpUid=(long) pickUpList.get(index).get("uid");
				 System.out.println("pickUpUid:"+pickUpUid);
			}
			params.put("pickUpUid", pickUpUid+"");
			
			Map<Object,Object> pickUPUidMap = loginDao.selectJJCPickUpUserByUID20140609(params);
			jjcMap = loginDao.selectJJCUserByUID(jjcMap);
			//插入战斗日志
			params.put("uid", jjcMap.get("uid")+"");
			params.put("start_time", System.currentTimeMillis()/1000+"");
			params.put("type", 1+"");
			loginDao.insertUserFight20140609(params);
			loginDao.updateUserGameByCur20140609(params);
			long fightID=loginDao.selectLastInsertID20140609();
			sqlSession.commit();
			jjcMap.put("fightID", fightID);
			jsonObject.put("userInfo", jjcMap);
			jsonObject.put("pickUpUserInfo", pickUPUidMap);
			jsonObject.put(Constant.RET, Constant.RET_JJC_PK_BEGIN_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_JJC_PK_BEGIN_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
