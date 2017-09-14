package com.zkzy.portal.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.service.WeChatMassMessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Jisy on 2017/7/14.
 */

@Validated
@RestController
@RequestMapping("/WeChatPlatform/massMessage")
@Api(tags = "微信公众号客服信息Controller")
public class WeChatMassMessageController extends BaseController {

    @Autowired
    private WeChatMassMessageService weChatMassMessageService;

    @PreAuthorize("hasAuthority('wx_massMessage_sendMessageByTag')")
    @PostMapping(value = "/sendMessageByTag", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据标签群发消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> sendMessageByTag(
            @ApiParam(required = true, value = "tagid") @RequestParam("tagid") int tagid,
            @ApiParam(required = true, value = "消息类型") @RequestParam("msgType") String msgType,
            @ApiParam(required = true, value = "消息体") @RequestParam("message") String message,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        JSONObject json = (JSONObject)JSON.parse(message);
        Map<String,Object> returnMessage=makeMessage(weChatMassMessageService.sendMessageByTag(weChatId, tagid, msgType, json));
        return returnMessage;
    }
/*
    @PreAuthorize("hasAuthority('wx_massMessage_sendMessageByOpenids')")
    @PostMapping(value = "/sendMessageByOpenids", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据用户群发消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> sendMessageByOpenids(
            @ApiParam(required = true, value = "openids") @RequestParam("openids") String openids,
            @ApiParam(required = true, value = "消息类型") @RequestParam("msgType") String msgType,
            @ApiParam(required = true, value = "消息体") @RequestParam("message") String message,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }
        JSONObject json = (JSONObject)JSON.parse(message);
        Map<String,Object> returnMessage=makeMessage(massMessageService.sendMessageByOpenids(weChatId, openids, msgType, json, access_token));
        return returnMessage;
    }*/

    @PreAuthorize("hasAuthority('wx_massMessage_sendMessageToAll')")
    @PostMapping(value = "/sendMessageToAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "给所有用户群发消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> sendMessageToAll(
            @ApiParam(required = true, value = "消息类型") @RequestParam("msgType") String msgType,
            @ApiParam(required = true, value = "消息体") @RequestParam("message") String message,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        JSONObject json = (JSONObject)JSON.parse(message);
        Map<String,Object> returnMessage=makeMessage(weChatMassMessageService.sendMessageToAll(weChatId, msgType, json));
        return returnMessage;
    }
}
