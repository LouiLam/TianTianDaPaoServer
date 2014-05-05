package client.charge_exchange;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class ChargeExchangeConfig extends AbstractConfig{

	private static ChargeExchangeConfig chargeExchangeConfig;
	/**
	 *   <!-- 1RMB等于多少话费点 -->
	 */
	public int ratio;
	public static ChargeExchangeConfig getInstance() {
		if (chargeExchangeConfig == null) {
			chargeExchangeConfig = new ChargeExchangeConfig();
		
		}
		return chargeExchangeConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(GlobalConfig.getInstance().getConfigResourceAddress("chargeExchangeConfig"));
		ratio=Integer.parseInt(document.selectSingleNode("/chargeExchange/ratio").getText());
	}
	private ChargeExchangeConfig()  {
		
		
	}
}
