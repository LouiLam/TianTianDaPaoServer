package config;

import org.dom4j.DocumentException;

public class GlobalConfig extends AbstractConfig {

	private static GlobalConfig globalConfig;
	private final String CONFIG_REF_CN = "config/GlobalConfig.xml";
	private final String CONFIG_REF_TW = "config/GlobalConfig_tw.xml";

	public static GlobalConfig getInstance() {
		if (globalConfig == null) {
			globalConfig = new GlobalConfig();
		}
		return globalConfig;
	}

	private GlobalConfig() {
	}

	public void setCountryCode(String code) throws DocumentException {
		if (code.equalsIgnoreCase("cn"))
			super.getDocumentByFileAddress(CONFIG_REF_CN);
		else if (code.equalsIgnoreCase("tw")) {
			super.getDocumentByFileAddress(CONFIG_REF_TW);
		}
	}

	public String getConfigResourceAddress(String configName) {
		return document.selectSingleNode("/globalConfig/" + configName)
				.getText();
	}

}
