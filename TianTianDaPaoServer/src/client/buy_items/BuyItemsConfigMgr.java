package client.buy_items;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class BuyItemsConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,BuyItems> taskObjMap;
	private static BuyItemsConfigMgr taskConfigMgr;
	public static BuyItemsConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new BuyItemsConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("buyItemsConfig"));
		Element root = (Element) document.getRootElement();
		taskList.clear();
		taskObjMap.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			BuyItems obj = new BuyItems();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.consume_ugold=Long.parseLong(task.attributeValue("consume_ugold"));
			obj.consume_ucharge=Long.parseLong(task.attributeValue("consume_ucharge"));
			obj.consume_score=Long.parseLong(task.attributeValue("consume_score"));
			obj.consume_diamond=Long.parseLong(task.attributeValue("consume_diamond"));
			obj.value = task
					.attributeValue("value");
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("consume_gold", obj.consume_ugold);
			map.put("consume_charge", obj.consume_ucharge);
			map.put("consume_score", obj.consume_score);
			map.put("consume_diamond", obj.consume_diamond);
			map.put("value", obj.value);
			taskList.add(map);
			taskObjMap.put(obj.id, obj);
//			System.out.println(obj.toString());
		}
	}
	private BuyItemsConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,BuyItems>();
	}

}
