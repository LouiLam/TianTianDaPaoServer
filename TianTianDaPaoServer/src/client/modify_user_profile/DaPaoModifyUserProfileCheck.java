package client.modify_user_profile;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.channel.Channel;
import org.json.JSONObject;

import server.ui.main.U;
import client.login.Check;
import config.ConfigFactory;
import config.Constant;
import database.DatabaseConnector;

public class DaPaoModifyUserProfileCheck extends Check {
	public DaPaoModifyUserProfileCheck() {
		super();
	}

	@Override
	public JSONObject check(Map<String, String> params, Channel channel) {

		JSONObject jsonObject = new JSONObject();

		SqlSession sqlSession = DatabaseConnector.getInstance().getSqlSession();

		try {
			DaPaoModifyUserProfileDao loginDao = (DaPaoModifyUserProfileDao) sqlSession.getMapper(ConfigFactory
					.getClazz("12"));
			Map<Object,Object> selectMap = loginDao.selectModifyByUtoken(params);

			if (selectMap == null) {
				jsonObject.put(Constant.RET, Constant.RET_MODIFY_USER_PROFILE_FAILED);
				jsonObject
						.put(Constant.MSG, ConfigFactory
								.getRetMsg(Constant.RET_MODIFY_USER_PROFILE_FAILED));
				U.infoQueue("用户修改资料请求失败：utoken非法或不存在 "
						+ channel.getRemoteAddress().toString());
				return jsonObject;
			}
			if(params.get("id")==null)
			{
				if(params.get("cur_role")==null||params.get("cur_pet")==null||params.get("cur_airship")==null)
				{
				jsonObject.put(Constant.RET, Constant.RET_MODIFY_USER_PROFILE_FAILED_MISS_ARG);
				jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_MODIFY_USER_PROFILE_FAILED_MISS_ARG));
				U.infoQueue("用户修改资料修改请求失败：参数要求非法，缺少参数"+channel.getRemoteAddress().toString());
				return jsonObject;
				}
				else  //修改当前角色信息等
				{
					long role_value=(long) selectMap.get("prop"+(Integer.parseInt(params.get("cur_role"))-1));
					params.put("uid", selectMap.get("uid")+"");
					params.put("cur_role_level", role_value+"");
					loginDao.updateByUserGame(params);
					jsonObject.put("userInfo", selectMap);
					U.infoQueue("用户修改资料请求成功"+channel.getRemoteAddress().toString());
					jsonObject.put(Constant.RET, Constant.RET_MODIFY_USER_PROFILE_SUCCESS);
					jsonObject.put(Constant.MSG,
							ConfigFactory.getRetMsg(Constant.RET_MODIFY_USER_PROFILE_SUCCESS));
				}
			}
			else  //修改帐号信息
			{
				Map<Object,Object> idMap = loginDao.selectIDByUserInfo(params);
				System.out.println("用户修改昵称："+params.get("id"));
				if(idMap!=null)
				{
					//此昵称已存在，请换一个
					jsonObject.put(Constant.RET, Constant.RET_MODIFY_USER_PROFILE_FAILED_ID_HAVE_EXIST);
					jsonObject.put(Constant.MSG, ConfigFactory.getRetMsg(Constant.RET_MODIFY_USER_PROFILE_FAILED_ID_HAVE_EXIST));
					U.infoQueue("用户修改资料请求失败：此昵称已存在，请换一个"+channel.getRemoteAddress().toString());
					return jsonObject;
				}
				selectMap.put("id", params.get("id"));
				loginDao.updateIDByUserInfo(selectMap);
				jsonObject.put("userInfo", selectMap);
				U.infoQueue("用户修改资料请求成功"+channel.getRemoteAddress().toString());
				jsonObject.put(Constant.RET, Constant.RET_MODIFY_USER_PROFILE_SUCCESS);
				jsonObject.put(Constant.MSG,
						ConfigFactory.getRetMsg(Constant.RET_MODIFY_USER_PROFILE_SUCCESS));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		return jsonObject;

	}
}
