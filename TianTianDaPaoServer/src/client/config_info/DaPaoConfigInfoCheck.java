package client.config_info;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.AES;
import util.RandomUtil;
import util.RegexUtil;
import client.buy_items.BuyItemsConfigMgr;
import client.charge_exchange.ChargeExchangeConfig;
import client.login.Check;
import client.money_consume.MoneyConsumeConfig;
import client.score_exchange_items.ScoreExchangeItemsConfigMgr;
import client.score_lottery.ScoreLotteryConfigMgr;
import client.task.TaskConfigMgr;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoConfigInfoCheck extends Check {
	
	public DaPaoConfigInfoCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params,Channel channel) {
		
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		
		try {
			DaPaoConfigInfoDao dao = sqlSession.getMapper(
					ConfigFactory.getClazz("8"));
			
			Map selectMap = dao.selectUserByUToken(params);
			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_EXCHANGE_ITEMS_FAILED));
				U.infoQueue("获取配置信息请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			// 如果存在//返回消息格式
			//{
			//	"ret":"5","userInfo":
			//		{
			//			"uid":1,"id":"111111","udevice":"0","uname":"用户1","gender":true,"ustatus":0,"ultime":"1393401845","password":"111111","utoken":"02E861ED2BC184C15EDF4C9AF48E5DF7","uface":1,"urtime":1351829267
			//		},
			//	"msg":"注册成功"
			//}
			//表示id重复不允许注册
			
//				//积分兑换物品配置信息
//				jsonObject.put("scoreExchange", ScoreExchangeItemsConfigMgr.getInstance().taskList);
//				//积分抽奖配置信息
//				jsonObject.put("scoreLottery", ScoreLotteryConfigMgr.getInstance().taskList);
//				//积分抽奖消耗
//				jsonObject.put("scoreLotteryConsume", ScoreLotteryConfigMgr.scoreConsume);
//				//购买道具配置信息
//				jsonObject.put("buyItems", BuyItemsConfigMgr.getInstance().taskList);
//				//返回任务配置信息
//				jsonObject.put("task_config", TaskConfigMgr.getInstance().taskList);
				//用户拥有道具配置信息
				jsonObject.put("userItems", selectMap);
				//话费点兑换比率
				jsonObject.put("chargeExchangeConfig", ChargeExchangeConfig.getInstance().ratio);
				//复活
				jsonObject.put("rebirthConfig", MoneyConsumeConfig.getInstance().diamond);
				jsonObject.put(Constant.RET, Constant.RET_CONFIG_INFO_SUCCESS);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_CONFIG_INFO_SUCCESS));
				U.infoQueue("id:"+selectMap.get("id")+"获取配置信息请求成功"+channel.getRemoteAddress().toString());
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
