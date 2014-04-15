package client.jjc;

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

public class DaPaoJJC_GetScoreCheck extends Check {
	public DaPaoJJC_GetScoreCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoJJCDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("3"));
			Map jjcMap = loginDao.selectJJCUserByUtoken(params);
			if (jjcMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_JJC_GET_SCORE_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_GET_SCORE_FAILED));
				U.infoQueue("竞技场获取积分请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id=(String)jjcMap.get("id");
			Map scoreMap=loginDao.selectJJC_GetScore(jjcMap);
		
			long score_3day=(long) scoreMap.get("score_3day");
			if(score_3day==0l)
			{
				jsonObject.put(Constant.RET, Constant.RET_JJC_GET_SCORE_FAILED_HAVE_DONE);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_GET_SCORE_FAILED_HAVE_DONE));
				U.infoQueue("id:"+id+"竞技场获取积分请求失败：该用户已领取过积分"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			//更新积分玩家数据
			loginDao.updateJJC_GetScore(jjcMap);
			sqlSession.commit();
			jjcMap = loginDao.selectJJCUserByUtoken(params);
			long score_3day_pass=DateUtil.getSecondsBetween(DateUtil.getTimesnight(),System.currentTimeMillis())%(1440*60*3); //三天一循环(一天1440分钟，1分钟60秒，3天)
		    jjcMap.put("score_3day_remain", 1440*60*3-score_3day_pass);
			jsonObject.put("userInfo", jjcMap);
			jsonObject.put(Constant.RET, Constant.RET_JJC_GET_SCORE_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_JJC_GET_SCORE_SUCCESS));
			U.infoQueue("id:"+id+"竞技场获取积分请求成功"
					+ channel.getRemoteAddress().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
