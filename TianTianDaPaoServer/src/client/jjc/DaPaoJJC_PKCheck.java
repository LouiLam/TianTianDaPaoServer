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

public class DaPaoJJC_PKCheck extends Check {
	public DaPaoJJC_PKCheck() {
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
			if(params.get("item0_count")==null||params.get("item1_count")==null||params.get("item2_count")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_JJC_PK_FAILED_ARG_INVALID);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_PK_FAILED_ARG_INVALID));
				U.infoQueue("竞技场挑战请求失败：缺少参数item0_count或item1_count或item2_count"
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
			long  rank_beidong=(long)jjc_pkMap.get("rank");
			long rank_zhudong=(long)jjcMap.get("rank");
			if(rank_beidong>rank_zhudong)
			{
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_RANK_LOWER);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_RANK_LOWER));
				U.infoQueue("id:"+id+"竞技场非法请求：被PK用户排名比发起用户排名低，不允许挑战   ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
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
			
			if(record_zhudong>record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				params.put("rank_beidong", rank_beidong+"");
				params.put("rank_zhudong", rank_zhudong+"");
				params.put("uid_beidong", params.get("pk_player_uid")+"");
				loginDao.updateJJC_RankWin(params);
				loginDao.updateJJC_RankBeidong(params);
//				"被动用户战绩"+record_beidong+"rank_beidong:"+rank_beidong+
//				"uid_beidong"+jjc_pkMap.get("uid")+"主动用户战绩"+record_zhudong+
//				"rank_zhudong:"+rank_zhudong+"uid_zhudong"+jjcMap.get("uid")+"帐号" + jjcMap.get("id") + 
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(胜利)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else if(record_zhudong==record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankDraw(params);
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(平局)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankLose(params);
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(失败)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			loginDao.updateUserTaskRunning(params);
			loginDao.updateGoldAndDiamondByUserGame(params);
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
			}
			
			
			jjcMap = loginDao.selectJJCUserByUID(jjcMap);
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
