package client.login;

import java.util.Calendar;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoLoginRewardCheck extends Check {

	public DaPaoLoginRewardCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			// YaoPengLoginDao loginDao = sqlSession.getMapper(
			// ConfigFactory.getClazz(params.get("gid")));
			DaPaoLoginDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("1"));
			// 登录检测
			Map userMap = loginDao.selectJJCUserByUtoken(params);
			if (userMap == null) {
				// 登录请求：帐号不存在或密码错误 返回json
				jsonObject.put(Constant.RET, Constant.RET_LOGIN_REWARD_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_LOGIN_REWARD_FAILED));
				U.infoQueue("领取登录奖励领取失败：utoken非法或不存在"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			else {
				int is_get_this_day=(int) userMap.get("is_get_this_day");
				if(is_get_this_day==0)//0表示可以领取 1表示已经领取过了
				{
					// 领取登录奖励
					loginDao.login_reward(userMap);
					jsonObject.put("userInfo", userMap);
					jsonObject.put(Constant.RET, Constant.RET_LOGIN_REWARD_SUCCESS);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_LOGIN_REWARD_SUCCESS));
					U.infoQueue("帐号:" + userMap.get("id") + "登录奖励领取成功!"
							+ channel.getRemoteAddress().toString());
				}
				else
				{
					jsonObject.put(Constant.RET, Constant.RET_LOGIN_REWARD_FAILED_HAVE_DONE);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_LOGIN_REWARD_FAILED_HAVE_DONE));
					U.infoQueue("帐号:" + userMap.get("id") + "领取登录奖励领取失败：今天该用户已经领取过了"
							+ channel.getRemoteAddress().toString());
				}
			}
		} catch (Exception e) {
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

		return (toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
	}
}
