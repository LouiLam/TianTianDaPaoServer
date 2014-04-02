package client.search;

import java.util.Map;
import java.util.Vector;

import org.jboss.netty.channel.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import client.login.Check;
import config.ConfigFactory;
import config.Constant;

public class YaoPengSearchCheck extends Check {
	private static Vector<String> RoomCollection=new Vector<String>();
	static
	{
		RoomCollection.add("你妈妈的");
	}
	public YaoPengSearchCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < RoomCollection.size(); i++)
		{
			if(RoomCollection.get(i).contains(params.get("keyword")))
			{
				try {
					jsonObject.put("result", params.get("keyword"));
					jsonObject.put(Constant.RET, Constant.RET_SEARCH_SUCCESS);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_SEARCH_SUCCESS));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			
			}
		}
		
//		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
//		try {
//			YaoPengSearchDao regDao = sqlSession.getMapper(
//					ConfigFactory.getClazz("2"));
			// 注册检测
//			Map userMap = regDao.selectUserByUid(params);
//			if (userMap == null) {
//				//id不存在 表示可以注册
//				int urtime = (int)(System.currentTimeMillis()/1000);
//				params.put("urtime", "" + urtime);
//				params.put("ugender", Constant.UGENDER_MALE);
//				if(params.get("uname")==null){params.put("uname", "昵称");}
//				params.put("utoken", AES.generateSessionKey());
//				regDao.insertUserIntoUserInfo(params);
//				sqlSession.commit();
//				userMap =  regDao.selectUserByUid(params);
//				jsonObject.put("userInfo", userMap);
//				jsonObject.put(Constant.RET, Constant.RET_REG_SUCCESS);
//				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_REG_SUCCESS));
//			}
//	
//			//表示id重复不允许注册
//			else {
//				jsonObject.put("userInfo", userMap);
//				jsonObject.put(Constant.RET, Constant.RET_REG_INVALID);
//				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_REG_INVALID));
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		} finally {
//			sqlSession.close();
//		}
		return jsonObject;

	}
}
