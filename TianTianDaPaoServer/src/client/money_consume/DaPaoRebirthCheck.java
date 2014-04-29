package client.money_consume;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoRebirthCheck extends Check {
	public DaPaoRebirthCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoRebirthDao loginDao = (DaPaoRebirthDao) sqlSession.getMapper(ConfigFactory
					.getClazz("14"));
			Map<Object,Object> selectMap = loginDao.selectUseItemsByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED));
				U.infoQueue("重生请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			if((long)selectMap.get("diamond")<MoneyConsumeConfig.getInstance().diamond)
			{
				jsonObject.put(Constant.RET, Constant.RET_REBIRTH_FAILED_DIAMOND_NOT_ENOUGH);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_REBIRTH_FAILED_DIAMOND_NOT_ENOUGH));
				U.infoQueue(id+"重生请求失败,钻石不够"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			selectMap.put("diamond", MoneyConsumeConfig.getInstance().diamond);
			loginDao.updateDiamondByUserGame(selectMap);
			
			sqlSession.commit();
			U.infoQueue("id:" + id + "重生请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			
			jsonObject.put(Constant.RET, Constant.RET_REBIRTH_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_REBIRTH_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
