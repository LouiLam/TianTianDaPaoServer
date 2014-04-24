package client.money_append;

import org.dom4j.DocumentException;

import config.AbstractConfig;
import config.GlobalConfig;

public class MoneyAppendConfig extends AbstractConfig{

	private static MoneyAppendConfig moneyAppendConfig;
	public int charge;
	public static MoneyAppendConfig getInstance() {
		if (moneyAppendConfig == null) {
			moneyAppendConfig = new MoneyAppendConfig();
		
		}
		return moneyAppendConfig;
	}
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(new GlobalConfig().getConfigResourceAddress("moneyAppendConfig"));
		charge=Integer.parseInt(document.selectSingleNode("/bossOverAppend/charge").getText());
	}
	private MoneyAppendConfig()  {
		
		
	}
}
