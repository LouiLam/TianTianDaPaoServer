package client.score_exchange_items;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoScoreExchangeItemsCheck extends Check {
	public DaPaoScoreExchangeItemsCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoScoreExchangeItemsDao loginDao = (DaPaoScoreExchangeItemsDao) sqlSession.getMapper(ConfigFactory
					.getClazz("6"));
			Map<Object,Object> selectMap = loginDao.selectScoreExchangeItemsByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED));
				U.infoQueue("积分兑换物品请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}

			String id = (String) selectMap.get("id");
			long score=(long) selectMap.get("score");
			if(params.get("item_id")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_MISS_ARG));
				U.infoQueue(id+"积分兑换物品请求失败,缺少参数item_id"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			ScoreExchangeItems obj=ScoreExchangeItemsConfigMgr.getInstance().taskObjMap.get(Integer.parseInt(params.get("item_id")));
			if(obj==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_ID_NOT_EXIST);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_ID_NOT_EXIST));
				U.infoQueue(id+"积分兑换物品请求失败,兑换物品ID不存在"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
				
			}
			if (score < obj.consume)// 用户积分不够了，兑换失败
			{
				jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_SCORE_NOT_ENOUGH);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_SCORE_NOT_ENOUGH));
				U.infoQueue(id+"积分兑换物品请求失败：积分不够"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
//			params.put("consume", obj.consume+"");
			//角色兑换只有一次
			if(obj.id>=1&&obj.id<=8)
			{
				long count=(long) selectMap.get(obj.value);
				if(count!=0)
				{
					jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_REPEAT_EXCHANGE);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED_REPEAT_EXCHANGE));
					U.infoQueue(id+"积分兑换物品请求失败：一次性兑换物品不允许重复兑换"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
			}
			StringBuffer sql=new StringBuffer("update userprop,userjjc set ");
			sql.append("userjjc.score=userjjc.score-"+obj.consume);
			sql.append(",userprop."+obj.value+"=userprop."+obj.value+"+1");
			sql.append(" where  userprop.uid="+selectMap.get("uid")+" and userjjc.uid="+selectMap.get("uid"));
	
//			System.out.println(sql.toString());
			loginDao.updateItemByUserJJCAndProp(sql.toString());
			sqlSession.commit();
			U.infoQueue("id:" + id + "兑换"+obj.des+"积分兑换物品请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			
			selectMap = loginDao.selectScoreLotteryByMuch(params);
			jsonObject.put("userInfo", selectMap);
			jsonObject.put("des", obj.des);
			jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
