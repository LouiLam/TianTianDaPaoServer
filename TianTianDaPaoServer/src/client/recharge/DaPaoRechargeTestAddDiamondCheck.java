package client.recharge;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import client.money_append.MoneyAppendConfig;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
//key:province,value:辽宁
//key:price,value:200
//key:cpParam,value:0000000000000131240
//key:serviceId,value:80061
//key:ord,value:25303393
//key:checkCode,value:000
public class DaPaoRechargeTestAddDiamondCheck extends Check {
	public DaPaoRechargeTestAddDiamondCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("16"));
			Map<Object, Object> selectMap = loginDao.selectRechargeByToken(params);
			
			
			if(selectMap==null||params.get("addDiamond")==null||params.get("addDiamond").length()==0)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_TEST_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_TEST_FAILED_MISS_ARG));
				U.infoQueue("游戏测试充值失败：Token不存在或格式错误"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			long addDiamond= Long.parseLong(params.get("addDiamond"));
			if(addDiamond>10000l)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_TEST_FAILED_INVALID_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_TEST_FAILED_INVALID_ARG));
				U.infoQueue("游戏测试充值失败：添加钻石值过大，最大一次为1万"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			// 更新钻石
			long diamond=(long) selectMap.get("diamond");
			if(diamond+addDiamond>999999999)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_TEST_FAILED_OVER_MAX_VALUE);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_TEST_FAILED_OVER_MAX_VALUE));
				U.infoQueue("游戏测试充值失败：已经超过用户钻石值最大值，不能在添加钻石了"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			selectMap.put("diamond", addDiamond);
			loginDao.updateDiamondByUserGame(selectMap);
			sqlSession.commit();
			selectMap = loginDao.selectRechargeByToken(params);
//			// 充值成功,写入记录rmbrecord数据库
//			selectMap.put("value", TotalPrice);
//			selectMap.put("finish_time", System.currentTimeMillis() / 1000);
//			selectMap.put("channelID", 2);
//			selectMap.put("uid", order);
//			loginDao.updateRMBrecord(selectMap);
//			sqlSession.commit();
			jsonObject.put("userInfo",selectMap);
			jsonObject.put(Constant.RET, Constant.RET_RECHARGE_TEST_SUCCESS);
			jsonObject.put(Constant.MSG,
			ConfigFactory.getRetMsg(Constant.RET_RECHARGE_TEST_SUCCESS));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
