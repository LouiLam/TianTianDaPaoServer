package client.recharge;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoGenerateOrdersCheck extends Check {
	public DaPaoGenerateOrdersCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession.getMapper(ConfigFactory
					.getClazz("16"));
			Map<Object,Object> selectMap = loginDao.selectRechargeByUID(params);

			if (params.get("uid") == null||params.get("uid").length()==0||params.get("channelID") == null||params.get("channelID").length()==0) {
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_GENERATE_ORDERS_FAILED_INVALID_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_GENERATE_ORDERS_FAILED_INVALID_ARG));
				U.infoQueue("生成订单请求失败：参数不存在或长度为0"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			try {
				int channelID=Integer.parseInt(params.get("channelID"));
				if(channelID<0||channelID>2)
				{
					return valueInvalid(jsonObject, channel);
				}
			} catch (Exception e) {
				return valueInvalid(jsonObject, channel);
			}
			
			if (selectMap == null) {
				return valueInvalid(jsonObject, channel);
			}
			String id=(String) selectMap.get("id");
			selectMap.put("generate_time", System.currentTimeMillis() / 1000);
			selectMap.put("channelID", 1);
			//生成订单
			loginDao.insertRMBrecord(selectMap);
			selectMap=loginDao.selectOrder(params);
			//返回订单id和生成时间
				U.infoQueue("id:" + id + "订单生成成功，订单号为" +selectMap.get("uid")+ "  ip:"
						+ channel.getRemoteAddress().toString());
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_GENERATE_ORDERS_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_RECHARGE_GENERATE_ORDERS_SUCCESS));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
	private JSONObject valueInvalid(JSONObject jsonObject,Channel channel) throws JSONException
	{
		jsonObject.put(Constant.RET, Constant.RET_RECHARGE_GENERATE_ORDERS_FAILED_ARG_VALUE_INVALID);
		jsonObject
				.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_RECHARGE_GENERATE_ORDERS_FAILED_ARG_VALUE_INVALID));
		U.infoQueue("生成订单请求失败：参数值非法"
				+ channel.getRemoteAddress().toString());
		return jsonObject;
	}
}
