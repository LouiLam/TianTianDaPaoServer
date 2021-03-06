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

public class DaPaoRechargeTelecomCheck extends Check {
	public DaPaoRechargeTelecomCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("16"));
			
			if(params.get("sign")==null||params.get("sign").length()==0||params.get("uid")==null||params.get("uid").length()==0||params.get("money")==null||params.get("money").length()==0)
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_MISS_ARG));
				U.infoQueue("游戏充值回调失败：缺少参数sign或uid或money"
						+ channel.getRemoteAddress().toString());
			}
			
			String sign=null;
			long uid = 0;
			int money = 0;
			long order=0;
			try {
				 sign = (String) params.get("sign");
				 uid = Long.parseLong(params.get("uid"));
				 money=Integer.parseInt(params.get("money"));
				 order = Long.parseLong(params.get("order"));
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_INVALID_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_INVALID_ARG));
				U.infoQueue("游戏充值回调失败：参数值格式不正确 "
						+ channel.getRemoteAddress().toString());
			}
			System.out.println("sign:"+ params.get("sign"));
			System.out.println("uid:"+ params.get("uid"));
			System.out.println("money:"+ params.get("money"));
			
			String md5Result=AES.getMD5Str(uid+""+money+order+"zjd.com");
			if(!md5Result.equals(sign))
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_MD5_ERROR);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_MD5_ERROR));
				U.infoQueue("游戏充值回调失败：加密MD5值和原始匹配不合法 对方sign="+sign+"我方md5Result="+md5Result
						+ "        "+channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			params.put("diamond",(money * MoneyAppendConfig.getInstance().ratioDiamond+RechargeGiveConfigMgr.getInstance().taskObjMap.get(money).giveDiamond)+"");
			Map<Object,Object> selectMap = loginDao.selectRechargeByUID(params);
			//获取订单状态
			params.put("order", order+"");
			Map<Object, Object> selectStateMap = loginDao.selectOrderState(params);
			if(selectStateMap==null||(boolean)selectStateMap.get("state"))
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_ORDER);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_ORDER));
				U.infoQueue("电信充值回调失败：订单号不存在或订单已完成"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(selectMap==null) //uid不存在
			{
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_CALLBACK_FAILED_UID_NOT_EXIST);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_CALLBACK_FAILED_UID_NOT_EXIST));
				U.infoQueue("游戏充值回调失败：uid不存在"+channel.getRemoteAddress().toString());
				return jsonObject;
			}
			//更新钻石
		     loginDao.updateDiamondByUserGame(params);
		     
		     
		   //充值成功,写入记录rmbrecord数据库
			selectMap.put("value", money);
			selectMap.put("finish_time", System.currentTimeMillis()/1000);
			selectMap.put("channelID", 0);
			selectMap.put("uid", order);
			loginDao.updateRMBrecord(selectMap);
		     sqlSession.commit();
			U.infoQueue("id:" + selectMap.get("id") + "游戏充值回调成功" + "ip:"
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
