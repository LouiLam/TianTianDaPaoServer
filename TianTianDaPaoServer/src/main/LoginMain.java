package main;


import org.apache.log4j.PropertyConfigurator;
import org.dom4j.DocumentException;

import server.ui.main.U;
import start.ServerConfig;
import start.ServerStart;
import client.buy_items.BuyItemsConfigMgr;
import client.charge_exchange.ChargeExchangeConfig;
import client.jjc.JJCEntryTypeConfigMgr;
import client.login_reward.LoginRewardConfigMgr;
import client.money_append.MoneyAppendConfig;
import client.money_consume.MoneyConsumeConfig;
import client.recharge.RechargeGiveConfigMgr;
import client.role_upgrade.RoleUpgradeConfigMgr;
import client.score_exchange_items.ScoreExchangeItemsConfigMgr;
import client.score_lottery.ScoreLotteryConfigMgr;
import client.task.TaskConfigMgr;
import config.ConfigFactory;
import config.GetNoticeConfigMgr;
import config.GetSystemInfoConfigMgr;
import config.GlobalConfig;
import database.DatabaseConnector;
import event.EveryDayDoSomthing;

public class LoginMain {


	public static void ReloadConfig(String code) throws DocumentException
	{
			GlobalConfig.getInstance().setCountryCode(code);
		
			// 日志
			PropertyConfigurator.configure(GlobalConfig.getInstance()
					.getConfigResourceAddress("logConfig"));
			U.info("日志启动成功");
			LoginRewardConfigMgr.getInstance().configure();
			U.info("登录奖励配置文件读取成功");
			TaskConfigMgr.getInstance().configure();
			U.info("任务配置文件读取成功");
			ScoreLotteryConfigMgr.getInstance().configure();
			
			U.info("每日重置全局数据定时器启动");
			ScoreExchangeItemsConfigMgr.getInstance().configure();
			U.info("积分兑换物品配置文件读取成功");
			BuyItemsConfigMgr.getInstance().configure();
			U.info("物品购买配置文件读取成功");
			RoleUpgradeConfigMgr.getInstance().configure();
			U.info("角色升级配置文件读取成功");
			ChargeExchangeConfig.getInstance().configure();
			U.info("话费点兑换比率配置文件读取成功");
			MoneyConsumeConfig.getInstance().configure();
			U.info("货币消耗配置文件读取成功");
			MoneyAppendConfig.getInstance().configure();
			U.info("货币增加配置文件读取成功");
			ServerConfig.getInstance().configure();
			U.info("ServerConfig配置文件读取成功");
			GetNoticeConfigMgr.getInstance().configure();
			U.info("公告配置文件读取成功");
			GetSystemInfoConfigMgr.getInstance().configure();
			U.info("系统信息奖励配置文件读取成功");
			JJCEntryTypeConfigMgr.getInstance().configure();
			U.info("竞技场入场配置文件读取成功");
			RechargeGiveConfigMgr.getInstance().configure();
			U.info("钻石赠送配置文件读取成功");
			// 数据库
			DatabaseConnector.getInstance().configure();
			U.info("数据库配置文件读取成功");
		
			ConfigFactory.init(code);
		
		
			EveryDayDoSomthing.configure();
	}
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
			ReloadConfig("cn");
			new ServerStart().start();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	

	}

}
