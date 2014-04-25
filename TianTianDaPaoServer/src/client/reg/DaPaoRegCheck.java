package client.reg;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import util.RandomUtil;
import util.RegexUtil;
import client.login.Check;
import client.task.TaskConfigMgr;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoRegCheck extends Check {
	
	public DaPaoRegCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
			DaPaoRegDao regDao = (DaPaoRegDao) sqlSession.getMapper(
					ConfigFactory.getClazz("2"));
			// 注册检测
			
			Map userMap = regDao.selectUserByUid(params);
			if(params.get("uphone")==null||params.get("id")==null||params.get("password")==null)//手机号必填写
			{
				jsonObject.put(Constant.RET, Constant.RET_REG_FAILED_MISS_ARG);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_REG_FAILED_MISS_ARG));
				U.infoQueue("id:"+params.get("id")+"注册参数要求非法"+channel.getRemoteAddress().toString());
				return jsonObject;
			}
		
			if (userMap == null) {
				//检测帐号格式
				if( params.get("id")!=null&&!RegexUtil.validateID((String) params.get("id")))
				{
					jsonObject.put(Constant.RET, Constant.RET_ACCOUNT_VALIDATE_INVALID);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_ACCOUNT_VALIDATE_INVALID));
					U.infoQueue("用户注册id:"+params.get("id")+"注册帐号格式非法");
					return jsonObject;
				}
				if( params.get("password")!=null&&!RegexUtil.validatePassword((String) params.get("password")))
				{
					jsonObject.put(Constant.RET, Constant.RET_PASSWORD_VALIDATE_INVALID);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_PASSWORD_VALIDATE_INVALID));
					U.infoQueue("用户注册password:"+params.get("password")+"密码格式非法");
					return jsonObject;
				}
				//id不存在 表示可以注册
				int urtime = (int)(System.currentTimeMillis()/1000);
				params.put("urtime", "" + urtime);
				params.put("utoken", AES.generateSessionKey());
				params.put("running_task_id", RandomUtil.getRan(1, TaskConfigMgr.Size+1)+"");
				params.put("last_tili_send_time", "" + urtime);
				regDao.insertUserIntoUserInfo(params);
				regDao.insertUserIntoUserJJC(params);
				regDao.insertUserIntoTaskUser(params);
				regDao.insertUserIntoUserProp(params);
				regDao.updateRankIntoUserJJC(params);
				regDao.insertUserIntoUserGame(params);
				regDao.insertUserIntoUserSystemReward(params);
				sqlSession.commit();
				userMap =  regDao.selectUserByUid(params);
				regDao.insert_score_3day(userMap);
				jsonObject.put("userInfo", userMap);
				jsonObject.put(Constant.RET, Constant.RET_REG_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_REG_SUCCESS));
				U.infoQueue("用户注册id:"+params.get("id")+"注册成功"+channel.getRemoteAddress().toString());
			}
			// 如果存在//返回消息格式
			//{
			//	"ret":"5","userInfo":
			//		{
			//			"uid":1,"id":"111111","udevice":"0","uname":"用户1","gender":true,"ustatus":0,"ultime":"1393401845","password":"111111","utoken":"02E861ED2BC184C15EDF4C9AF48E5DF7","uface":1,"urtime":1351829267
			//		},
			//	"msg":"注册成功"
			//}
			//表示id重复不允许注册
			else {
//				jsonObject.put("userInfo", userMap);
				jsonObject.put(Constant.RET, Constant.RET_REG_FAILED_ID_REPART);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_REG_FAILED_ID_REPART));
				U.infoQueue("id:"+params.get("id")+"重复不允许注册"+channel.getRemoteAddress().toString());
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
