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
import client.money_append.DaPaoRechargeDao;
import client.money_append.MoneyAppendConfig;
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
			Document documentCharge = Dom4jTest
					.getDocumentByString(decodeuri);
			Element root = (Element) documentCharge.getRootElement();
			String otherSide = root.element("MD5Sign").getText();
			String OrderID = root.element("OrderID").getText();
			String ChannelID = root.element("ChannelID").getText();
			String PayCode = root.element("PayCode").getText();
			String appKey = "F5A12F2B2F319F93";
			String TransactionID = root.element("TransactionID").getText();
			String uid = root.element("ExData").getText();
			// 单位分/100=元
			int TotalPrice = Integer.parseInt(root.element("TotalPrice")
					.getText()) / 100;
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
				selectMap.put("diamond", TotalPrice * MoneyAppendConfig.getInstance().ratioDiamond + "");
				// 更新钻石
				loginDao.updateDiamondByUserGame(selectMap);

				// 充值成功,写入记录rmbrecord数据库
				selectMap.put("value", TotalPrice);
				selectMap.put("time", System.currentTimeMillis() / 1000);
				selectMap.put("channelID", 1);
				loginDao.insertRMBrecord(selectMap);
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
