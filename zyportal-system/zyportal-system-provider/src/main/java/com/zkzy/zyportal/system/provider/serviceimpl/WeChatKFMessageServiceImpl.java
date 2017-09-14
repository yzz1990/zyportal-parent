package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatKFMessageService;
import com.zkzy.zyportal.system.api.util.MessageUtil;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Jisy on 2017/7/18.
 */
@Service("weChatKfMessageService")
public class WeChatKFMessageServiceImpl implements WeChatKFMessageService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

    //上传多媒体文件获取id， 文件路径为系统路径
    @Override
    public String getMedia_id(String filePath, String type, String weChatId)
            throws Exception {
        //获取access_token
        String access_token = redisTemplate.opsForValue().get(weChatId);
        //为空自动获取并保存
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        JSONObject result = MessageUtil.getMedia_id(filePath,type,access_token);
        int errcode = result.getIntValue("errcode");
        //accexx_token失效时重新获取并保存
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            result = MessageUtil.getMedia_id(filePath,type,access_token);
        }
        return result.getString("media_id");
    }

    //上传图文消息获取图文消息id（用于群发图文消息）
    @Override
    public String getMpNewsId(String picurl, String author, String title,
                              String desc, String content, String weChatId){
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        //处理内容转义字符
        content = content.replace("&lt;","<").replace("&gt;",">").replace("&quot;","'").replace("&amp;","&");
        //图文消息content处理
        Set<String> pics = MessageUtil.getImgStr(content);
        try{
            for(String str : pics){
                String imgUrl = MessageUtil.getImgUrl(str,access_token);
                content = content.replace(str,imgUrl);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        JSONObject result = MessageUtil.getMpNewsId(picurl, author, title, desc, content, access_token);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            result = MessageUtil.getMpNewsId(picurl, author, title, desc, content, access_token);
        }
        return result.getString("media_id");
    }

    //获取视频信息的media_id
    public String getVedioId(String filePath,String title,String description,String weChatId){
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        JSONObject result = MessageUtil.getVedioId(filePath, title, description, access_token);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            result = MessageUtil.getVedioId(filePath, title, description, access_token);
        }
        return result.getString("media_id");
    }

    //发送客服消息
    @Override
    public CodeObject sendServiceMessage(String weChatId, String openid, String msgType, JSONObject message) {
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
        JSONObject json = new JSONObject();
        json.put("touser", openid);
        json.put("msgtype", msgType);
        json.put(msgType, message);
        JSONObject result = WeChatUtil.post(json, url);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
            errcode = WeChatUtil.post(json, url).getIntValue("errcode");
        }
        if (errcode == 0) {
            return ReturnCode.MESSAGE_SEND_SUCCESS;
        } else {
            return ReturnCode.MESSAGE_SEND_FAILED;
        }
    }
}
