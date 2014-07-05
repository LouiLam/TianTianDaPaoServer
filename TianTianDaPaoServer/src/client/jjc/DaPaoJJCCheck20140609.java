package client.jjc;

import java.util.List;
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

public class DaPaoJJCCheck20140609 extends Check {
	
	public DaPaoJJCCheck20140609() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
			DaPaoJJCDao loginDao = (DaPaoJJCDao) sqlSession.getMapper(
					ConfigFactory.getClazz("3"));
			Map<Object,Object> jjcMap = loginDao.selectJJCUserByUtoken(params);
			
			if (jjcMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_JJC_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_JJC_FAILED));
				U.infoQueue("竞技场请求：utoken非法或不存在 "+	channel.getRemoteAddress().toString());
				return jsonObject;
			}
//			long rank=(long) jjcMap.get("rank");
//			HashMap<String, String> tempMap=new HashMap<String, String>();
//			if(rank>10)
//			{
//			tempMap.put("one", rank-rank/10+"");
//			tempMap.put("two",  rank-rank/10*2+"");
//			tempMap.put("three",  rank-rank/10*3+"");
//			tempMap.put("four",  rank-rank/10*4+"");
//			}
			//世界排名
			List<Object> worldRankMap = loginDao.selectJJCUserByWorldRank20140609(null);
//			//我的排名
//			List<Object> myRankMap = loginDao.selectJJCUserByMyRank(tempMap);
			if((long)jjcMap.get("score_3day")==0)
			{
				long score_3day_pass=DateUtil.getSecondsBetween(DateUtil.getTimesnight(),System.currentTimeMillis())%(1440*60*7); //三天一循环(一天1440分钟，1分钟60秒，3天)
			    jjcMap.put("score_3day_remain", 1440*60*7-score_3day_pass);
			}
				jsonObject.put("userInfo", jjcMap);
				jsonObject.put("worldRankMap", worldRankMap);
				jsonObject.put("jjcEntryType", JJCEntryTypeConfigMgr.getInstance().taskList);
				jsonObject.put(Constant.RET, Constant.RET_JJC_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_JJC_SUCCESS));
				U.infoQueue("帐号"+jjcMap.get("id")+"竞技场请求数据成功!"+"    ip:"+channel.getRemoteAddress().toString());
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
