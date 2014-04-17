package client.consume;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class RebirthConfig extends AbstractConfig{

	private static RebirthConfig rebirthConfig;
	/**
	 *   <!-- 1RMB等于多少话费点 -->
	 */
	public int diamond;
	public static RebirthConfig getInstance() {
		if (rebirthConfig == null) {
			rebirthConfig = new RebirthConfig();
		
		}
		return rebirthConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(new GlobalConfig().getConfigResourceAddress("rebirthConsumeConfig"));
		diamond=Integer.parseInt(document.selectSingleNode("/rebirthConsume/diamond").getText());
	}
	private RebirthConfig()  {
		
		
	}
}
