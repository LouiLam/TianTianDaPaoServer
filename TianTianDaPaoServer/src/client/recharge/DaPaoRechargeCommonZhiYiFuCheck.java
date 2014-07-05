package client.recharge;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import client.login.Check;
import client.money_append.MoneyAppendConfig;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
//指易付
public class DaPaoRechargeCommonZhiYiFuCheck extends Check {
	public DaPaoRechargeCommonZhiYiFuCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("16"));
		

			String order=params.get("order");
			String uid=params.get("uid");
			int value=Integer.parseInt(params.get("value"));
			String sign=params.get("sign");
			String We = AES.getMD5Str(
					order + "#" + uid + "#" + value + "#" + "zjd.com");
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
				U.infoQueue("指易付游戏充值回调失败：订单号不存在或订单已完成"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(selectMap==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG));
				U.infoQueue("指易付游戏充值回调失败：uid不存在或格式错误"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(!We.equals(sign))
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_MD5_ERROR);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_MD5_ERROR));
				U.infoQueue("指易付游戏充值回调失败：加密MD5值和原始匹配不合法 对方sign="+sign+"我方md5Result="+We
						+ "        "+channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			value=value/100;
			selectMap.put("diamond", (value * MoneyAppendConfig.getInstance().ratioDiamond+RechargeGiveConfigMgr.getInstance().taskObjMap.get(value).giveDiamond) + "");
			// 更新钻石
			loginDao.updateDiamondByUserGame(selectMap);

			// 充值成功,写入记录rmbrecord数据库
			selectMap.put("value", value);
			selectMap.put("finish_time", System.currentTimeMillis() / 1000);
			selectMap.put("channelID", 4);
			selectMap.put("uid", order);
			loginDao.updateRMBrecord(selectMap);
			sqlSession.commit();
			U.infoQueue("指易付游戏充值回调成功：钻石增加"+value/100 * MoneyAppendConfig.getInstance().ratioDiamond
					+ channel.getRemoteAddress().toString());
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
