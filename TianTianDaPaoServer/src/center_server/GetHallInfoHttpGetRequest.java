package center_server;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import start.ServerConfig;



public class GetHallInfoHttpGetRequest implements Runnable {

	public static JSONObject hallJsonObject;
	
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://"
				+ ServerConfig.getInstance().getCenterServerIP() + ":"
				+ ServerConfig.getInstance().getCenterServerPort()
				+ "/v1/loginServer/getHallServerInfo");
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			String json = EntityUtils.toString(entity);
			try {
				hallJsonObject = new JSONObject(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println(json);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	}

}
