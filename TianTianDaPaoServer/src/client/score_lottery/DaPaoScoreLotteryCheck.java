package client.score_lottery;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import util.RandomUtil;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;
import event.EveryDayDoSomthing;

public class DaPaoScoreLotteryCheck extends Check {
	public DaPaoScoreLotteryCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoScoreLotteryDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("5"));
			Map selectMap = loginDao.selectScoreLotteryByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_SCORE_LOTTERY_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_LOTTERY_FAILED));
				U.infoQueue("游戏积分抽奖请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}

			String id = (String) selectMap.get("id");
			long score=(long) selectMap.get("score");
			if (score <= ScoreLotteryConfigMgr.scoreConsume)// 积分不够了，抽奖失败
			{
				jsonObject.put(Constant.RET, Constant.RET_SCORE_LOTTERY_FAILED_SCORE_NOT_ENOUGH);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_SCORE_LOTTERY_FAILED_SCORE_NOT_ENOUGH));
				U.infoQueue(id+"积分抽奖请求失败：积分不够"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
		
			int probability=RandomUtil.getRan(0, 1000);
			String gold="",charge="";
			ScoreLottery obj=null;
			for (int i = 0; i < ScoreLotteryConfigMgr.getInstance().taskObjList.size(); i++) {
				 obj=ScoreLotteryConfigMgr.getInstance().taskObjList.get(i);
				if(probability>=obj.min_probability&&probability<obj.max_probability)
				{
					if(obj.type==ScoreLotteryConfigMgr.Gold) //等于金币直接处理
					{
						params.put("ugold", obj.value+"");
						params.put("uid", selectMap.get("uid")+"");
						params.put("score_consume", ScoreLotteryConfigMgr.scoreConsume+"");
						loginDao.updateUGoldByUserGame(params);
						sqlSession.commit();
						gold=obj.value;
					}
					else if(obj.type==ScoreLotteryConfigMgr.Charge) //等于话费点直接处理
					{
					
						if(EveryDayDoSomthing.LotteryChargeRemain<=0)//每日话费点限额已用完，奖励金币
						{
							obj=ScoreLotteryConfigMgr.getInstance().taskObjList.get(11);//11索引表示金币
							obj.des+="每日话费点限额已用完，奖励金币";
							params.put("ugold", obj.value+"");
							params.put("uid", selectMap.get("uid")+"");
							params.put("score_consume", ScoreLotteryConfigMgr.scoreConsume+"");
							loginDao.updateUGoldByUserGame(params);
							sqlSession.commit();
							gold=obj.value;
						}
						else
						{
							int range=Integer.parseInt(obj.value)+1;
							EveryDayDoSomthing.LotteryChargeRemain-=range;
							//话费点1~10随机
							params.put("ucharge", RandomUtil.getRan(1, range)+"");
							params.put("uid", selectMap.get("uid")+"");
							params.put("score_consume", ScoreLotteryConfigMgr.scoreConsume+"");
							loginDao.updateUChargeByUserGame(params);
							sqlSession.commit();
							charge=range+"";
						}
					
					}
					else //道具处理
					{
						long count=(long) selectMap.get(obj.value);
						if(count!=0&&obj.id>=1&&obj.id<=6) //已经拥有此一次性拥有道具,就奖励普通抽奖金币值X2
						{
							params.put("ugold", ScoreLotteryConfigMgr.goldValue*2+"");
							params.put("uid", selectMap.get("uid")+"");
							params.put("score_consume", ScoreLotteryConfigMgr.scoreConsume+"");
							loginDao.updateUGoldByUserGame(params);
							sqlSession.commit();
							gold= ScoreLotteryConfigMgr.goldValue*2+"";
							 obj=ScoreLotteryConfigMgr.getInstance().taskObjList.get(11);//11索引表示金币
							 obj.des+="已经拥有此一次性拥有道具,就奖励普通抽奖金币值X2";
						}
						else   //非一次性道具 ，数量递增
						{
							StringBuffer sql=new StringBuffer("update userprop,userjjc set ");
							sql.append("userjjc.score=userjjc.score-"+ScoreLotteryConfigMgr.scoreConsume);
							sql.append(",userprop."+obj.value+"=userprop."+obj.value+"+1");
							sql.append(" where  userprop.uid="+selectMap.get("uid")+" and userjjc.uid="+selectMap.get("uid"));
							loginDao.updateItemByUserProp(sql.toString());
							sqlSession.commit();
						}
					}
					
					
				
					break;
				}
			}
			
			U.infoQueue(id + "抽取"+obj.des+"积分抽奖请求成功，数据更新!系统剩余可抽取的话费点为"+EveryDayDoSomthing.LotteryChargeRemain + "ip:"
					+ channel.getRemoteAddress().toString());
			selectMap = loginDao.selectScoreLotteryByMuch(params);
			jsonObject.put("userInfo", selectMap);
			jsonObject.put("item_id", obj.id);
			if(gold.length()!=0)
			{jsonObject.put("gold",Integer.parseInt(gold));}
			if(charge.length()!=0)
			{
				jsonObject.put("charge",Integer.parseInt(charge));
			}
			jsonObject.put(Constant.RET, Constant.RET_SCORE_LOTTERY_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_SCORE_LOTTERY_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}

}
