package client.jjc;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.RandomUtil;
import client.login.Check;
import client.task.Task;
import client.task.TaskConfigMgr;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
//500金币入场，胜利奖励1500金币5积分；
//2000金币入场，赠送6000金币，30积分；
//10话费点入场，胜利获得20话费，20积分，
//40话费点入场，胜利送100话费点，30积分；
//20钻石入场，胜利获得30钻石，赠送积分100；
//200钻石入场，胜利送300钻石，888积分
public class DaPaoJJC_PKEndCheck20140609MM extends Check {
	public DaPaoJJC_PKEndCheck20140609MM() {
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
			if(params.get("maxRange")==null||params.get("fightID")==null||params.get("item0_count")==null||params.get("item1_count")==null||params.get("item2_count")==null||params.get("entryTypeID")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_JJC_PK_FAILED_ARG_INVALID);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_PK_FAILED_ARG_INVALID));
				U.infoQueue("竞技场挑战请求失败：缺少参数item0_count或item1_count或item2_count或entryTypeID或fightID或max_range"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			
			
			
			
			String id=(String)jjcMap.get("id");
			try {
				 Long.parseLong(params.get("gold"));
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_JJC_PK_FAILED_ARG_INVALID);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_PK_FAILED_ARG_INVALID));
				U.infoQueue(id+"竞技场挑战请求失败：gold参数值非法"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
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
			//被动玩家数据
			Map<Object,Object> jjc_pkMap = loginDao.selectJJC_PKUser(params);
			if (jjc_pkMap == null) {
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_NOT_EXIST);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_NOT_EXIST));
				U.infoQueue("id:"+id+"竞技场非法请求：被pk的用户uid不存在      ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
			//被挑战用户的战绩
			long record_beidong=(long)jjc_pkMap.get("jjc_max_record");
			long record_zhudong=Long.parseLong(params.get("record"));
			long sqlRecord=(long) jjcMap.get("jjc_max_record");
			if(record_zhudong<sqlRecord){//如果提交的成绩 大于数据库中的成绩，就更新提交的成绩 否则不提交新成绩
				params.put("record", sqlRecord+"");
			}
			if ((jjcMap.get("uid")+"").equals(params.get("pk_player_uid")+"")) {
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_MYSELF);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_MYSELF));
				U.infoQueue("id:"+id+"竞技场非法请求：被pk的用户为自己的uid   ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			//竞技场消耗 
			int entryTypeID=Integer.parseInt(params.get("entryTypeID"));
			JJCEntryType objJJC=	JJCEntryTypeConfigMgr.getInstance().taskObjMap.get(entryTypeID);
			params.put("jjcDiamond", objJJC.diamond+"");
			params.put("jjcScore", objJJC.score+"");
			params.put("jjcCharge", objJJC.charge+"");
			params.put("jjcGold", objJJC.gold+"");
			params.put("consumeDiamond", objJJC.consumeDiamond+"");
			params.put("consumeCharge", objJJC.consumeCharge+"");
			params.put("consumeGold", objJJC.consumeGold+"");
			if(record_zhudong>record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				params.put("uid_beidong", params.get("pk_player_uid")+"");
				U.infoQueue(" objJJC.consumeDiamond"+ objJJC.consumeDiamond);
				loginDao.updateJJC_RankWin20140609(params);
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(胜利)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else if(record_zhudong==record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankDraw20140609(params);
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(平局)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankLose20140609(params);
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(失败)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			if((long)jjcMap.get("max_range")>Long.parseLong(params.get("maxRange")))
			{
				params.put("max_range", jjcMap.get("max_range")+"");
			}
			else
			{
				params.put("max_range", params.get("maxRange")+"");
			}
			loginDao.updateUserTaskRunning(params);
			loginDao.updateGoldAndDiamondByUserGameMM(params);
			params.put("finish_time", System.currentTimeMillis()/1000+"");
			loginDao.updateUserFight20140609(params);
			sqlSession.commit();
			
			//处理任务相关
			int running_task_id=(int) jjcMap.get("running_task_id");
			Task obj=TaskConfigMgr.getInstance().taskObjMap.get(running_task_id);
			if(obj==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED_MISS_ARG);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED_MISS_ARG));
				U.infoQueue(id+"游戏正常结束请求----相关任务请求失败：running_task_id非法获取到的Task对象为空"
						+ channel.getRemoteAddress().toString());
			}
			else
			{
				switch (running_task_id) {
				case TaskConfigMgr.GOLD:
					if((long)jjcMap.get("gold_count")+Long.parseLong(params.get("gold"))>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", jjcMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;
				case TaskConfigMgr.GAME_COUNT:
					if((long)jjcMap.get("game_count")+1>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", jjcMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;
				case TaskConfigMgr.JJC_PK_COUNT:
					if((long)jjcMap.get("jjc_pk_count")+1>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", jjcMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;
				case TaskConfigMgr.TOTAL_RECORD:
					if((long)jjcMap.get("record_count")+record_zhudong>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", jjcMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;

				default:
					break;
				}
				sqlSession.commit();
			}
			
			
			jjcMap = loginDao.selectJJCUserByUID20140609(jjcMap);
			jsonObject.put("userInfo", jjcMap);
			jsonObject.put(Constant.RET, Constant.RET_JJC_PK_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_JJC_PK_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
