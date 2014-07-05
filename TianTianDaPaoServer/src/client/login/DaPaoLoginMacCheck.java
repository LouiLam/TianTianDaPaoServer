package client.login;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import util.DateUtil;
import util.RandomUtil;
import client.login_reward.LoginRewardConfigMgr;
import client.reg.DaPaoRegDao;
import client.task.TaskConfigMgr;
import config.ConfigFactory;
import config.Constant;
import config.GetNoticeConfigMgr;
import config.GetSystemInfoConfigMgr;
import database.DatabaseConnector;

/**
 * mac登录请求
 * @author Administrator
 *
 */
public class DaPaoLoginMacCheck extends Check {
	
	public DaPaoLoginMacCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
			DaPaoLoginDao loginDao = (DaPaoLoginDao) sqlSession.getMapper(
					ConfigFactory.getClazz("1"));
			DaPaoRegDao regDao = (DaPaoRegDao) sqlSession.getMapper(
					ConfigFactory.getClazz("2"));
			
			//检查参数
			if(params.get("mac")==null||params.get("mac").trim().length()==0)//mac必填写
			{
				jsonObject.put(Constant.RET, Constant.RET_LOGIN_FAILED_ARG_INVALID);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_LOGIN_FAILED_ARG_INVALID));
				U.infoQueue("登录请求失败：缺少参数MAC或参数值非法"+channel.getRemoteAddress().toString());
				return jsonObject;
			}
		
			// 登录检测
			Map<Object,Object> userMap = loginDao.selectUserByLoginMac(params);
			// 如果userMap不存在 表示第一次使用设备游客登录,执行注册逻辑
			if (userMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_ACCOUNT_INVALID);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_ACCOUNT_INVALID));
				U.infoQueue("登录请求：MAC"+params.get("mac")+"不存在，执行注册请求 "+	channel.getRemoteAddress().toString());
				int urtime = (int)(System.currentTimeMillis()/1000);
				params.put("urtime", "" + urtime);
				params.put("utoken", AES.generateSessionKey());
				params.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
				params.put("last_tili_send_time", "" + urtime);
				params.put("password", "123456");
				if(params.get("channelID")!=null)
				{
					params.put("channelID", params.get("channelID"));
				}
				else
				{
					params.put("channelID", 908000+"");
				}
				regDao.insertUserInfoByMac(params);
				regDao.updateIDIntoUserInfo(params);
				regDao.insertUserIntoUserJJC(params);
				regDao.insertUserIntoTaskUser(params);
				regDao.insertUserIntoUserProp(params);
				regDao.updateRankIntoUserJJC(params);
				regDao.insertUserIntoUserGame(params);
				regDao.insertUserIntoUserSystemReward(params);
				sqlSession.commit();
				userMap =  regDao.selectUserByMac(params);
				regDao.insert_score_3day(userMap);
				userMap = loginDao.selectUserByLoginMac(params);
				userMap.put("isFirstLogin", 1);
				if(GetNoticeConfigMgr.SIZE>0)
				{userMap.put("isNotice", 1);}
				if(GetSystemInfoConfigMgr.SIZE>0)
				{userMap.put("isSystemInfo", 1);}
				loginProcess(userMap, jsonObject, loginDao, channel);
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
					if(GetNoticeConfigMgr.SIZE>0)
					{userMap.put("isNotice", 1);}
					if(GetSystemInfoConfigMgr.SIZE>0)
					{userMap.put("isSystemInfo", 1);}
					loginProcess(userMap, jsonObject, loginDao, channel);
				
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
	private void loginProcess(Map<Object,Object> userMap,JSONObject jsonObject ,DaPaoLoginDao loginDao ,Channel channel) throws JSONException
	{
		boolean is_get_login_reward_this_day=(boolean) userMap.get("is_get_login_reward_this_day");
		if(!is_get_login_reward_this_day)//返回登录奖励配置信息给客户端做显示
		{
			jsonObject.put("login_reward_config", LoginRewardConfigMgr.getInstance().loginRewardList);
		}
		int ultime = (int)(System.currentTimeMillis()/1000);
		//先检查登录日期，是否是连续登录
		long lastTime=(long) userMap.get("ultime");
		int uconsecutive=(int) userMap.get("uconsecutive");
//	long prc=DateUtil.getDaysBetween(1397722970*1000l, System.currentTimeMillis());
		
		long prc=DateUtil.getDaysBetween(lastTime*1000l, System.currentTimeMillis());
		if(prc==1)//连续登录 uconsecutive+1
		{
			 uconsecutive++;
		}
		else//非连续登录 重置uconsecutive=1
		{
			uconsecutive=1;
		}
		if(uconsecutive==8)//uconsecutive==8的时候 重置uconsecutive=1
		{
			uconsecutive=1;
		}
		userMap.put("ultime", "" + ultime);
		userMap.put("utoken", AES.generateSessionKey());
		userMap.put("uconsecutive", uconsecutive+"");
		userMap.put("ip", channel.getRemoteAddress().toString().substring(1));
		loginDao.updateUserInfoUltime(userMap);

		jsonObject.put("userInfo", userMap);
		jsonObject.put(Constant.RET, Constant.RET_LOGIN_SUCCESS);
		jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_LOGIN_SUCCESS));
		U.infoQueue("帐号:"+userMap.get("id")+"登录成功!"+channel.getRemoteAddress().toString());
	}
}
