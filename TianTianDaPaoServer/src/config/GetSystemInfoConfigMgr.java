package config;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

public class GetSystemInfoConfigMgr extends AbstractConfig {
	public ArrayList<HashMap<String, Object>> getSystemInfoList;
	public HashMap<Integer,GetSystemInfo> getSystemInfoMap;
	private static GetSystemInfoConfigMgr getSystemInfoConfigMgr;
	public static int SIZE;
	public static GetSystemInfoConfigMgr getInstance() {
		if (getSystemInfoConfigMgr == null) {
			getSystemInfoConfigMgr = new GetSystemInfoConfigMgr();
		}
		return getSystemInfoConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("getSystemInfoConfig"));
		Element root = (Element) document.getRootElement();
		getSystemInfoList.clear();
		getSystemInfoMap.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			GetSystemInfo obj = new GetSystemInfo();
			HashMap<String, Object> map=new HashMap<>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
		
			obj.title = task.attributeValue("title");
			obj.content = task.attributeValue("content");
			obj.ugold = Integer.parseInt(task
					.attributeValue("ugold"));
			obj.diamond = Integer.parseInt(task
					.attributeValue("diamond"));
			obj.score = Integer.parseInt(task
					.attributeValue("score"));
			obj.ucharge = Integer.parseInt(task
					.attributeValue("ucharge"));
			obj.prop12 = Integer.parseInt(task
					.attributeValue("prop12"));
			obj.prop13 = Integer.parseInt(task
					.attributeValue("prop13"));
			obj.prop14 = Integer.parseInt(task
					.attributeValue("prop14"));
			obj.prop15 = Integer.parseInt(task
					.attributeValue("prop15"));
			obj.prop16 = Integer.parseInt(task
					.attributeValue("prop16"));
			map.put("id", obj.id);
			map.put("title", obj.title);
			map.put("content", obj.content);
			map.put("ugold", obj.ugold);
			map.put("diamond", obj.diamond);
			map.put("score", obj.score);
			map.put("ucharge", obj.ucharge);
			map.put("prop12", obj.prop12);
			map.put("prop13", obj.prop13);
			map.put("prop14", obj.prop14);
			map.put("prop15", obj.prop15);
			map.put("prop16", obj.prop16);
			getSystemInfoList.add(map);
			getSystemInfoMap.put(obj.id, obj);
			
		}
		SIZE=root.elements().size();
	}

	private GetSystemInfoConfigMgr() {

		getSystemInfoList = new ArrayList<HashMap<String, Object>>();
		getSystemInfoMap=new HashMap<Integer,GetSystemInfo>();
	}

}
