package client.score_lottery;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;

public class ScoreLotteryConfigMgr extends AbstractConfig {
	public static final int Role=1;
	public static final int Airship=2;
	public static final int Pet=3;
	public static final int Prop=4;
	public static final int Gold=5;
	public static final int Charge=6;
	public ArrayList<HashMap<String, Object>> taskList;
	public ArrayList<ScoreLottery> taskObjList;
	private static ScoreLotteryConfigMgr taskConfigMgr;
	/**
	 * 积分消耗
	 */
	public static int scoreConsume=0;
	/**
	 * 奖励金币值
	 */
	public static int goldValue=0;
	public static ScoreLotteryConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new ScoreLotteryConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("scoreLotteryConfig"));
		Element root = (Element) document.getRootElement();
		scoreConsume=Integer.parseInt(root.attributeValue("scoreConsume"));
		int total_probability=0;
		taskList.clear();
		taskObjList.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			ScoreLottery obj = new ScoreLottery();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.min_probability=total_probability;
			total_probability+=Integer.parseInt(task.attributeValue("probability"));
			obj.max_probability = total_probability;
			obj.type = Integer.parseInt(task.attributeValue("type"));
			obj.value = task.attributeValue("value");
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("probability", task.attributeValue("probability"));
			map.put("type", obj.type);
			map.put("value", obj.value);
			taskList.add(map);
			taskObjList.add(obj);
			if(obj.type==ScoreLotteryConfigMgr.Gold)
			{
				goldValue= Integer.parseInt(obj.value);
			}
		}
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("scoreConsume", scoreConsume);
		
	}
@Override
public String toString() {
	for (ScoreLottery scoreLottery : taskObjList) {
		System.out.println(scoreLottery.id);
		System.out.println(scoreLottery.min_probability);
		System.out.println(scoreLottery.max_probability);
	}
	return null;
}
	private ScoreLotteryConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjList = new ArrayList<ScoreLottery>();
	}

}
