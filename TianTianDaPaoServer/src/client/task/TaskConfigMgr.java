package client.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class TaskConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> taskList;
	public ArrayList<Task> taskObjList;
	private static TaskConfigMgr taskConfigMgr;

	public static TaskConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new TaskConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("taskConfig"));
		Element root = (Element) document.getRootElement();
		for (int i = 0; i < root.elements().size(); i++) {
			Task obj = new Task();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.target = Integer.parseInt(task.attributeValue("target"));
			obj.reward_gold = Integer.parseInt(task
					.attributeValue("reward_gold"));
			obj.reward_charge = Integer.parseInt(task
					.attributeValue("reward_charge"));
			obj.reward_score = Integer.parseInt(task
					.attributeValue("reward_score"));
			obj.reward_diamond = Integer.parseInt(task
					.attributeValue("reward_diamond"));
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("target", obj.target);
			map.put("reward_gold", obj.reward_gold);
			map.put("reward_charge", obj.reward_charge);
			map.put("reward_score", obj.reward_score);
			map.put("reward_diamond", obj.reward_diamond);
			taskList.add(map);
			taskObjList.add(obj);
		}
	}

	private TaskConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjList = new ArrayList<Task>();
	}

}
