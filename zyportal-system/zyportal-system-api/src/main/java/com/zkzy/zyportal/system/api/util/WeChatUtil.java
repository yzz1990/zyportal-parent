package com.zkzy.zyportal.system.api.util;

import com.zkzy.portal.common.utils.Http;
import com.zkzy.zyportal.system.api.entity.WeChatAccountDevelopInfo;
import com.zkzy.zyportal.system.api.entity.WeChatTag;
import com.zkzy.zyportal.system.api.entity.WeChatUser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;

public class WeChatUtil {

	private static Http http = new Http();

	// 获取access_token
	public static String getAccess_token(WeChatAccountDevelopInfoService weChatAccountDevelopInfoService,String weChatId) {
		WeChatAccountDevelopInfo weChatAccountDevelopInfo = weChatAccountDevelopInfoService.selectById(weChatId);
		String appid = weChatAccountDevelopInfo.getAppid();
		String appsecret = weChatAccountDevelopInfo.getAppsecret();
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appid + "&secret=" + appsecret;
		JSONObject json = get(url);
		return json.getString("access_token");
	}
	
	//用户基本信息json转换为user
	public static WeChatUser jsonToUser(JSONObject json){
		WeChatUser weChatUser = new WeChatUser();
		weChatUser.setSubscribe(json.getInteger("subscribe"));
		weChatUser.setOpenid(json.getString("openid"));
		weChatUser.setNickname(json.getString("nickname"));
		weChatUser.setSex(json.getInteger("sex"));
		weChatUser.setLanguage(json.getString("language"));
		weChatUser.setCity(json.getString("city"));
		weChatUser.setProvince(json.getString("province"));
		weChatUser.setCountry(json.getString("country"));
		weChatUser.setHeadimgurl(json.getString("headimgurl"));
		weChatUser.setSubscribeTime(json.getString("subscribe_time"));
		weChatUser.setUnionid(json.getString("unionid"));
		weChatUser.setRemark(json.getString("remark"));
		weChatUser.setGroupid(json.getString("groupid"));
		String list = json.getString("tagid_list");
		list = list.substring(1, list.length()-1);
		weChatUser.setTagidList(list);
		return weChatUser;
	}
	
	//标签信息json转换为tag
	public static WeChatTag jsonToTag(JSONObject json){
		WeChatTag weChatTag = new WeChatTag();
		weChatTag.setTagid(json.getIntValue("id"));
		weChatTag.setTagname(json.getString("name"));
		return weChatTag;
	}
	
	// 向指定接口（url）发送post请求，请求参数为json
	public static JSONObject post(JSONObject json, String url) {
		String result = http.postRes(url,json);
		return (JSONObject) JSON.parse(result);
	}
	
	//向指定接口（url）发送get请求
	public static JSONObject get(String url) {
		String result = http.getRes(url);
		return (JSONObject) JSON.parse(result);
	}

}
