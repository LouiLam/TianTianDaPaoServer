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

public class DaPaoGetTiliInfoCheck extends Check {
	public DaPaoGetTiliInfoCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoGameProcessDao loginDao = (DaPaoGameProcessDao) sqlSession.getMapper(ConfigFactory
					.getClazz("4"));
			Map gameMap = loginDao.selectGetTiliByUtoken(params);
		
			if (gameMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_GET_TILI_INFO_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_GET_TILI_INFO_FAILED));
				U.infoQueue("获取体力信息请求失败：utoken非法或不存在"
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
			if(result>=5)//如果有人买体力的情况下不能重置为5
			{
				result=5;
			}
			else
			{
				//10分钟为一个单位 取余
				sec=60*10-sec%(60*10);
			}
			
			System.out.println("result:"+result+",heart:"+heart+"hours:"+hours+",minutes:"+minutes+",剩余minutes:"+sec/60 );
			String id = (String) gameMap.get("id");
			params.put("uid", gameMap.get("uid") + "");
			params.put("tili", result+"");
			params.put("last_tili_send_time", System.currentTimeMillis()/1000+"");
			
			if(heart>0&&tili<5) //如果有人买体力的情况下不用更新数据库中的体力值
			//更新数据库last_tili_send_time和tili字段
			{loginDao.updateGetTiliUserGameByTiliAndTime(params);
			sqlSession.commit();
			}
			
		
			U.infoQueue("id:" + id + "获取体力信息请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			gameMap = loginDao.selectGetTiliByUtoken(params);
			if(result<5)
			{
				gameMap.put("remain", sec);
			}
			jsonObject.put("userInfo", gameMap);
			jsonObject.put(Constant.RET, Constant.RET_GET_TILI_INFO_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_GET_TILI_INFO_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
