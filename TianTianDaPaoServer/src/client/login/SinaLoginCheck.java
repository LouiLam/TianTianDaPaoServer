package client.login;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class SinaLoginCheck  extends Check {
	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();
		String json=null;
		
		
		
		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		String upuid=params.get("upuid");
		String token=params.get("token");

		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("https://api.weibo.com/2/users/show.json?access_token="+token+"&uid="+upuid);
			HttpResponse response;
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			json=EntityUtils.toString(entity);
			JSONObject sinaPlatformJson = new JSONObject(json);
			try {
				String name=sinaPlatformJson.getString("name");
				String gender=sinaPlatformJson.getString("gender");
				String uface=sinaPlatformJson.getString("profile_image_url");
				DaPaoLoginDao loginDao = sqlSession.getMapper(
						ConfigFactory.getClazz(params.get("gid")));
				// 先查询UserInfo表中的upuid字段 是否存在相同的upuid
				Map userMap = loginDao.selectUserByLogin(params);
				// 如果upuid不存在 表示第一次使用设备游客登录
				if (userMap == null) {
					
					// 数据库插入纪录
					int urtime = (int)(System.currentTimeMillis()/1000);
					params.put("urtime", "" + urtime);
					params.put("ugender", gender.equals("m")?Constant.UGENDER_MALE:Constant.UGENDER_FEMALE);
					params.put("utoken", token);
					params.put("uname", name);
					params.put("uface", uface);
					loginDao.insertUserIntoUserInfo(params);
//					loginDao.insertUserIntoGameTable(params);
					sqlSession.commit();
					userMap = loginDao.selectUserByLogin(params);
					jsonObject.put("userInfo", userMap);
					jsonObject.put(Constant.RET, Constant.RET_LOGIN_SUCCESS);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_LOGIN_SUCCESS));
				}
				// 如果存在
				else {
					//账号封停
					if(Constant.USTATUS_LOCKOUT.equals("" + userMap.get("ustatus"))){
						jsonObject.put(Constant.RET, Constant.RET_ACCOUNT_LOCKOUT);
						jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_ACCOUNT_LOCKOUT));
					}else if(Constant.USTATUS_NORMAL.equals("" + userMap.get("ustatus"))){
						int ultime = (int)(System.currentTimeMillis()/1000);
						userMap.put("ultime", "" + ultime);
						userMap.put("utoken", token);
						userMap.put("uname", name);
						userMap.put("uface", uface);
//						loginDao.updateUserInfoUltime(userMap);
						loginDao.updateUserinfoSetUname(userMap);
						jsonObject.put("userInfo", userMap);
						jsonObject.put(Constant.RET,  Constant.RET_LOGIN_SUCCESS);
						jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_LOGIN_SUCCESS));
					}
				}
			} catch (JSONException e1) {
				//客户端传来的token和uid 验证不通过。
					jsonObject.put(Constant.RET,Constant.RET_INVALID_TOKEN);
					jsonObject.put(Constant.MSG, sinaPlatformJson.getString("error"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}

}
