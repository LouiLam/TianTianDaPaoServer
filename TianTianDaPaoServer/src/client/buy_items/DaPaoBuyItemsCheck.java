package client.buy_items;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoBuyItemsCheck extends Check {
	public DaPaoBuyItemsCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoBuyItemsDao loginDao = (DaPaoBuyItemsDao) sqlSession.getMapper(ConfigFactory
					.getClazz("7"));
			Map<Object, Object> selectMap = loginDao.selectBuyItemsByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_BUY_ITEMS_FAILED));
				U.infoQueue("购买物品请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}

			String id = (String) selectMap.get("id");
			if(params.get("item_id")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_BUY_ITEMS_FAILED_MISS_ARG));
				U.infoQueue(id+"购买物品请求失败,缺少参数item_id"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			BuyItems obj=BuyItemsConfigMgr.getInstance().taskObjMap.get(Integer.parseInt(params.get("item_id")));
			if(obj==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_FAILED_ID_NOT_EXIST);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_BUY_ITEMS_FAILED_ID_NOT_EXIST));
				U.infoQueue(id+"购买物品请求失败,兑换物品ID不存在"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
				
			}
//			long score=(long) selectMap.get("score");
			long ugold=(long) selectMap.get("ugold");
			long ucharge=(long) selectMap.get("ucharge");
			long diamond=(long) selectMap.get("diamond");
			if (ucharge<obj.consume_ucharge||ugold<obj.consume_ugold||ucharge<obj.consume_ucharge||diamond<obj.consume_diamond)// 用户货币不够了，购买失败
			{
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_FAILED_MONEY_NOT_ENOUGH);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_BUY_ITEMS_FAILED_MONEY_NOT_ENOUGH));
				U.infoQueue(id+"购买物品请求失败：货币不够"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
//			params.put("consume", obj.consume+"");
			//检测一次性购买物品
			if(obj.id>=1&&obj.id<=9)
			{
				long count=(long) selectMap.get(obj.value);
				if(count!=0)
				{
					jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_FAILED_REPEAT_BUY);
					jsonObject
							.put(Constant.MSG, ConfigFactory
									.getRetMsg(Constant.RET_BUY_ITEMS_FAILED_REPEAT_BUY));
					U.infoQueue(id+"购买物品请求失败,一次性购买物品不允许重复购买"
							+ channel.getRemoteAddress().toString());
					return jsonObject;
				}
			}
			
			if(obj.id==15)//幸运金币直接购买 不执行数据库操作
			{
				U.infoQueue("id:" + id +obj.des+"物品购买请求成功，数据更新!" + "ip:"
						+ channel.getRemoteAddress().toString());
				selectMap.put("consume_ugold", obj.consume_ugold);
				loginDao.updateGoldSubByUserGame(selectMap);
				sqlSession.commit();
				selectMap = loginDao.selectBuyItemByMuch(selectMap);
				selectMap.put("buyItemsID", obj.id);
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BUY_ITEMS_SUCCESS));
				return jsonObject;
			}
			if(obj.id>15&&obj.id<19)//体力直接购买
			{
				U.infoQueue("id:" + id +obj.des+"物品购买请求成功，数据更新!" + "ip:"
						+ channel.getRemoteAddress().toString());
				selectMap.put("tili", obj.value);
				selectMap.put("consume_diamond", obj.consume_diamond);
				loginDao.updateTiliByUserGame(selectMap);
				sqlSession.commit();
				selectMap = loginDao.selectBuyItemByMuch(selectMap);
				selectMap.put("buyItemsID", obj.id);
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BUY_ITEMS_SUCCESS));
				return jsonObject;
			}
			if(obj.id>18)//金币直接购买
			{
				U.infoQueue("id:" + id +obj.des+"物品购买请求成功，数据更新!" + "ip:"
						+ channel.getRemoteAddress().toString());
				selectMap.put("gold", obj.value);
				selectMap.put("consume_diamond", obj.consume_diamond);
				loginDao.updateGoldByUserGame(selectMap);
				sqlSession.commit();
				selectMap = loginDao.selectBuyItemByMuch(selectMap);
				selectMap.put("buyItemsID", obj.id);
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_BUY_ITEMS_SUCCESS));
				return jsonObject;
			}
			StringBuffer sql=new StringBuffer("update userprop,userjjc,usergame set ");
			sql.append("userjjc.score=userjjc.score-"+obj.consume_score);
			sql.append(",usergame.ucharge=usergame.ucharge-"+obj.consume_ucharge);
			sql.append(",usergame.ugold=usergame.ugold-"+obj.consume_ugold);
			sql.append(",usergame.diamond=usergame.diamond-"+obj.consume_diamond);
			sql.append(",userprop."+obj.value+"=userprop."+obj.value+"+1");
			sql.append(" where  userprop.uid="+selectMap.get("uid")+" and userjjc.uid="+selectMap.get("uid"));
			sql.append(" and  usergame.uid="+selectMap.get("uid"));
	
//			System.out.println(sql.toString());
			loginDao.updateItemByUserJJCAndProp(sql.toString());
			sqlSession.commit();
			U.infoQueue("id:" + id +obj.des+"物品购买请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			
			
			
			selectMap = loginDao.selectBuyItemByMuch(selectMap);
			selectMap.put("buyItemsID", obj.id);
			jsonObject.put("userInfo", selectMap);
			jsonObject.put(Constant.RET, Constant.RET_BUY_ITEMS_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_BUY_ITEMS_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
