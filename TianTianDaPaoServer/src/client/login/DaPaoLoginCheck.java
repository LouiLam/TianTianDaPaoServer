package client.login;

import java.util.Map;

import login_reward.LoginRewardConfigMgr;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import util.DateUtil;
import client.task.TaskConfigMgr;
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
			DaPaoLoginDao loginDao = sqlSession.getMapper(
					ConfigFactory.getClazz("1"));
			// 登录检测
			Map userMap = loginDao.selectUserByLogin(params);
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
					boolean is_get_login_reward_this_day=(boolean) userMap.get("is_get_login_reward_this_day");
					if(!is_get_login_reward_this_day)//返回登录奖励配置信息给客户端做显示
					{
						jsonObject.put("login_reward_config", LoginRewardConfigMgr.getInstance().loginRewardList);
					}
					int ultime = (int)(System.currentTimeMillis()/1000);
					//先检查登录日期，是否是连续登录
					long lastTime=(long) userMap.get("ultime");
					int uconsecutive=(int) userMap.get("uconsecutive");
					long prc=DateUtil.getDaysBetween(lastTime*1000l, System.currentTimeMillis());
					if(prc==1)//连续登录 uconsecutive+1
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
 
}
