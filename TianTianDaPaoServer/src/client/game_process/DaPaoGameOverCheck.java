package client.game_process;

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

public class DaPaoGameOverCheck extends Check {
	public DaPaoGameOverCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoGameProcessDao loginDao = (DaPaoGameProcessDao) sqlSession.getMapper(ConfigFactory
					.getClazz("4"));
			Map gameMap = loginDao.selectGameEndByUtoken(params);
		
			if (gameMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED));
				U.infoQueue("游戏正常结束请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			String id = (String) gameMap.get("id");
			if (params.get("record") == null||params.get("gold")==null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED_MISS_ARG);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED_MISS_ARG));
				U.infoQueue(id+"游戏正常结束请求失败：缺少参数record或gold"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			long record;
			try {
				 record=Long.parseLong(params.get("record"));
				 Long.parseLong(params.get("gold"));
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED_ARG_INVALID);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED_ARG_INVALID));
				U.infoQueue(id+"游戏正常结束请求失败：参数值非法"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}

		
			
			//正常游戏结束处理
			long normal_max_record=(long) gameMap.get("normal_max_record");
			params.put("uid", gameMap.get("uid") + "");
			if(record>normal_max_record)
			{
				//更新数据库usergame.normal_max_record字段
				loginDao.updateRecordByUserGame(params);
				
			}
			loginDao.updateGoldByUserGame(params);
			loginDao.updateUserTaskRunning(params);
			sqlSession.commit();
			//处理任务相关
			int running_task_id=(int) gameMap.get("running_task_id");
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
					if((long)gameMap.get("gold_count")+Long.parseLong(params.get("gold"))>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", gameMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;
				case TaskConfigMgr.GAME_COUNT:
					if((long)gameMap.get("game_count")+1>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", gameMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;
				case TaskConfigMgr.JJC_PK_COUNT:
					
					break;
				case TaskConfigMgr.TOTAL_RECORD:
					if((long)gameMap.get("record_count")+record>=obj.target)//目标达到，任务完成，任务奖励,清空用户任务列表数据
					{
						//任务暂不奖励积分
						HashMap<String, Object>map=new HashMap<String, Object>();
						map.put("reward_gold", obj.reward_gold);
						map.put("reward_charge", obj.reward_charge);
						map.put("reward_diamond", obj.reward_diamond);
						map.put("uid", gameMap.get("uid"));
						map.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
						loginDao.updateUserTaskFinish(map);
						jsonObject.put("taskFinishID", obj.id);
					}
					break;

				default:
					break;
				}
			}
			
			U.infoQueue("id:" + id + "游戏正常结束请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGameEndByUID(params);
			jsonObject.put("userInfo", gameMap);
			jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
