package com.zkzy.zyportal.system.api.service;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.constant.CodeObject;

/**
 * Created by Jisy on 2017/7/21.
 */
public interface WeChatMassMessageService {

    CodeObject sendMessageByTag(String weChatId, int tagid, String msgType, JSONObject message);

    //CodeObject sendMessageByOpenids(String weChatId, String openids, String msgType, JSONObject message, String access_token);

    CodeObject sendMessageToAll(String weChatId, String msgType, JSONObject message);
}
