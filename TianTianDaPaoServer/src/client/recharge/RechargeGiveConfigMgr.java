package client.recharge;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import config.AbstractConfig;
import config.GlobalConfig;
/**
 * 充值赠送
 * @author Administrator
 *
 */
public class RechargeGiveConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> taskList;
	public HashMap<Integer,RechargeGive> taskObjMap;
	private static RechargeGiveConfigMgr taskConfigMgr;
	public static RechargeGiveConfigMgr getInstance() {
		if (taskConfigMgr == null) {
			taskConfigMgr = new RechargeGiveConfigMgr();
		}
		return taskConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("rechargeGiveConfigMgr"));
		Element root = (Element) document.getRootElement();
		taskList.clear();
		taskObjMap.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			RechargeGive obj = new RechargeGive();
			HashMap<String, Object> map=new HashMap<String, Object>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.des = task.attributeValue("des");
			obj.rmb=Integer.parseInt(task.attributeValue("rmb"));
			obj.giveDiamond =Integer.parseInt(task.attributeValue("giveDiamond"));
			map.put("id", obj.id);
			map.put("des", obj.des);
			map.put("rmb", obj.rmb);
			map.put("giveDiamond", obj.giveDiamond);
			taskList.add(map);
			taskObjMap.put(obj.rmb, obj);
//			System.out.println(obj.toString());
		}
	}
	private RechargeGiveConfigMgr() {

		taskList = new ArrayList<HashMap<String, Object>>();
		taskObjMap = new HashMap<Integer,RechargeGive>();
	}

}
