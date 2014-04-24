package client.task;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

/**
 * 任务管理器，游戏结束时做相关操作
 * @author Administrator
 *
 */
public class TaskConfigMgr extends AbstractConfig {
	/**
	 * 需要达到金币数
	 */
	public static final int GOLD=1;
	/**
	 * 需要达到的竞技场挑战次数
	 */
	public static final int JJC_PK_COUNT=2;
	/**
	 * 需要达到的当日总成绩
	 */
	public static final int TOTAL_RECORD=3;
	/**
	 * 需要达到的当前游戏次数
	 */
	public static final int GAME_COUNT=4;
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,Task> taskObjMap;
	private static TaskConfigMgr taskConfigMgr;

	public static TaskConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new TaskConfigMgr();
		}
		return taskConfigMgr;
	}
	public static int Size;

	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("taskConfig"));
		Element root = (Element) document.getRootElement();
		taskList.clear();
		taskObjMap.clear();
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
			
			obj.type=Integer.parseInt(task
					.attributeValue("type"));
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("type", obj.type);
			map.put("target", obj.target);
			map.put("reward_gold", obj.reward_gold);
			map.put("reward_charge", obj.reward_charge);
			map.put("reward_score", obj.reward_score);
			map.put("reward_diamond", obj.reward_diamond);
			taskList.add(map);
			taskObjMap.put(obj.id,obj);
		}
		Size=root.elements().size();
	}

	private TaskConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,Task>();
	}

}
