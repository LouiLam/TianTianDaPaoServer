package login_reward;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class LoginRewardConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> loginRewardList;
	private static LoginRewardConfigMgr loginRewardConfigMgr;

	public static LoginRewardConfigMgr getInstance() {
		if (loginRewardConfigMgr == null) {
			loginRewardConfigMgr = new LoginRewardConfigMgr();
		}
		return loginRewardConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("loginRewardConfig"));
		Element root = (Element) document.getRootElement();
		for (int i = 0; i < root.elements().size(); i++) {
			LoginReward obj = new LoginReward();
			HashMap<String, Object> map=new HashMap<>();
			Element task = (Element) root.elements().get(i);
			obj.day = Integer.parseInt(task.attributeValue("day"));
		
			obj.name = task.attributeValue("name");
			obj.reward_gold = Integer.parseInt(task
					.attributeValue("reward_gold"));
			obj.reward_charge = Integer.parseInt(task
					.attributeValue("reward_charge"));
			obj.reward_score = Integer.parseInt(task
					.attributeValue("reward_score"));
			obj.reward_diamond = Integer.parseInt(task
					.attributeValue("reward_diamond"));
			map.put("day", obj.day);
			map.put("name", obj.name);
			map.put("reward_gold", obj.reward_gold);
			map.put("reward_charge", obj.reward_charge);
			map.put("reward_score", obj.reward_score);
			map.put("reward_diamond", obj.reward_diamond);
			loginRewardList.add(map);
			
			
		}
	}

	private LoginRewardConfigMgr() {

		loginRewardList = new ArrayList<HashMap<String, Object>>();
	}

}
