package client;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.Element;
import org.jboss.netty.channel.Channel;
import org.json.JSONException;
import org.json.JSONObject;

import server.ui.main.U;
import start.AbstractHttpRequestHandler;
import util.AES;
import util.Dom4jTest;
import client.money_append.MoneyAppendConfig;
import client.recharge.DaPaoRechargeDao;
import client.recharge.RechargeGiveConfigMgr;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

/**
 * 移动MM，充值回调接口
 * 
 * @author Administrator
 * 
 */
public class RechargeMobileMMHttpPostRequestHandler extends
		AbstractHttpRequestHandler {

	@Override
	protected void handle(String parametersString, Channel channel) {
		try {
			// 参数解码
			String decodeuri = null;
			decodeuri = URLDecoder.decode(parametersString, "utf-8");

			// 参数转换
//			QueryStringDecoder queryStringDecoder = new QueryStringDecoder("/?"
//					+ decodeuri);
			// Map<String, List<String>> params =
			// queryStringDecoder.getParameters();
			// Map<String, String> paramClone = new HashMap<String, String>();
			// Iterator<String> it = params.keySet().iterator();
			System.out.println(decodeuri);
			Document documentCharge = Dom4jTest
					.getDocumentByString(decodeuri);
			Element root = (Element) documentCharge.getRootElement();
			String otherSide = root.element("MD5Sign").getText();
			String OrderID = root.element("OrderID").getText();
			String ChannelID = root.element("ChannelID").getText();
			String PayCode = root.element("PayCode").getText();
//			String appKey = "E354F54D162DF3AD";
			String appKey = "EB66FDD1A8C1B886";
			String TransactionID = root.element("TransactionID").getText();
			String uidAndOrderNum[] = root.element("ExData").getText().split("#");
			String uid=uidAndOrderNum[0];
			String orderNum=uidAndOrderNum[1];
		
			// 单位分/100=元
			int TotalPrice = Integer.parseInt(root.element("TotalPrice")
					.getText()) / 100;
			System.out.println("uid:"+uid+"orderNum:"+orderNum+"TotalPrice"+TotalPrice);
			String We = AES.getMD5Str(
					OrderID + "#" + ChannelID + "#" + PayCode + "#" + appKey)
					.toUpperCase();
			String result = null;
			if (We.equals(otherSide)) // md5匹配
			{
				result = Dom4jTest.writeXml(TransactionID);
				U.infoQueue("移动MM回调充值请求成功： " + "ip地址："
						+ channel.getRemoteAddress().toString());
				SqlSession sqlSession = DatabaseConnector.getInstance()
						.getSqlSession();

				DaPaoRechargeDao loginDao = (DaPaoRechargeDao) sqlSession
						.getMapper(ConfigFactory.getClazz("16"));
				HashMap<Object, Object> map = new HashMap<Object, Object>();
				map.put("uid", uid);
				Map<Object, Object> selectMap = loginDao.selectRechargeByUID(map);
				if(selectMap==null)
				{
					result="移动MM回调充值请求发生错误，缺少参数";
					U.infoQueue("移动MM回调充值请求发生错误，缺少参数"
							+ channel.getRemoteAddress().toString());
					sendResponse(result, channel);
					
					return ;
				}
				//获取订单状态
				map.put("order", orderNum);
				Map<Object, Object> selectStateMap = loginDao.selectOrderState(map);
				if(selectStateMap==null||(boolean)selectStateMap.get("state"))
				{
					result="移动MM游戏充值回调失败：订单号不存在或订单已完成";
					U.infoQueue("MDO游戏充值回调失败：订单号不存在或订单已完成"
							+ channel.getRemoteAddress().toString());
					sendResponse(result, channel);
					return ;
				}
				
				selectMap.put("diamond", (TotalPrice * MoneyAppendConfig.getInstance().ratioDiamond+RechargeGiveConfigMgr.getInstance().taskObjMap.get(TotalPrice).giveDiamond)+ "");
				// 更新钻石
				loginDao.updateDiamondByUserGame(selectMap);

				// 充值成功,写入记录rmbrecord数据库
				selectMap.put("value", TotalPrice);
				selectMap.put("finish_time", System.currentTimeMillis() / 1000);
				selectMap.put("channelID", 1);
				selectMap.put("uid", orderNum);
				loginDao.updateRMBrecord(selectMap);
				sqlSession.commit();
				sqlSession.close();
				U.infoQueue("移动MM回调充值请求成功： "  + "ip地址："
						+ channel.getRemoteAddress().toString());
			} else {
				U.infoQueue("移动MM回调充值请求MD5发生不匹配： " + "我方：" + We + "---对方："
						+ otherSide + "ip地址："
						+ channel.getRemoteAddress().toString());
			}
			// System.out.println("result:"+result);
			// System.out.println("uid:"+uid);
			// System.out.println("TotalPrice:"+TotalPrice);
			// System.out.println("OrderID:"+OrderID);

			// while(it.hasNext()){
			// String key = it.next();
			// String value = params.get(key).get(0);
			// paramClone.put(key, value);
			// System.out.println("key:"+key+",value:"+value);
			// }

			// JSONObject jsonObj = ConfigFactory
			// .getCheck("25").check(paramClone,channel);
			// 返回
			sendResponse(result, channel);
		} catch (Exception e) {
			e.printStackTrace();
			U.infoQueue("移动MM回调充值请求发生异常： " + e.getMessage() + "ip地址："
					+ channel.getRemoteAddress().toString() + "    uri = "
					+ parametersString);
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constant.RET, Constant.RET_INVALID_PLATFORM);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_INVALID_PLATFORM));
				sendResponse(jsonObject.toString(), channel);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}

}
