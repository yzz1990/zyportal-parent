package com.zkzy.zyportal.system.api.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.WeChatTag;

public interface WeChatTagService {
	
	//新增标签
	public CodeObject addTag(String name, String weChatId);
	
	//获取已有标签
	public CodeObject synchronize(String weChatId);
	
	//根据标签id修改标签
	public CodeObject updateTag(int tagId, String name, String id);
	
	//根据标签id删除标签
	public CodeObject deleteTag(int tagId, String weChatId);

	WeChatTag selectByTagid(int tagid);

	public PageInfo selectAll(int currentPage, int pageSize, String param);

	/*//获取标签下用户openid列表
	public String[] getOpenidsByTag(int tagId, String access_token);
	
	//为多个用户添加标签
	public boolean addTagToUsers(int tagId, String[] openids, String access_token);
	
	//为多个用户取消标签
	public boolean deleteTagFromUsers(int tagId, String[] openids, String access_token);*/
	
	//获取用户标签列表
	public List<WeChatTag> queryTagsByOpenid(String openid,String weChatId);
}
