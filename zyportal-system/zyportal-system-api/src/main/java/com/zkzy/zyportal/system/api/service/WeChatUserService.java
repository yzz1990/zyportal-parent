package com.zkzy.zyportal.system.api.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.WeChatUser;

public interface WeChatUserService {
	
	// 获取用户openid列表
	public String[] getOpenids(String weChatId,String access_token);
	
	//根据openid单个获取用户基本信息列表
	public WeChatUser getUserInfoByOpenid(String openid,String weChatId, String access_token);
	
	//根据openid获取多个用户基本信息列表
	public List<WeChatUser> getUserInfosByOpenids(String[] openids,String weChatId, String access_token);
	
	//根据openid给用户设置备注名
	public CodeObject setRemarkByOpenid(String openid, String remark, String weChatId);

	public PageInfo selectAll(int currentPage, int pageSize, String param);

	//添加标签
	public CodeObject addTag(int tagid, String openid, String weChatId);

	//删除标签
	public CodeObject deleteTag(int tagid, String openid, String weChatId);

	//同步
	public CodeObject synchronize(String weChatId);

	public List<WeChatUser> selectUsersSubscribed(String param);
}
