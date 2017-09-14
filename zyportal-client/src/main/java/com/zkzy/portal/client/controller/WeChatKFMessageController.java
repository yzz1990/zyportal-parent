package com.zkzy.portal.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.service.WeChatKFMessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jisy on 2017/7/14.
 */

@Validated
@RestController
@RequestMapping("/WeChatPlatform/kfMessage")
@Api(tags = "微信公众号客服信息Controller")
public class WeChatKFMessageController extends BaseController {

    @Autowired
    private WeChatKFMessageService weChatKfMessageService;

    @PreAuthorize("hasAuthority('wx_kfMessage_getMedia_id')")
    @PostMapping(value = "/getMedia_id", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "上传多媒体文件获取id")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, String> getMedia_id(
            @ApiParam(required = true, value = "filePath") @RequestParam("filePath") String filePath,
            @ApiParam(required = true, value = "type") @RequestParam("type") String type,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,String> map = new HashMap<String,String>();
        String media_id= null;
        try {
            media_id = weChatKfMessageService.getMedia_id(filePath, type, weChatId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("media_id",media_id);
        return map;
    }

    @PreAuthorize("hasAuthority('wx_kfMessage_getMpNewsId')")
    @PostMapping(value = "/getMpNewsId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "上传图文消息获取图文消息id")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, String> getMpNewsId(
            @ApiParam(required = true, value = "filePath") @RequestParam("filePath") String filePath,
            @ApiParam(required = true, value = "author") @RequestParam("author") String author,
            @ApiParam(required = true, value = "title") @RequestParam("title") String title,
            @ApiParam(required = true, value = "desc") @RequestParam("desc") String desc,
            @ApiParam(required = true, value = "content") @RequestParam("content") String content,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,String> map = new HashMap<String,String>();
        String mpNewsId = weChatKfMessageService.getMpNewsId(filePath,author,title,desc,content,weChatId);
        map.put("mpNewsId",mpNewsId);
        return map;
    }

    @PreAuthorize("hasAuthority('wx_kfMessage_getVedioId')")
    @PostMapping(value = "/getVedioId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取视频信息的media_id")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, String> getVedioId(
            @ApiParam(required = true, value = "filePath") @RequestParam("filePath") String filePath,
            @ApiParam(required = true, value = "title") @RequestParam("title") String title,
            @ApiParam(required = true, value = "description") @RequestParam("description") String description,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,String> map = new HashMap<String,String>();
        String videoId = weChatKfMessageService.getVedioId(filePath, title, description, weChatId);
        map.put("videoId",videoId);
        return map;
    }

    @PreAuthorize("hasAuthority('wx_kfMessage_sendMessage')")
    @PostMapping(value = "/sendMessage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "发送客服消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> sendMessage(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "消息类型") @RequestParam("msgType") String msgType,
            @ApiParam(required = true, value = "消息体") @RequestParam("message") String message,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        JSONObject json = (JSONObject)JSON.parse(message);
        Map<String,Object> returnMessage=makeMessage(weChatKfMessageService.sendServiceMessage(weChatId,openid, msgType,json));
        return returnMessage;
    }

}
