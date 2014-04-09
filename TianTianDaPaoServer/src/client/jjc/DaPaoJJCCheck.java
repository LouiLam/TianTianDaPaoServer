package client.jjc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoJJCCheck extends Check {
	
	public DaPaoJJCCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
//			YaoPengLoginDao loginDao = sqlSession.getMapper(
//					ConfigFactory.getClazz(params.get("gid")));
			DaPaoJJCDao loginDao = sqlSession.getMapper(
					ConfigFactory.getClazz("3"));
			// 登录检测
			//uid=3  rank=3  day_max_times=15  socre=0 diamond
			Map jjcMap = loginDao.selectJJCUserByUtoken(params);
			
			// 如果upuid不存在 表示第一次使用设备游客登录
			if (jjcMap == null) {
				//登录请求：帐号不存在或密码错误 返回json
				jsonObject.put(Constant.RET, Constant.RET_JJC_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_JJC_FAILED));
				U.infoQueue("竞技场请求：utoken非法或不存在 "+	channel.getRemoteAddress().toString());
				return jsonObject;
			}
			long rank=(long) jjcMap.get("rank");
			HashMap<String, String> tempMap=new HashMap<String, String>();
			if(rank>10)
			{
			tempMap.put("one", rank-rank/10+"");
			tempMap.put("two",  rank-rank/10*2+"");
			tempMap.put("three",  rank-rank/10*3+"");
			tempMap.put("four",  rank-rank/10*4+"");
			}
			//世界排名
			List worldRankMap = loginDao.selectJJCUserByWorldRank(tempMap);
//			//我的排名
			List myRankMap = loginDao.selectJJCUserByMyRank(tempMap);
			// 如果存在//返回消息格式
			//{
			//	"ret":"0","userInfo":
			//		{
			//			"uid":1,"id":"111111","udevice":"0","uname":"用户1","gender":true,"ustatus":0,"ultime":"1393401845","password":"111111","utoken":"02E861ED2BC184C15EDF4C9AF48E5DF7","uface":1,"urtime":1351829267
			//		},
			//	"msg":"成功"
			//}
			
				jsonObject.put("userInfo", jjcMap);
				jsonObject.put("worldRankMap", worldRankMap);
				jsonObject.put("myRankMap", myRankMap);
				jsonObject.put(Constant.RET, Constant.RET_JJC_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_JJC_SUCCESS));
				U.infoQueue("帐号"+jjcMap.get("id")+"竞技场请求数据成功!竞技场排名："+rank+"    ip:"+channel.getRemoteAddress().toString());
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
