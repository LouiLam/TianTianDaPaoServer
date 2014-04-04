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

public class DaPaoGameStartCheck extends Check {
	public DaPaoGameStartCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoGameProcessDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("4"));
			Map gameMap = loginDao.selectGameStartByUtoken(params);

			if (gameMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GAME_START_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_GAME_START_FAILED));
				U.infoQueue("游戏正常开始请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}

			int tili = (int) gameMap.get("tili");
			String id = (String) gameMap.get("id");
			if (tili <= 0)// 体力不够了
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
			// 更新数据库tili字段
			loginDao.updateUserGameByTili(params);
			sqlSession.commit();
			U.infoQueue("id:" + id + "游戏正常开始请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGetTiliByUtoken(params);
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
