package client.money_consume;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class MoneyConsumeConfig extends AbstractConfig{

	private static MoneyConsumeConfig moneyConsumeConfig;
	/**
	 *   <!-- 1RMB等于多少话费点 -->
	 */
	public int diamond;
	public static MoneyConsumeConfig getInstance() {
		if (moneyConsumeConfig == null) {
			moneyConsumeConfig = new MoneyConsumeConfig();
		
		}
		return moneyConsumeConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(GlobalConfig.getInstance().getConfigResourceAddress("moneyConsumeConfig"));
		diamond=Integer.parseInt(document.selectSingleNode("/rebirthConsume/diamond").getText());
	}
	private MoneyConsumeConfig()  {
		
		
	}
}
