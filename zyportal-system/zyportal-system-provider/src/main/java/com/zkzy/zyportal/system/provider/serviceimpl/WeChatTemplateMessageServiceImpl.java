package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatTemplateMessageService;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Jisy on 2017/8/28.
 */

@Service("weChatTemplateMessageService")
public class WeChatTemplateMessageServiceImpl implements WeChatTemplateMessageService{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

    @Override
    public CodeObject sendMessage(String weChatId, String openid, String templateId, String data) {
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        data = data.replace("&lt;","<").replace("&gt;",">").replace("&quot;","'").replace("&amp;","&");
        JSONObject d = (JSONObject) JSON.parse(data);
        JSONObject json = new JSONObject();
        json.put("touser", openid);
        json.put("template_id","Yl0wiwDaKZ0vcv4CAD3oJ9DVv-TJ9lFiYfV42fuPn4I");
        json.put("data", d);
        JSONObject result = WeChatUtil.post(json,url);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            if(access_token==null){
                return ReturnCode.DATA_ERROR;
            }else{
                redisTemplate.opsForValue().set(weChatId, access_token);
                url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + access_token;
                errcode = WeChatUtil.post(json, url).getIntValue("errcode");
            }
        }
        if (errcode == 0) {
            return ReturnCode.MESSAGE_SEND_SUCCESS;
        } else {
            return ReturnCode.MESSAGE_SEND_FAILED;
        }
    }
}
