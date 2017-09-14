package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.WeChatMessage;
import com.zkzy.zyportal.system.api.service.WeChatMessageService;
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
@RequestMapping("/WeChatPlatform/message")
@Api(tags = "微信公众号小消息Controller")
public class WeChatMessageController extends BaseController {

    @Autowired
    private WeChatMessageService weChatMessageService;

    @PreAuthorize("hasAuthority('wx_message_queryMessage')")
    @PostMapping(value = "/queryMessage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询历史消息记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel selectAll(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @ModelAttribute WeChatMessage weChatMessage
    ) {
        String param="";
        if(weChatMessage.getWechatid()!=null&&!"".equals(weChatMessage.getWechatid().trim())){
            param += "and wechatid='"+ weChatMessage.getWechatid()+"' ";
        }
        if(!"".equals(weChatMessage.getType()+"")&& weChatMessage.getType()!=-1){
            param += "and type="+ weChatMessage.getType()+" ";
        }
        if(weChatMessage.getMsgtype()!=null&&!"".equals(weChatMessage.getMsgtype()+"")){
            param += "and msgtype='"+ weChatMessage.getMsgtype()+"' ";
        }
        PageInfo pageInfo = weChatMessageService.queryMessage(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), param);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }


    @PreAuthorize("hasAuthority('wx_message_addMessage')")
    @PostMapping(value = "/addMessage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "添加消息记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addMessage(
            @ModelAttribute WeChatMessage weChatMessage
    ) {
        Map<String,Object> returnMessage=makeMessage(weChatMessageService.addMessage(weChatMessage));
        return returnMessage;
    }

    @PreAuthorize("hasAuthority('wx_message_delMessage')")
    @PostMapping(value = "/delMessage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除消息记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delMessage(
            @ModelAttribute WeChatMessage weChatMessage
    ) {
        Map<String,Object> returnMessage=makeMessage(weChatMessageService.delMessage(weChatMessage));
        return returnMessage;
    }

}
