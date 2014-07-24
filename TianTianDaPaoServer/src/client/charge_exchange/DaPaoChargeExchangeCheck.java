package client.charge_exchange;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoChargeExchangeCheck extends Check {
	final String KEY = "e63jWLW6yqUDwGwBy5A02cSaJZEOwGHBUkKKbbasRZjK0bVTXQR1sGeHrQktZTaT";

	public DaPaoChargeExchangeCheck() {
		super();
	}

	int count;
	String phone;
	long value ;
	String notify_url;

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoChargeExchangeDao dao = (DaPaoChargeExchangeDao) sqlSession
					.getMapper(ConfigFactory.getClazz("10"));

			Map<Object,Object> selectMap = dao.selectUserByUToken(params);
			if (selectMap == null) {
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_CHARGE_EXCHANGE_FAILED));
				U.infoQueue("兑换话费点请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if (params.get("phone") == null) {
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_FAILED_MISS_ARG);
				jsonObject
						.put(Constant.MSG,
								ConfigFactory
										.getRetMsg(Constant.RET_CHARGE_EXCHANGE_FAILED_MISS_ARG));
				U.infoQueue("兑换话费点请求失败,缺少参数phone"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			count = dao.selectCountByChargerecord();
			phone = params.get("phone");
			if (phone.getBytes().length > 19) {
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_FAILED_PHONE_ERROR);
				jsonObject
						.put(Constant.MSG,
								ConfigFactory
										.getRetMsg(Constant.RET_CHARGE_EXCHANGE_FAILED_PHONE_ERROR));
				U.infoQueue("兑换话费点请求失败，手机号格式错误"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			long ucharge = (long) selectMap.get("ucharge");
			value = ucharge / ChargeExchangeConfig.getInstance().ratio;
			System.out.println("兑换话费value:"+value);
			if (value < 1) {
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_FAILED_CHARGE_NOT_ENOUGH);
				jsonObject
						.put(Constant.MSG,
								ConfigFactory
										.getRetMsg(Constant.RET_CHARGE_EXCHANGE_FAILED_CHARGE_NOT_ENOUGH));
				U.infoQueue("兑换话费点请求失败,话费点不够兑换话费"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			// 判断用户话费点不够兑换话费
			// 兑换面值(10，20，30，50，100，150，200，300，500)
			int result = httpGet();
			if (result == 0)// 兑换成功,扣除话费点,写入chargerecord数据库
			{

				selectMap.put("ucharge",
						value * ChargeExchangeConfig.getInstance().ratio);
				dao.updateChargeByUsergame(selectMap);
				selectMap.put("value", value);
				selectMap.put("time", System.currentTimeMillis() / 1000);
				selectMap.put("phone", phone);
				dao.insertChargerecordByUsergame(selectMap);
				sqlSession.commit();
				selectMap = dao.selectUserByUID(selectMap);
				jsonObject.put("userInfo", selectMap);
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_CHARGE_EXCHANGE_SUCCESS));
				U.infoQueue("id:" + selectMap.get("id") + "兑换话费点请求成功"
						+ channel.getRemoteAddress().toString());
			} else {
				jsonObject.put("result", result);
				jsonObject.put(Constant.RET,
						Constant.RET_CHARGE_EXCHANGE_FAILED_PLATFORM_ERROR);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_CHARGE_EXCHANGE_FAILED_PLATFORM_ERROR));
				U.infoQueue("id:" + selectMap.get("id") + "兑换话费点请求失败，第三方平台错误码"
						+ result + channel.getRemoteAddress().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
public static void main(String[] args) {
	int result=new DaPaoChargeExchangeCheck().httpGet();
	System.out.println(result);
}
	public  int httpGet() {
		String decodeString = "partner=11588&out_trade_id=dapao" + count
				+ "&mobile=" + phone + "&value=" + value + "&type=0"
				+ "&notify_url=" + notify_url + "&" + KEY;
		StringBuffer url = new StringBuffer(
				"http://api.99dou.com/mobile/direct.aspx?partner=11588&out_trade_id=dapao"
						+ count);
		url.append("&mobile=" + phone);
		url.append("&value=" + value);
		url.append("&type=0");
		url.append("&notify_url=" + notify_url);
		url.append("&sign=" + AES.getMD5Str(decodeString));
		url.append("&charset=utf-8");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {

		
			HttpGet httpGet = new HttpGet(url.toString());
			System.out.println(url.toString());
			CloseableHttpResponse response1;

			response1 = httpclient.execute(httpGet);

			try {
				String strResult = EntityUtils.toString(response1.getEntity());
				System.out.println(strResult);
				HttpEntity entity1 = response1.getEntity();
				if (response1.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					Document documentCharge;
					documentCharge = DocumentHelper.parseText(strResult);
					// <return>
					// <result>0</result>
					// <sign>163500DE4E885A9468644423A3433C58</sign>
					// </return>
					int result = Integer.parseInt(documentCharge
							.selectSingleNode("/return/result").getText());
					return result;
				}
				EntityUtils.consume(entity1);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			finally {
				response1.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			  try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return -1;
		//
		// }

	}
	// 0 成功
	// 1 参数错误
	// 2 代理商ID不存在
	// 3 访问被拒绝
	// 4 交易序号重复
	// 5 签名验证失败
	// 6 代理商余额不足
	// 7 不能对该手机号进行充值
	// 8 充值失败
	// 9 没有权限访问该服务
	// 10 超时取消充值
	// 11 面额不存在
	// 12 交易订单不存在
	// 13 该手机号超过24小时内最大充值金额
}
