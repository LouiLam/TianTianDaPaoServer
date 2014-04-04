package client.game_process;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
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
			DaPaoGameProcessDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("4"));
			Map gameMap = loginDao.selectGetTiliByUtoken(params);
		
			if (gameMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED));
				U.infoQueue("游戏正常结束请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			
			if (params.get("record") == null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_OVER_FAILED_MISS_ARG);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GAME_OVER_FAILED_MISS_ARG));
				U.infoQueue("游戏正常结束请求失败：缺少参数record"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) gameMap.get("id");
			long normal_max_record=(long) gameMap.get("normal_max_record");
			params.put("uid", gameMap.get("uid") + "");
			if(Long.parseLong(params.get("record"))>normal_max_record)
			{
				//更新数据库usergame.normal_max_record字段
				loginDao.updateUserGameByRecord(params);
				sqlSession.commit();
			}
		
			U.infoQueue("id:" + id + "游戏正常结束请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGetTiliByUtoken(params);
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
