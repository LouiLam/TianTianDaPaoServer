package client.recharge_query;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoRechargeQueryCheck extends Check {
	public DaPaoRechargeQueryCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRechargeQueryDao loginDao = (DaPaoRechargeQueryDao) sqlSession.getMapper(ConfigFactory
					.getClazz("17"));
			Map<Object,Object> selectMap = loginDao.selectOrderByUID(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_RECHARGE_QUERY_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_RECHARGE_QUERY_FAILED));
				U.infoQueue("充值查询请求失败：uid或orderID非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			
			U.infoQueue("id:" + id + "充值查询请求成功!" + "ip:"
					+ channel.getRemoteAddress().toString());
			jsonObject.put("userInfo", selectMap);
			jsonObject.put(Constant.RET, Constant.RET_RECHARGE_QUERY_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_RECHARGE_QUERY_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
