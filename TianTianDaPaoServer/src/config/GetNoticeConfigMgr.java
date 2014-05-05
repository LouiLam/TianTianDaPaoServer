package config;

import java.util.ArrayList;
import java.util.HashMap;

import org.dom4j.DocumentException;
import org.dom4j.Element;

public class GetNoticeConfigMgr extends AbstractConfig{

	public ArrayList<HashMap<String, Object>> getNoticeList;
	private static GetNoticeConfigMgr getNoticeConfigMgr;
	public static int SIZE;
	public static GetNoticeConfigMgr getInstance() {
		if (getNoticeConfigMgr == null) {
			getNoticeConfigMgr = new GetNoticeConfigMgr();
		}
		return getNoticeConfigMgr;
	}


	public void configure() throws DocumentException {
		super.getDocumentByFileAddress(GlobalConfig.getInstance()
				.getConfigResourceAddress("getNoticeConfig"));
		Element root = (Element) document.getRootElement();
		getNoticeList.clear();
		for (int i = 0; i < root.elements().size(); i++) {
			GetNoticeInfo obj = new GetNoticeInfo();
			HashMap<String, Object> map=new HashMap<>();
			Element task = (Element) root.elements().get(i);
			obj.id = Integer.parseInt(task.attributeValue("id"));
			obj.content = task.attributeValue("content");
			obj.title = task.attributeValue("title");
			obj.date = task.attributeValue("date");
			obj.remark = task.attributeValue("remark");
			map.put("id", obj.id);
			map.put("title", obj.title);
			map.put("content", obj.content);
			map.put("date", obj.date);
			map.put("remark", obj.remark);
			getNoticeList.add(map);
			
			
		}
		SIZE= root.elements().size();
	}

	private GetNoticeConfigMgr() {

		getNoticeList = new ArrayList<HashMap<String, Object>>();
	}
}
