package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatMassMessageService;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Jisy on 2017/7/21.
 */
@Service("weChatMassMessageService")
public class WeChatMassMessageServiceImpl implements WeChatMassMessageService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

    @Override
    public CodeObject sendMessageByTag(String weChatId, int tagid, String msgType, JSONObject message) {
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+ access_token;
        JSONObject filter = new JSONObject();
        JSONObject json = new JSONObject();
        filter.put("is_to_all", false);
        filter.put("tag_id", tagid);
        json.put("filter", filter);
        json.put(msgType, message);
        json.put("msgtype", msgType);
        if("mpnews".equals(msgType)){
            // 判定为转载是是否继续群发 1为继续群发 0停止群发
            json.put("send_ignore_reprint", 1);
        }
        JSONObject result = WeChatUtil.post(json, url);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+ access_token;
            errcode = WeChatUtil.post(json, url).getIntValue("errcode");
        }
        if (errcode==0) {
            return ReturnCode.MESSAGE_SEND_SUCCESS;
        } else {
            return ReturnCode.MESSAGE_SEND_FAILED;
        }
    }

    /*@Override
    public CodeObject sendMessageByOpenids(String weChatId, String openids, String msgType, JSONObject message, String access_token) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
                + access_token;
        JSONObject json = new JSONObject();
        openids = openids.substring(1,openids.length()-1);
        String[] arr = openids.split(",");
        json.put("touser", arr);
        json.put(msgType, message);
        json.put("msgtype", msgType);
        if("mpnews".equals(msgType)){
            // 判定为转载是是否继续群发 1为继续群发 0停止群发
            json.put("send_ignore_reprint", 1);
        }
        JSONObject result = WeChatUtil.post(json, url);
        int errcode = result.getIntValue("errcode");
        if (errcode==0) {
            return ReturnCode.MESSAGE_SEND_SUCCESS;
        } else {
            return ReturnCode.MESSAGE_SEND_FAILED;
        }
    }*/

    @Override
    public CodeObject sendMessageToAll(String weChatId, String msgType, JSONObject message) {
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
                + access_token;
        JSONObject filter = new JSONObject();
        JSONObject json = new JSONObject();
        filter.put("is_to_all", true);
        json.put("filter", filter);
        json.put(msgType, message);
        json.put("msgtype", msgType);
        if("mpnews".equals(msgType)){
            // 判定为转载是是否继续群发 1为继续群发 0停止群发
            json.put("send_ignore_reprint", 1);
        }
        JSONObject result = WeChatUtil.post(json, url);
        int errcode = result.getIntValue("errcode");
        if(errcode == 40014 || errcode == 40001 || errcode == 42001) {
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId, access_token);
            url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="
                    + access_token;
            errcode = WeChatUtil.post(json, url).getIntValue("errcode");
        }
        if (errcode==0) {
            return ReturnCode.MESSAGE_SEND_SUCCESS;
        } else if(errcode==45028){
            return ReturnCode.MESSAGE_SEND_NOQUOTA;
        } else {
            return ReturnCode.MESSAGE_SEND_FAILED;
        }
    }
}
