package client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.json.JSONObject;

import start.AbstractHttpRequestHandler;
import start.MessageHandler;
import config.ConfigFactory;

public class LoginHttpPostRequestHandler extends AbstractHttpRequestHandler{

	private static Logger logger = Logger.getLogger(MessageHandler.class);
	
	@Override
	protected void handle(String parametersString, Channel channel) {
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
					.getCheck("1").check(paramClone,channel);
			sendResponse(jsonObj.toString(), channel);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}


}
