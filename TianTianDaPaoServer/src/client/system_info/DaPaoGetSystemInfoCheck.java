package client.system_info;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import config.GetSystemInfoConfigMgr;
import database.DatabaseConnector;

public class DaPaoGetSystemInfoCheck extends Check {

	public DaPaoGetSystemInfoCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {
		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();
		try {
			DaPaoGetSystemInfoRewardDao loginDao = (DaPaoGetSystemInfoRewardDao) sqlSession
					.getMapper(ConfigFactory.getClazz("18"));
			Map<Object, Object> selectMap = loginDao
					.selectSystemInfoByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET,
						Constant.RET_GET_SYSTEM_INFO_FAILED);
				jsonObject.put(Constant.MSG, ConfigFactory
						.getRetMsg(Constant.RET_GET_SYSTEM_INFO_FAILED));
				U.infoQueue("获取系统信息请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			String id = (String) selectMap.get("id");

			U.infoQueue("id:" + id + "获取系统信息请求成功"
					+ channel.getRemoteAddress().toString());
			jsonObject.put("result", selectMap);

			jsonObject.put("systemInfo",
					GetSystemInfoConfigMgr.getInstance().getSystemInfoList);
			jsonObject.put(Constant.RET,
					Constant.RET_GET_SYSTEM_INFO_SUCCESS);
			jsonObject
					.put(Constant.MSG,
							ConfigFactory
									.getRetMsg(Constant.RET_GET_SYSTEM_INFO_SUCCESS));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
