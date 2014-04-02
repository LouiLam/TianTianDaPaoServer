package client;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.json.JSONException;
import org.json.JSONObject;

import start.AbstractHttpRequestHandler;
import config.ConfigFactory;
import config.Constant;

public class RegHttpPostRequestHandler extends AbstractHttpRequestHandler {

	private static Logger logger = Logger.getLogger(RegHttpPostRequestHandler.class);

	@Override
	protected void handle(String parametersString, Channel channel) {
			//参数解码
			String decodeuri=null;
			try {
				decodeuri= URLDecoder.decode(parametersString,"utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder("/?" + parametersString);
			
			Map<String, List<String>> params = queryStringDecoder.getParameters();
			Map<String, String> paramClone = new HashMap<String, String>();
			Iterator<String> it = params.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String value = params.get(key).get(0);
				paramClone.put(key, value);
			}
			try {
				JSONObject jsonObj = ConfigFactory
						.getCheck("2").check(paramClone,channel);
				sendResponse(jsonObj.toString(), channel);
			} catch (Exception e) {
				e.printStackTrace();
			} 
	}

}
