package client.task;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
/**
 * 请求任务进度信息
 * @author Administrator
 *
 */
public class DaPaoTaskRunningCheck extends Check {
	public DaPaoTaskRunningCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
			DaPaoTaskRunningDao loginDao = (DaPaoTaskRunningDao) sqlSession.getMapper(
					ConfigFactory.getClazz("11"));
			Map jjcMap = loginDao.selectTaskUserByUtoken(params);
			
			if (jjcMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_TASK_RUNNING_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_TASK_RUNNING_FAILED));
				U.infoQueue("任务进度请求失败：utoken非法或不存在 "+	channel.getRemoteAddress().toString());
				return jsonObject;
			}
				jsonObject.put("userInfo", jjcMap);
				jsonObject.put(Constant.RET, Constant.RET_TASK_RUNNING_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_TASK_RUNNING_SUCCESS));
				U.infoQueue("帐号"+jjcMap.get("id")+"任务进度请求成功"+"    ip:"+channel.getRemoteAddress().toString());
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
