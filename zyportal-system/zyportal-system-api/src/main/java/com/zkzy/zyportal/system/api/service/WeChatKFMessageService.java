package com.zkzy.zyportal.system.api.service;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.zyportal.system.api.constant.CodeObject;

/**
 * Created by Jisy on 2017/7/18.
 */
public interface WeChatKFMessageService {

    //上传多媒体文件获取id， 文件路径为系统路径
    public String getMedia_id(String filePath, String type, String weChatId)
            throws Exception;

    //上传图文消息获取图文消息id（用于群发图文消息）
    public String getMpNewsId(String picurl, String author, String title,
                              String desc, String content, String weChatId);

    //获取视频信息的media_id
    public String getVedioId(String filePath,String title,String description,String weChatId);

    //发送客服消息
    public CodeObject sendServiceMessage(String weChatId, String openid, String msgType, JSONObject message);
}
