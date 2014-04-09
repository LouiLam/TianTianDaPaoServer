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

			long last_tili_send_time=(long) gameMap.get("last_tili_send_time");
			
			long hours=DateUtil.getHoursBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long minutes=DateUtil.getMinutesBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			long sec=DateUtil.getSecondsBetween(last_tili_send_time*1000l, System.currentTimeMillis());
			//10分钟一颗星
			int tili=(int) gameMap.get("tili");
			int heart=(int) (minutes/10);
			int result=tili+heart;
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
			if(heart>0)
			{
				loginDao.updateGetTiliUserGameByTiliAndTime(params);
				sqlSession.commit();
			}
			//更新数据库last_tili_send_time和tili字段
			loginDao.updateUserGameByTili(params);
			sqlSession.commit();
			
			U.infoQueue("id:" + id + "游戏正常开始请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGetTiliByUtoken(params);
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
