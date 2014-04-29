package client.use_items;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.buy_items.BuyItems;
import client.buy_items.BuyItemsConfigMgr;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
/**
 * <buyItems id="10" des="超级水晶" consume_ugold="100"
		consume_ucharge="0" consume_score="0" consume_diamond="0" value="prop12"></buyItems>
	<buyItems id="11" des="能量之星" consume_ugold="1200"
		consume_ucharge="0" consume_score="0" consume_diamond="0" value="prop13"></buyItems>
	<buyItems id="12" des="不屈意志" consume_ugold="500"
		consume_ucharge="0" consume_score="0" consume_diamond="0" value="prop14"></buyItems>
	<buyItems id="13" des="水晶护甲" consume_ugold="150"
		consume_ucharge="0" consume_score="0" consume_diamond="0" value="prop15"></buyItems>
	<buyItems id="14" des="永恒之力" consume_ugold="2000"
		consume_ucharge="0" consume_score="0" consume_diamond="0" value="prop16"></buyItems>
 * @author Administrator
 *
 */
public class DaPaoUseItemsCheck extends Check {
	public DaPaoUseItemsCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoUseItemsDao loginDao = (DaPaoUseItemsDao) sqlSession.getMapper(ConfigFactory
					.getClazz("13"));
			Map<Object,Object> selectMap = loginDao.selectUseItemsByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED));
				U.infoQueue("使用物品请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");
			if(params.get("item_id")==null)
			{
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED_MISS_ARG));
				U.infoQueue(id+"使用物品请求失败,缺少参数item_id"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			int item_id;
			try {
				 item_id=Integer.parseInt(params.get("item_id"));
			} catch (Exception e) {
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED_ARG_INVALID));
				U.infoQueue(id+"使用物品请求失败,参数非法---item_id的值不是数值型"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			
			if(item_id<10||item_id>14) //ID非法 ，只能传消耗性道具id
			{
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED_ARG_INVALID);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED_ARG_INVALID));
				U.infoQueue(id+"使用物品请求失败,参数非法----ID非法 ，只能传消耗性道具id"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
		
			
			BuyItems obj=BuyItemsConfigMgr.getInstance().taskObjMap.get(item_id);
			
			if((long)selectMap.get(obj.value)==0)
			{
				jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_FAILED_COUNT_ZERO);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_USE_ITEMS_FAILED_COUNT_ZERO));
				U.infoQueue(id+"使用物品请求失败,物品数量为0"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			StringBuffer sql=new StringBuffer("update userprop set ");
			sql.append("userprop."+obj.value+"=userprop."+obj.value+"-1");
			sql.append(" where  userprop.uid="+selectMap.get("uid"));
	
			loginDao.updateItemByUserProp(sql.toString());
			sqlSession.commit();
			U.infoQueue("id:" + id + "使用"+obj.des+"物品请求成功，数据更新!" + "ip:"
					+ channel.getRemoteAddress().toString());
			
			
			
			jsonObject.put("useItemsID", obj.id);
			jsonObject.put(Constant.RET, Constant.RET_USE_ITEMS_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_USE_ITEMS_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
