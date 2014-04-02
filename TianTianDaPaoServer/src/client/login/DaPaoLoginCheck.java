package client.login;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoLoginCheck extends Check {
	
	public DaPaoLoginCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
//			YaoPengLoginDao loginDao = sqlSession.getMapper(
//					ConfigFactory.getClazz(params.get("gid")));
			DaPaoLoginDao loginDao = sqlSession.getMapper(
					ConfigFactory.getClazz("1"));
			// 登录检测
			Map userMap = loginDao.selectUserByUpuid(params);
			// 如果upuid不存在 表示第一次使用设备游客登录
			if (userMap == null) {
				//登录请求：帐号不存在或密码错误 返回json
				jsonObject.put(Constant.RET, Constant.RET_ACCOUNT_INVALID);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_ACCOUNT_INVALID));
				U.infoQueue("登录请求：帐号不存在或密码错误 "+	channel.getRemoteAddress().toString());
			}
			// 如果存在//返回消息格式
			//{
			//	"ret":"0","userInfo":
			//		{
			//			"uid":1,"id":"111111","udevice":"0","uname":"用户1","gender":true,"ustatus":0,"ultime":"1393401845","password":"111111","utoken":"02E861ED2BC184C15EDF4C9AF48E5DF7","uface":1,"urtime":1351829267
			//		},
			//	"msg":"成功"
			//}
			else {
				//账号封停
				if(Constant.USTATUS_LOCKOUT.equals("" + userMap.get("ustatus"))){
					jsonObject.put(Constant.RET, Constant.RET_ACCOUNT_LOCKOUT);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_ACCOUNT_LOCKOUT));
				}else if(Constant.USTATUS_NORMAL.equals("" + userMap.get("ustatus"))){
					int ultime = (int)(System.currentTimeMillis()/1000);
					
					
				
					//先检查登录日期，是否是连续登录
					long lastTime=(long) userMap.get("ultime");
					int uconsecutive=(int) userMap.get("uconsecutive");
					long prc=getDaysBetween(lastTime*1000l, System.currentTimeMillis());
					if(getDaysBetween(lastTime*1000l, System.currentTimeMillis())==1)//连续登录 uconsecutive+1
					{
						 uconsecutive++;
//						 System.out.println("连续登录 uconsecutive+1");
					}
					userMap.put("ultime", "" + ultime);
					userMap.put("utoken", AES.generateSessionKey());
					userMap.put("uconsecutive", uconsecutive+"");
					
					loginDao.updateUserInfoUltime(userMap);
			
					jsonObject.put("userInfo", userMap);
					jsonObject.put(Constant.RET, Constant.RET_LOGIN_SUCCESS);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_LOGIN_SUCCESS));
					U.infoQueue("帐号:"+userMap.get("id")+"登录成功!"+channel.getRemoteAddress().toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
	public static long getDaysBetween(long startDate, long endDate) {   
        Calendar fromCalendar = Calendar.getInstance();   
        fromCalendar.setTimeInMillis(startDate);   
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);   
        fromCalendar.set(Calendar.MINUTE, 0);   
        fromCalendar.set(Calendar.SECOND, 0);   
        fromCalendar.set(Calendar.MILLISECOND, 0);   
  
        Calendar toCalendar = Calendar.getInstance();   
        toCalendar.setTimeInMillis(endDate);   
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);   
        toCalendar.set(Calendar.MINUTE, 0);   
        toCalendar.set(Calendar.SECOND, 0);   
        toCalendar.set(Calendar.MILLISECOND, 0);   
  
        return (toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24);   
    }  
}
