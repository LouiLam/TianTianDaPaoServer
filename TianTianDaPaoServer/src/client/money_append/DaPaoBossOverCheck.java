package client.money_append;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.RandomUtil;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
import event.EveryDayDoSomthing;

public class DaPaoBossOverCheck extends Check {
	public DaPaoBossOverCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoBossOverDao loginDao = (DaPaoBossOverDao) sqlSession.getMapper(ConfigFactory
					.getClazz("15"));
			Map selectMap = loginDao.selectBossOverByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_BOSS_OVER_FAILED));
				U.infoQueue("Boss结束请求话费点失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			int probability=RandomUtil.getRan(1, 101);
		
			if(probability<10)//10%概率
			{
			
				int range=RandomUtil.getRan(1, MoneyAppendConfig.getInstance().charge);
				long BOSSChargeRemain=(long) selectMap.get("boss_charge_remain");
						BOSSChargeRemain-=range;
				if(BOSSChargeRemain<=0)//超过每日奖励限额
				{
					U.infoQueue("id:" + id + "Boss结束请求话费点失败，超过每日奖励限额!" + "ip:"
							+ channel.getRemoteAddress().toString());
					
					jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_FAILED_NOT_PROBABTLITY);
					jsonObject.put(Constant.MSG,
							ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_FAILED_NOT_PROBABTLITY));
					return jsonObject;
				}
				selectMap.put("ucharge", range);
				loginDao.updateChargeByUserGame(selectMap);
				sqlSession.commit();
				U.infoQueue("id:" + id + "Boss结束请求话费点成功，数据更新!系统剩余可获取的话费点为"+BOSSChargeRemain + "ip:"
						+ channel.getRemoteAddress().toString());
				jsonObject.put("get_charge", range);
				jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_SUCCESS));
			}
			else
			{
				U.infoQueue("id:" + id + "Boss结束请求话费点失败，没有随机到匹配的概率!" + "ip:"
						+ channel.getRemoteAddress().toString());
				
				jsonObject.put(Constant.RET, Constant.RET_BOSS_OVER_FAILED_NOT_PROBABTLITY);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BOSS_OVER_FAILED_NOT_PROBABTLITY));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
