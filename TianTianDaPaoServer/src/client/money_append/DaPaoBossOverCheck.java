package client.money_append;

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

public class DaPaoBossOverCheck extends Check {
	public DaPaoBossOverCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoBossOverDao loginDao = (DaPaoBossOverDao) sqlSession
					.getMapper(ConfigFactory.getClazz("15"));
			Map<Object, Object> selectMap = loginDao
					.selectBossOverByUtoken(params);

		
			
			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_FAILED));
				U.infoQueue("Boss结束请求话费点失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			
			int range =0;
			if( DaPaoBossOverChargeGenerateCheck.rangeMap.get(selectMap
					.get("uid"))==null)
			{
				U.infoQueue("id:" + id + "Boss结束请求话费点失败---此玩家没有生成话费点"+ "ip:"
						+ channel.getRemoteAddress().toString());
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_FAILED_NO_RANGE);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_FAILED_NO_RANGE));
				return jsonObject;
			}
			range=DaPaoBossOverChargeGenerateCheck.rangeMap.get(selectMap
					.get("uid"));
			long BOSSChargeRemain = (long) selectMap.get("boss_charge_remain");
			BOSSChargeRemain -= range;
			selectMap.put("ucharge", range);
			loginDao.updateChargeByUserGame(selectMap);
			sqlSession.commit();
			selectMap=loginDao.selectBossOverByUID(selectMap);
			U.infoQueue("id:" + id + "Boss结束请求话费点成功---BOSS挂掉，数据更新!系统剩余可获取的话费点为"
					+ BOSSChargeRemain + "ip:"
					+ channel.getRemoteAddress().toString());
			jsonObject.put("userInfo", selectMap);
			jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_SUCCESS));
			
			DaPaoBossOverChargeGenerateCheck.rangeMap.remove(selectMap
					.get("uid"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
