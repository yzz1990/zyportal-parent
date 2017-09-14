package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.service.WeChatTemplateMessageService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Jisy on 2017/8/28.
 */

@Validated
@RestController
@RequestMapping("/WeChatPlatform/templateMessage")
@Api(tags = "微信公众号模板信息Controller")
public class WeChatTemplateMessageController extends BaseController{

    @Autowired
    private WeChatTemplateMessageService weChatTemplateMessageService;

    @PreAuthorize("hasAuthority('wx_templateMessage_sendMessage')")
    @PostMapping(value = "/sendMessage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "发送模板消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> sendMessage(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "模板id") @RequestParam("templateId") String templateId,
            @ApiParam(required = true, value = "模板数据") @RequestParam("data") String data,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> returnMessage=makeMessage(weChatTemplateMessageService.sendMessage(weChatId,openid,templateId,data));
        return returnMessage;
    }
}
