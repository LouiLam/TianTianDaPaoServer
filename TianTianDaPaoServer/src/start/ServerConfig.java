package start;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class ServerConfig extends AbstractConfig{

	private static ServerConfig serverConfig;

	public static ServerConfig getInstance() {
		if (serverConfig == null) {
				serverConfig = new ServerConfig();
		
		}
		return serverConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(GlobalConfig.getInstance().getConfigResourceAddress("serverConfig"));
	}
	private ServerConfig()  {
		
		
	}

	public int getPort(){
		return Integer.parseInt(document.selectSingleNode("/serverConfig/port").getText());
	}
	public int getCenterServerPort(){
		return Integer.parseInt(document.selectSingleNode("/serverConfig/centerServerPort").getText());
	}
	public String getCenterServerIP(){
		return document.selectSingleNode("/serverConfig/centerServerIP").getText();
	}
}
