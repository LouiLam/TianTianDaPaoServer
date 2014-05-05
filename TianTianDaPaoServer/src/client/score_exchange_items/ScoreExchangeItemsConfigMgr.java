package client.score_exchange_items;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class ScoreExchangeItemsConfigMgr extends AbstractConfig {
	public static final int Role=1;
	public static final int Airship=2;
	public static final int Pet=3;
	public static final int Prop=4;
	public static final int Gold=5;
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,ScoreExchangeItems> taskObjMap;
	private static ScoreExchangeItemsConfigMgr taskConfigMgr;
	public static ScoreExchangeItemsConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new ScoreExchangeItemsConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("scoreExchangeItemsConfig"));
		Element root = (Element) document.getRootElement();
		taskList.clear();
		taskObjMap.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			ScoreExchangeItems obj = new ScoreExchangeItems();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.consume=Integer.parseInt(task.attributeValue("consume"));
			obj.value = task
					.attributeValue("value");
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("consume", obj.consume);
			map.put("value", obj.value);
			taskList.add(map);
			taskObjMap.put(obj.id, obj);
//			System.out.println(obj.toString());
		}
	}
	private ScoreExchangeItemsConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,ScoreExchangeItems>();
	}

}
