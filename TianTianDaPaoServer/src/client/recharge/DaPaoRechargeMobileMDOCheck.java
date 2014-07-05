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
public class DaPaoRechargeMobileMDOCheck extends Check {
	public DaPaoRechargeMobileMDOCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("16"));
			String uidAndOrder[]=params.get("cpParam").split("#");
			String uid=uidAndOrder[0];
			String order=uidAndOrder[1];
			int TotalPrice=Integer.parseInt(params.get("price"))/100;
			params.put("uid", uid);
			Map<Object, Object> selectMap = loginDao.selectRechargeByUID(params);
			//获取订单状态
			params.put("order", order);
			Map<Object, Object> selectStateMap = loginDao.selectOrderState(params);
			if(selectStateMap==null||(boolean)selectStateMap.get("state"))
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_ORDER);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_ORDER));
				U.infoQueue("MDO游戏充值回调失败：订单号不存在或订单已完成"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(selectMap==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG));
				U.infoQueue("MDO游戏充值回调失败：uid不存在或格式错误"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			selectMap.put("diamond", (TotalPrice * MoneyAppendConfig.getInstance().ratioDiamond+RechargeGiveConfigMgr.getInstance().taskObjMap.get(TotalPrice).giveDiamond) + "");
			// 更新钻石
			loginDao.updateDiamondByUserGame(selectMap);

			// 充值成功,写入记录rmbrecord数据库
			selectMap.put("value", TotalPrice);
			selectMap.put("finish_time", System.currentTimeMillis() / 1000);
			selectMap.put("channelID", 2);
			selectMap.put("uid", order);
			loginDao.updateRMBrecord(selectMap);
			sqlSession.commit();
			
			jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_SUCCESS);
			jsonObject.put(Constant.MSG,
			ConfigFactory.getRetMsg(Constant.RET_RECHARGE_CALLBACK_SUCCESS));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
