package config;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;

import server.ui.main.U;
import start.AbstractHttpRequestHandler;
import client.login.Check;

public class ConfigFactory {


	private static Properties httpRequestHandlerProperties;
	private static Properties checkProperties;
	private static Properties searchCheckProperties;
	private static Properties dbDaoConfigProperties;
	private static Properties timerProperties;
	private static HashMap<Integer, String> retMsgMap;
	
	
	public static void init(){
		try{
			httpRequestHandlerProperties = new Properties();
			FileInputStream fis = new FileInputStream(
					new GlobalConfig().getConfigResourceAddress("httpRequestHandlerConfig"));
			httpRequestHandlerProperties.load(fis);
			fis.close();
			U.info("http请求处理配置成功");
			checkProperties  = new Properties();
			FileInputStream fis1 = new FileInputStream(
					new GlobalConfig().getConfigResourceAddress("CheckConfig"));
			checkProperties.load(fis1);
			fis1.close();
			U.info("check配置成功");
			
		
			dbDaoConfigProperties = new Properties();
			FileInputStream fis2 = new FileInputStream(
					new GlobalConfig().getConfigResourceAddress("dbDaoConfig"));
			dbDaoConfigProperties.load(fis2);
			fis2.close();
			U.info("dbDao配置成功");
			timerProperties=new Properties();
			FileInputStream fis3 = new FileInputStream(
					new GlobalConfig().getConfigResourceAddress("timerConfig"));
			timerProperties.load(fis3);
			fis3.close();
			U.info("时间配置成功");
			
			Document document = new GlobalConfig().getDocumentByFileAddress(new GlobalConfig()
			.getConfigResourceAddress("retMsgConfig"));
			retMsgMap = new HashMap<Integer, String>();
			Element root = (Element) document.getRootElement();
			for (int i = 0; i < root.elements().size(); i++) {
				Element retMsg = (Element) root.elements().get(i);
				retMsgMap.put(Integer.parseInt(retMsg.attributeValue("ret")),retMsg.attributeValue("msg"));
			}
			U.info("返回消息内容与消息码映射配置成功");
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static AbstractHttpRequestHandler getHttpRequestHandler(String requestPath) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (AbstractHttpRequestHandler)(Class.forName(httpRequestHandlerProperties.getProperty(requestPath)).newInstance());
		
	
	}
	public static Check getCheck(String platformId) 
			throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (Check)(Class.forName(checkProperties.getProperty(platformId)).newInstance());
	}
	
	public static Class getClazz(String gameId) throws ClassNotFoundException {
		return Class.forName(dbDaoConfigProperties.getProperty(gameId));
	}
	public static String getTimerConfig(String key)
	{
		return timerProperties.getProperty(key);
	}
	public static String getRetMsg(int ret)  {
		return retMsgMap.get(ret);
	}
	
}
