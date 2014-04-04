package main;

import login_reward.LoginRewardConfigMgr;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;

import client.task.TaskConfigMgr;

import server.ui.main.DaPaoMain;
import server.ui.main.U;
import start.MessageHandler;
import start.ServerConfig;
import start.ServerStart;
import config.ConfigFactory;
import config.GlobalConfig;
import database.DatabaseConnector;

public class LoginMain {

	private static Logger logger = Logger.getLogger(MessageHandler.class);

	/**
	 * @param args
	 * @throws DocumentException
	 */
	public static void main(String[] args) {
		// 客户端登录 http://127.0.0.1:4321/v1/user/login?id=111111&password=111111
		// 客户端注册
		// http://127.0.0.1:4321/v1/user/reg?id=111111&password=111111&uemail=78117253@qq.com&uphone=13975800403
		// 客户端搜索 http://127.0.0.1:4321/v1/user/search?keyword=你妈妈的
		// 客户端创建搜索 http://127.0.0.1:4321/v1/user/search?create=你妈妈的
		try {
			// 日志
			PropertyConfigurator.configure(new GlobalConfig()
					.getConfigResourceAddress("logConfig"));
			U.info("日志启动成功");
			// 登录奖励
			LoginRewardConfigMgr.getInstance().configure();
			U.info("登录奖励配置文件读取成功");
			// 任务
			TaskConfigMgr.getInstance().configure();
			U.info("任务配置文件读取成功");
			// ServerConfig
			ServerConfig.getInstance().configure();
			U.info("ServerConfig配置文件读取成功");
			// 数据库
			DatabaseConnector.getInstance().configure();
			U.info("数据库配置文件读取成功");

			ConfigFactory.init();

			// 起动netty端口监听
			new ServerStart().start();
		} catch (DocumentException e) {
			U.info("启动失败------异常类型：" + e.getMessage());
			e.printStackTrace();
		}

	}

}
