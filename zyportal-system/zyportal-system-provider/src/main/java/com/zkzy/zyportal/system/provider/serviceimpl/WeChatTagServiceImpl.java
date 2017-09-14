package com.zkzy.zyportal.system.provider.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.WeChatTag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatTagService;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import com.zkzy.zyportal.system.provider.mapper.WeChatTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.zkzy.portal.common.utils.RandomHelper.uuid;

@Service("weChatTagService")
public class WeChatTagServiceImpl implements WeChatTagService {

	@Autowired
	private WeChatTagMapper weChatTagMapper;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

	//新增标签
	@Override
	public CodeObject addTag(String name, String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token="+access_token;
		JSONObject tag = new JSONObject();
		JSONObject json = new JSONObject();
		tag.put("name", name);
		json.put("tag", tag);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token="+access_token;
			result = WeChatUtil.post(json, url);
			errcode = result.getIntValue("errcode");
		}
		if(result.getJSONObject("tag")!=null){
			WeChatTag t = new WeChatTag();
			t.setId(uuid());
			t.setTagid(result.getJSONObject("tag").getIntValue("id"));
			t.setTagname(result.getJSONObject("tag").getString("name"));
			t.setWechatid(weChatId);
			weChatTagMapper.insert(t);
			return ReturnCode.TAG_ADD_SUCCESS;
		}else if(errcode==-1){
			return ReturnCode.SYSTEM_BUSY;
		}else if(errcode==45157){
			return ReturnCode.TAG_REPEAT;
		}else if(errcode==45158){
			return ReturnCode.TAG_NAME_OVERFLOW;
		}else if(errcode==45056){
			return ReturnCode.TAG_ADDFAILED_NUM_OVERFLOW;
		}else{
			return ReturnCode.TAG_ADD_FAILED;
		}
	}
	
	//同步标签
	@Override
	public CodeObject synchronize(String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		if(weChatId!=null&&weChatId.trim()!=""){
			String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="+access_token;
			JSONObject result = WeChatUtil.get(url);
			int errcode = result.getIntValue("errcode");
			if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
				access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
				redisTemplate.opsForValue().set(weChatId, access_token);
				url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="+access_token;
				result = WeChatUtil.get(url);
				errcode = result.getIntValue("errcode");
			}
			if(errcode == 0){
				JSONArray jsonarr = result.getJSONArray("tags");
				weChatTagMapper.deleteByWeChatId(weChatId);
				for(Object obj:jsonarr){
					WeChatTag weChatTag = WeChatUtil.jsonToTag((JSONObject)obj);
					weChatTag.setId(uuid());
					weChatTag.setWechatid(weChatId);
					weChatTagMapper.insert(weChatTag);
				}
			/*List<Tag> list = tagMapper.selectAll("and wechatid='"+weChatId+"' ");
			for(int i = 0;i<jsonarr.size();i++){
				Tag tag = WeChatUtil.jsonToTag((JSONObject)jsonarr.get(i));
				if(!list.contains(tag)){
					tag.setId(uuid());
					tag.setWechatid(weChatId);
					tagMapper.insert(tag);
				}
				list.add(tag);
			}
			if(list.size()>jsonarr.size()){
				for(Tag tag:list){
					if(!jsonarr.contains(tag)){
						tagMapper.deleteByTag(tag);
					}
				}
			}*/
				return ReturnCode.TAG_SYNCHRONIZE_SUCCESS;
			}else{
				return ReturnCode.TAG_SYNCHRONIZE_FAILED;
			}
		}else{
			return ReturnCode.TAG_SYNCHRONIZE_FAILED;
		}

	}
	
	//根据标签id修改标签
	@Override
	public CodeObject updateTag(int tagId,String name, String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token="+access_token;
		JSONObject json = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", tagId);
		tag.put("name", name);
		json.put("tag", tag);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token="+access_token;
			errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		}
		if(errcode==0){
			WeChatTag t = new WeChatTag();
			t.setTagid(tagId);
			t.setTagname(name);
			t.setWechatid(weChatId);
			weChatTagMapper.updateByTag(t);
			return ReturnCode.TAG_UPDATE_SUCCESS;
		}else if(errcode==-1){
			return ReturnCode.SYSTEM_BUSY;
		}else if(errcode==45157){
			return ReturnCode.TAG_REPEAT;
		}else if(errcode==45158){
			return ReturnCode.TAG_NAME_OVERFLOW;
		}else if(errcode==45058){
			return ReturnCode.TAG_NOCHANGE_DEFAULT;
		}else{
			return ReturnCode.TAG_UPDATE_FAILED;
		}
	}
	
	//根据标签id删除标签
	@Override
	public CodeObject deleteTag(int tagid, String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token="+access_token;
		JSONObject json = new JSONObject();
		JSONObject tag = new JSONObject();
		tag.put("id", tagid);
		json.put("tag", tag);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token="+access_token;
			errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		}
		if(errcode==0){
			WeChatTag t = new WeChatTag();
			t.setTagid(tagid);
			t.setWechatid(weChatId);
			weChatTagMapper.deleteByTag(t);
			return ReturnCode.TAG_DELETE_SUCCESS;
		}else if(errcode==-1){
			return ReturnCode.SYSTEM_BUSY;
		}else if(errcode==45058){
			return ReturnCode.TAG_NOCHANGE_DEFAULT;
		}else if(errcode==45057){
			return ReturnCode.TAG_DELETE_FAILED_DIRECT;
		}else{
			return ReturnCode.TAG_DELETE_FAILED;
		}
	}

	//根据标签id查询标签信息
	@Override
	public WeChatTag selectByTagid(int tagid){
		return weChatTagMapper.selectByTagid(tagid);
	}

	@Override
	public PageInfo selectAll(int currentPage, int pageSize, String param){
		PageHelper.startPage(currentPage,pageSize);
		List<WeChatTag> list = weChatTagMapper.selectAll(param);
		PageInfo pageInfo=new PageInfo(list);
		return pageInfo;
	}

	/*//获取标签下用户openid列表
	@Override
	public String[] getOpenidsByTag(int tagId, String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token="+access_token;
		JSONObject json = new JSONObject();
		json.put("tagid", tagId);
		JSONObject result = WeChatUtil.post(json, url);
		//System.out.println(result);
		String openid = result.getJSONObject("data").getString("openid");
		//System.out.println(openid);
		openid = openid.substring(2, openid.length()-2);
		String[] openids = openid.split("\",\"");
		return openids;
	}
	
	//为多个用户添加标签
	@Override
	public boolean addTagToUsers(int tagId, String[] openids, String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token="+access_token;
		JSONObject json = new JSONObject();
		json.put("openid_list", openids);
		json.put("tagid", tagId);
		int errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		if(errcode==0){
			return true;
		}else{
			return false;
		}
	}
	
	//为多个用户取消标签
	@Override
	public boolean deleteTagFromUsers(int tagId, String[] openids,
			String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token="+access_token;
		JSONObject json = new JSONObject();
		String[] arr = new String[openids.length];
		for(int i = 0;i<openids.length;i++){
			arr[i] = openids[i];
		}
		json.put("openid_list", arr);
		json.put("tagid", tagId);
		int errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		if(errcode==0){
			return true;
		}else{
			return false;
		}
	}*/
	
	//获取用户标签id列表
	@Override
	public List<WeChatTag> queryTagsByOpenid(String openid,String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token="+access_token;
		JSONObject json = new JSONObject();
		json.put("openid", openid);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token="+access_token;
			result = WeChatUtil.post(json, url);
		}
		String tagid_list = result.getString("tagid_list");
		tagid_list = tagid_list.substring(1, tagid_list.length()-1);
		String[] strtags = tagid_list.split(",");
		List<WeChatTag> weChatTags = new ArrayList<WeChatTag>();
		for(int i = 0;i<strtags.length;i++){
			weChatTags.add(selectByTagid(Integer.parseInt(strtags[i])));
		}
		return weChatTags;
	}

}
