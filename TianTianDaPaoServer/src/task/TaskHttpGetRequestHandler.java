//package task;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.jboss.netty.channel.Channel;
//import org.jboss.netty.handler.codec.http.QueryStringDecoder;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import client.login.CheckReturnMessage;
//import start.AbstractHttpRequestHandler;
//import start.MessageHandler;
//
//public class TaskHttpGetRequestHandler extends AbstractHttpRequestHandler{
//	private static Logger logger = Logger.getLogger(MessageHandler.class);
//	@Override
//	protected void handle(String uri, Channel channel) {
//		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
//		Map<String, List<String>> params = queryStringDecoder.getParameters();
//		
//		String uid ;
//		try{
//			uid = params.get("uid").get(0);
//			CheckReturnMessage msg = new TaskCheck(Integer.parseInt(uid)).check();
//			logger.info("uid=" + uid +"-------ret=" + msg.arg1);
//			sendResponse(getJSONRegisterResponse((List<HashMap<?, ?>>) msg.obj1,msg.arg1), channel);
//		}catch(Exception e1){		
//				logger.info("注册请求非法参数, ip地址：" + channel.getRemoteAddress());
//				sendResponse(getJSONRegisterResponse(null,2), channel);
//			
//		}	
//		
//	}
//	public String getJSONRegisterResponse(List<HashMap<?, ?>>taskList,int ret) {
//		JSONObject jsonObj = new JSONObject();
//
//		
//		
//		try {
//			switch (ret) {
//			case 0:
//				jsonObj.put("ret", "0");
//				for (int i = 0; i < taskList.size(); i++) {
//					JSONObject json =new JSONObject();
//					json.put("id", taskList.get(i).get("taskid"));
//					json.put("status", taskList.get(i).get("status"));
//					jsonObj.put(i+"", json);
////					System.out.println(taskList.get(i).get("status"));
////					System.out.println(taskList.get(i).get("taskid"));		
//				}
//				System.out.println(jsonObj.toString());
//				
//				break;
//			case -1:			
//				jsonObj.put("ret", "-1");
//				jsonObj.put("msg", "uid不存在");
//				break;
//			case 2:
//				jsonObj.put("ret", "2");
//				jsonObj.put("msg", "注册请求非法参数");
//				break;
//			default:
//				break;
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return jsonObj.toString();
//	}
//}
