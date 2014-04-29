package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpTest {
public static void main(String[] args) {
	
	try {
		httpPost();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public static void  httpGet() throws Exception {
	String url="http://218.76.35.162:4321/v1/user/recharge?uid=10000001&money=2&sign=01ac22cbf28d092f7e254985ed74450f";
	
	
	
	  System.out.println(url);
	
//	String testEncode = URLEncoder.encode("2|3|4", "utf-8" ); 
//	String testEncode1 = URLEncoder.encode("12|13|14", "utf-8" ); 
//	String uu="action=stac&id="+sql_id+"&status=1&gt="+testEncode+"&yt="+testEncode1;	
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        // The underlying HTTP connection is still held by the response object
        // to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources
        // the user MUST either fully consume the response content  or abort request
        // execution by calling CloseableHttpResponse#close().

        try {
        	
        	String str=EntityUtils.toString(response1
						.getEntity());
            HttpEntity entity1 = response1.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            if(response1.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
            {
            
            	 System.out.println(str);
            	//用户点击开始游戏按钮成功
            }
            EntityUtils.consume(entity1);
          
        } finally {
            response1.close();
        }

    } finally {
        httpclient.close();
    }

	
	
	


}
public static void httpPost() {
	CloseableHttpClient httpClient = HttpClients.createDefault();
	HttpPost httpPost = new HttpPost(
			"http://218.76.35.162:4321/v1/user/recharge_mobile_mm?");

	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
	nvps.add(new BasicNameValuePair("uid","10000001"));
	nvps.add(new BasicNameValuePair("money", "2"));
	nvps.add(new BasicNameValuePair("sign", "01ac22cbf28d092f7e254985ed74450f"));
	
	try {
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse httppHttpResponse2 = httpClient
				.execute(httpPost);
		if (httppHttpResponse2.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			String entity = EntityUtils.toString(httppHttpResponse2
					.getEntity());
			
			JSONObject  obj=new JSONObject(entity);
			 System.out.println(obj);
		}
		else
		{
		}
		httppHttpResponse2.close();
		httpClient.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

}
}
