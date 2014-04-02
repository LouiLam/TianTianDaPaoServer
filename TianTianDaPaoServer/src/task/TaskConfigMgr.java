package task;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import start.MessageHandler;
import config.AbstractConfig;
import config.GlobalConfig;

public class TaskConfigMgr extends AbstractConfig {
	public ArrayList<Task> taskList;
	private static TaskConfigMgr taskConfigMgr;

	public static TaskConfigMgr getInstance() {
		if (taskConfigMgr == null) {
				taskConfigMgr = new TaskConfigMgr();
		}
		return taskConfigMgr;
	}
 
	private static Logger logger = Logger.getLogger(MessageHandler.class);
	public void configure()  throws DocumentException
	{
		super.getDocumentByFileAddress(new GlobalConfig()
				.getConfigResourceAddress("taskConfig"));
		Element root = (Element) document.getRootElement();
		for (int i = 0; i < root.elements().size(); i++) {
			Task obj = new Task();
			Element task = (Element) root.elements().get(i);
			obj.setId(Integer.parseInt(task.attributeValue("id")));
			obj.setName(task.attributeValue("name"));
			obj.setDes(task.attributeValue("des"));
			obj.setReward(task.attributeValue("reward"));
			obj.setType(task.attributeValue("type"));
			obj.setArg1(Integer.parseInt(task.attributeValue("arg1")));
			obj.setArg2(Integer.parseInt(task.attributeValue("arg2")));
			taskList.add(obj);
		}
		logger.info("================================================");
		logger.info("================读取任务配置成功=================");
		logger.info("================================================");
	}
private TaskConfigMgr() {
		
	taskList= new ArrayList<Task>();
	}

}
