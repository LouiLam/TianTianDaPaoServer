package client.login;

import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

public abstract class Check {
	
	protected Map<String, String> params;
	
	public abstract JSONObject check(Map<String, String> params,Channel channel);

}
