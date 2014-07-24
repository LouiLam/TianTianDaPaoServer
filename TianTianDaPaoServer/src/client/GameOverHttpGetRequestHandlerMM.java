package client;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.json.JSONException;
import org.json.JSONObject;

import server.ui.main.U;
import start.AbstractHttpRequestHandler;
import config.ConfigFactory;
import config.Constant;
/**
 * 普通游戏结束 MM版本
 * @author Administrator
 *
 */
public class GameOverHttpGetRequestHandlerMM extends AbstractHttpRequestHandler {


	@Override
	protected void handle(String uri, Channel channel) {
		try{
			//参数解码
			String decodeuri=null;
			decodeuri= URLDecoder.decode(uri,"utf-8");
			
			//参数转换
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(decodeuri);
			Map<String, List<String>> params = queryStringDecoder.getParameters();
			Map<String, String> paramClone = new HashMap<String, String>();
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = params.get(key).get(0);
				paramClone.put(key, value);
			}
			

			JSONObject jsonObj = ConfigFactory
					.getCheck("40").check(paramClone,channel);
			//返回
			sendResponse(jsonObj.toString(), channel);
		}catch(Exception e){	
			e.printStackTrace();
			U.infoQueue("MM游戏正常结束请求发生异常： "+e.getMessage()+"ip地址："
					+ channel.getRemoteAddress().toString()+"    uri = " + uri);
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put(Constant.RET, Constant.RET_INVALID_PLATFORM);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_INVALID_PLATFORM));
				sendResponse(jsonObject.toString(), channel);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
	}

}
