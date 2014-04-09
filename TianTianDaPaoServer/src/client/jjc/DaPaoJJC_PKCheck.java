package client.jjc;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoJJC_PKCheck extends Check {
	public DaPaoJJC_PKCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			// YaoPengLoginDao loginDao = sqlSession.getMapper(
			// ConfigFactory.getClazz(params.get("gid")));
			DaPaoJJCDao loginDao = sqlSession.getMapper(ConfigFactory
					.getClazz("3"));
			// 登录检测
			// uid=3 rank=3 day_max_times=15 socre=0 diamond
			Map jjcMap = loginDao.selectJJCUserByUtoken(params);
			// 如果upuid不存在 表示第一次使用设备游客登录
			if (jjcMap == null) {
				// 登录请求：帐号不存在或密码错误 返回json
				jsonObject.put(Constant.RET, Constant.RET_JJC_PK_FAILED);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_JJC_PK_FAILED));
				U.infoQueue("竞技场挑战请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id=(String)jjcMap.get("id");
			int day_cur_times=(int) jjcMap.get("day_cur_times");
			int day_max_times=(int) jjcMap.get("day_max_times");
			if(day_cur_times>=day_max_times)
			{
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_COUNT_LIMIT);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_COUNT_LIMIT));
				U.infoQueue("id:"+id+"竞技场非法请求：今天挑战次数已到上限    ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			//被动玩家数据
			Map jjc_pkMap = loginDao.selectJJC_PKUser(params);
			if (jjc_pkMap == null) {
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_NOT_EXIST);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_NOT_EXIST));
				U.infoQueue("id:"+id+"竞技场非法请求：被pk的用户uid不存在      ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;

			}
			//被挑战用户的战绩
			long record_beidong=(long)jjc_pkMap.get("jjc_max_record");
			long record_zhudong=Long.parseLong(params.get("record"));
			long  rank_beidong=(long)jjc_pkMap.get("rank");
			long rank_zhudong=(long)jjcMap.get("rank");
			if(rank_beidong>rank_zhudong)
			{
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_MYSELF);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_MYSELF));
				U.infoQueue("id:"+id+"竞技场非法请求：被PK用户排名比发起用户排名低，不允许挑战   ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			long sqlRecord=(long) jjcMap.get("jjc_max_record");
			if(record_zhudong<sqlRecord){//如果提交的成绩 大于数据库中的成绩，就更新提交的成绩 否则不提交新成绩
				params.put("record", sqlRecord+"");
			}
			if ((jjcMap.get("uid")+"").equals(params.get("pk_player_uid")+"")) {
				jsonObject.put(Constant.RET,
						Constant.RET_JJC_PK_FAILED_UID_MYSELF);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_JJC_PK_FAILED_UID_MYSELF));
				U.infoQueue("id:"+id+"竞技场非法请求：被pk的用户为自己的uid   ip:"
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			
			if(record_zhudong>record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				params.put("rank_beidong", rank_beidong+"");
				params.put("rank_zhudong", rank_zhudong+"");
				params.put("uid_beidong", params.get("pk_player_uid")+"");
				loginDao.updateJJC_RankWin(params);
				loginDao.updateJJC_RankBeidong(params);
				sqlSession.commit();
//				"被动用户战绩"+record_beidong+"rank_beidong:"+rank_beidong+
//				"uid_beidong"+jjc_pkMap.get("uid")+"主动用户战绩"+record_zhudong+
//				"rank_zhudong:"+rank_zhudong+"uid_zhudong"+jjcMap.get("uid")+"帐号" + jjcMap.get("id") + 
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(胜利)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else if(record_zhudong==record_beidong)
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankDraw(params);
				sqlSession.commit();
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(平局)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			else
			{
				params.put("uid",jjcMap.get("uid")+"");
				loginDao.updateJJC_RankLose(params);
				sqlSession.commit();
				U.infoQueue("id:"+id+"竞技场挑战请求成功，数据更新(失败)!" + "ip:"
						+ channel.getRemoteAddress().toString());
			}
			jjcMap = loginDao.selectJJCUserByUtoken(params);
			jsonObject.put("userInfo", jjcMap);
			jsonObject.put(Constant.RET, Constant.RET_JJC_PK_SUCCESS);
			jsonObject.put(Constant.MSG,
					ConfigFactory.getRetMsg(Constant.RET_JJC_PK_SUCCESS));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
