package client.jjc;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class JJCEntryTypeConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,JJCEntryType> taskObjMap;
	private static JJCEntryTypeConfigMgr taskConfigMgr;
	public static JJCEntryTypeConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new JJCEntryTypeConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("jjcEntryTypeConfig"));
		Element root = (Element) document.getRootElement();
		taskList.clear();
		taskObjMap.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			JJCEntryType obj = new JJCEntryType();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.charge=Integer.parseInt(task.attributeValue("charge"));
			obj.diamond=Integer.parseInt(task.attributeValue("diamond"));
			obj.gold=Integer.parseInt(task.attributeValue("gold"));
			obj.score=Integer.parseInt(task.attributeValue("score"));
			obj.consumeGold=Integer.parseInt(task.attributeValue("consumeGold"));
			obj.consumeCharge=Integer.parseInt(task.attributeValue("consumeCharge"));
			obj.consumeDiamond=Integer.parseInt(task.attributeValue("consumeDiamond"));
			
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("charge", obj.charge);
			map.put("diamond", obj.diamond);
			map.put("gold", obj.gold);
			map.put("score", obj.score);
			map.put("consumeGold", obj.consumeGold);
			map.put("consumeCharge", obj.consumeCharge);
			map.put("consumeDiamond", obj.consumeDiamond);
			taskList.add(map);
			taskObjMap.put(obj.id, obj);
//			System.out.println(obj.toString());
		}
	}
	private JJCEntryTypeConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,JJCEntryType>();
	}

}
