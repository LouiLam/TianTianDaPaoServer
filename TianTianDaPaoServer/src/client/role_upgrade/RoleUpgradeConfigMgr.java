package client.role_upgrade;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class RoleUpgradeConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,RoleUpgrade> taskObjMap;
	private static RoleUpgradeConfigMgr taskConfigMgr;
	public static RoleUpgradeConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new RoleUpgradeConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("roleUpgradeConfig"));
		Element root = (Element) document.getRootElement();
		taskObjMap.clear();
		taskList.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			RoleUpgrade obj = new RoleUpgrade();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.name = task.attributeValue("name");
			obj.max_level=Integer.parseInt(task.attributeValue("max_level"));
			obj.count();
			map.put("id", obj.id);
			map.put("name", obj.name);
			map.put("max_level", obj.max_level);
			
			taskList.add(map);
			taskObjMap.put(obj.id, obj);
		}
	}
	private RoleUpgradeConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,RoleUpgrade>();
	}

}
