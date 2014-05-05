package client.money_append;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class MoneyAppendConfig extends AbstractConfig{

	private static MoneyAppendConfig moneyAppendConfig;
	/**
	 * Boss死亡奖励的话费点最大随即值
	 */
	public int charge;
	/**
	 *   <!-- 1RMB等于多少钻石 -->
	 */
	public int ratioDiamond;
	public static MoneyAppendConfig getInstance() {
		if (moneyAppendConfig == null) {
			moneyAppendConfig = new MoneyAppendConfig();
		
		}
		return moneyAppendConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(GlobalConfig.getInstance().getConfigResourceAddress("moneyAppendConfig"));
		charge=Integer.parseInt(document.selectSingleNode("/moneyAppendConfig/bossOverAppend/charge").getText());
		ratioDiamond=Integer.parseInt(document.selectSingleNode("/moneyAppendConfig/diamindExchange/ratio").getText());
	}
	private MoneyAppendConfig()  {
		
		
	}
}
