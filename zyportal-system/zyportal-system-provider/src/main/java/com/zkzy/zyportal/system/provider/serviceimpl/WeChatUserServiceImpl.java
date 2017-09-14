package com.zkzy.zyportal.system.provider.serviceimpl;

import java.util.ArrayList;
import java.util.List;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.WeChatTag;
import com.zkzy.zyportal.system.api.entity.WeChatUser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatTagService;
import com.zkzy.zyportal.system.api.service.WeChatUserService;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import com.zkzy.zyportal.system.provider.mapper.WeChatUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.zkzy.portal.common.utils.RandomHelper.uuid;

@Service("weChatUserService")
public class WeChatUserServiceImpl implements WeChatUserService{

	@Autowired
	private WeChatUserMapper weChatUserMapper;
	@Autowired
	private WeChatTagService weChatTagService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

	//获取用户openid列表
	@Override
	public String[] getOpenids(String weChatId,String access_token){
		String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+access_token+"&next_openid=";
		JSONObject json = WeChatUtil.get(url);
		int errcode = json.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			return getOpenids(weChatId, access_token);
		}else {
			String result = json.getJSONObject("data").getString("openid");
			result = result.substring(2, result.length()-2);
			String[] openids = result.split("\",\"");
			return openids;
		}
	}

	//根据openid单个获取用户基本信息列表
	@Override
	public WeChatUser getUserInfoByOpenid(String openid,String weChatId, String access_token) {
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid;
		JSONObject json = WeChatUtil.get(url);
		int errcode = json.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			return getUserInfoByOpenid(openid, weChatId, access_token);
		}else{
			return WeChatUtil.jsonToUser(json);
		}
	}
	
	//方法实现需进一步商讨（用户基本信息提前存入数据库，从数据库读取）
	//根据openid获取多个用户基本信息列表（接口最多一次性获取100条信息）
	@Override
	public List<WeChatUser> getUserInfosByOpenids(String[] openids,String weChatId, String access_token){
		JSONObject json = new JSONObject();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+access_token;
		JSONObject[] arr = new JSONObject[openids.length];
		for (int i=0;i<openids.length;i++) {
			JSONObject jsonobj = new JSONObject();
			jsonobj.put("openid", openids[i]);
			arr[i] = jsonobj;
		}
		json.put("user_list", arr);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+access_token;
			result = WeChatUtil.post(json, url);
		}
		JSONArray users = result.getJSONArray("user_info_list");

		List<WeChatUser> list = new ArrayList<>();
		for(int i = 0;i<users.size();i++){
			WeChatUser weChatUser = WeChatUtil.jsonToUser((JSONObject) users.get(i));
			list.add(weChatUser);
		}
		return list;
	}
	
	//根据openid给用户设置备注名
	@Override
	public CodeObject setRemarkByOpenid(String openid, String remark, String weChatId) {
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token="+access_token;
		JSONObject json = new JSONObject();
		json.put("openid", openid);
		json.put("remark", remark);
		JSONObject result = WeChatUtil.post(json, url);
		int errcode = result.getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token="+access_token;
			errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		}
		if(errcode==0){
			WeChatUser weChatUser = new WeChatUser();
			weChatUser.setRemark(remark);
			weChatUser.setOpenid(openid);
			weChatUserMapper.updateRemarkByOpenid(weChatUser);
			return ReturnCode.User_UPDATEREMARK_SUCCESS;
		}else{
			return ReturnCode.User_UPDATEREMARK_FAILED;
		}
	}

	@Override
	public PageInfo selectAll(int currentPage, int pageSize, String param){
		PageHelper.startPage(currentPage,pageSize);
		List<WeChatUser> list = weChatUserMapper.selectAll(param);
		for(WeChatUser weChatUser :list){
			String tagidlist = weChatUser.getTagidList();
			if(tagidlist!=null){
				String[] arr = tagidlist.split(",");
				String taglist = "";
				for(String s:arr){
					int i = Integer.parseInt(s);
					WeChatTag weChatTag = weChatTagService.selectByTagid(i);
					taglist += weChatTag.getTagname()+",";
				}
				taglist = taglist.substring(0,taglist.length()-1);
				weChatUser.setTagidList(taglist);
			}

		}
		PageInfo pageInfo=new PageInfo(list);
		return pageInfo;
	}

	@Override
	public CodeObject addTag(int tagid, String openid, String weChatId){
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token="+access_token;
		JSONObject json = new JSONObject();
		String[] openids = {openid};
		json.put("openid_list", openids);
		json.put("tagid", tagid);
		int errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token="+access_token;
			errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		}
		if(errcode==0){
			WeChatUser weChatUser = getUserInfoByOpenid(openid,weChatId,access_token);
			weChatUserMapper.updateByOpenid(weChatUser);
			return ReturnCode.User_UPDATETAG_SUCCCESS;
		}else if(errcode==-1){
			return ReturnCode.SYSTEM_BUSY;
		}else if(errcode==45159){
			return ReturnCode.User_UPDATETAG_ILLEGALTAG;
		}else if(errcode==45059){
			return ReturnCode.User_UPDATETAG_TAGNUMMAX;
		}else if(errcode==40003){
			return ReturnCode.User_UPDATETAG_ILLEGALUSER;
		}else{
			return ReturnCode.User_UPDATETAG_FAILED;
		}
	}

	@Override
	public CodeObject deleteTag(int tagid, String openid, String weChatId){
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		String url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token="+access_token;
		JSONObject json = new JSONObject();
		String[] openids = {openid};
		json.put("openid_list", openids);
		json.put("tagid", tagid);
		int errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId, access_token);
			url = "https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token="+access_token;
			errcode = WeChatUtil.post(json, url).getIntValue("errcode");
		}
		if(errcode==0){
			WeChatUser weChatUser = getUserInfoByOpenid(openid,weChatId,access_token);
			weChatUserMapper.updateByOpenid(weChatUser);
			return ReturnCode.User_DELETETAG_SUCCESS;
		}else if(errcode==-1){
			return ReturnCode.SYSTEM_BUSY;
		}else if(errcode==45159){
			return ReturnCode.User_UPDATETAG_ILLEGALTAG;
		}else if(errcode==40003||errcode==49003){
			return ReturnCode.User_UPDATETAG_ILLEGALUSER;
		}else{
			return ReturnCode.User_DELETETAG_FAILED;
		}
	}

	@Override
	public CodeObject synchronize(String weChatId){
		String access_token = redisTemplate.opsForValue().get(weChatId);
		if(access_token == null){
			access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
			redisTemplate.opsForValue().set(weChatId,access_token);
		}
		if(weChatId!=null&&weChatId.trim()!=""){
			String[] openids = getOpenids(weChatId,access_token);
			List<WeChatUser> weChatUsers = new ArrayList<WeChatUser>();
			//userMapper.deleteByWeChatId(weChatId);
			for(String openid: openids){
				WeChatUser weChatUser = getUserInfoByOpenid(openid,weChatId, access_token);
				//user.setId(uuid());
				//user.setWechatid(weChatId);
				weChatUsers.add(weChatUser);
				//userMapper.insert(user);
			}

			//将所有用户置为未关注
			weChatUserMapper.updateSubscribe(0);

			//添加新增的用户
			for(int i = 0; i< weChatUsers.size(); i++){
				WeChatUser weChatUser = weChatUsers.get(i);
				if(weChatUserMapper.selectByOpenid(weChatUser.getOpenid())==null){
					weChatUser.setId(uuid());
					weChatUser.setWechatid(weChatId);
					weChatUserMapper.insert(weChatUser);
				}else{
					weChatUserMapper.updateByOpenid(weChatUser);
				}
			}
			return ReturnCode.User_SYNCHRONIZE_SUCCESS;
		}else{
			return ReturnCode.User_SYNCHRONIZE_FAILED;
		}
	}

	@Override
	public List<WeChatUser> selectUsersSubscribed(String param){
		List<WeChatUser> list = weChatUserMapper.selectUsersSubscribed(param);
		return list;
	}
}
