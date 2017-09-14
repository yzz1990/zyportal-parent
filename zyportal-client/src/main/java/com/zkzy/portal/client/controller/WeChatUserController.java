package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.WeChatUser;
import com.zkzy.zyportal.system.api.service.WeChatUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jisy on 2017/7/14.
 */

@Validated
@RestController
@RequestMapping("/WeChatPlatform/userManage")
@Api(tags = "微信公众号用户Controller")
public class WeChatUserController extends BaseController {

    @Autowired
    private WeChatUserService weChatUserService;

    @PreAuthorize("hasAuthority('wx_selectusers')")
    @PostMapping(value = "/selectAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel selectAll(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @ModelAttribute WeChatUser weChatUser
    ) {
        String param="";
        if(weChatUser.getWechatid()!=null&&!"".equals(weChatUser.getWechatid().trim())){
            param = "and wechatid='"+ weChatUser.getWechatid()+"' ";
        }
        if(weChatUser.getOpenid()!=null&&!"".equals(weChatUser.getOpenid().trim())){
            param = "and openid='"+ weChatUser.getOpenid()+"' ";
        }
        PageInfo pageInfo = weChatUserService.selectAll(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), param);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    @PreAuthorize("hasAuthority('wx_selectusers_subscribed')")
    @PostMapping(value = "/selectUsersSubscribed", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,List<WeChatUser>> selectUsersSubscribed(
            @ModelAttribute WeChatUser weChatUser
    ) {
        String param="and subscribe=1 ";
        if(weChatUser.getWechatid()!=null&&!"".equals(weChatUser.getWechatid().trim())){
            param = "and wechatid='"+ weChatUser.getWechatid()+"' ";
        }
        List<WeChatUser> weChatUsers = weChatUserService.selectUsersSubscribed(param);
        Map<String,List<WeChatUser>> map = new HashMap<String,List<WeChatUser>>();
        map.put("users", weChatUsers);
        return map;
    }

    @PreAuthorize("hasAuthority('wx_addtag')")
    @PostMapping(value = "/addTag", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "添加标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addTag(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "标签id") @RequestParam("tagid") String tagid,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatUserService.addTag(Integer.parseInt(tagid), openid, weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_userManage_deletetag')")
    @PostMapping(value = "/deleteTag", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteTag(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "标签id") @RequestParam("tagid") String tagid,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatUserService.deleteTag(Integer.parseInt(tagid), openid, weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_remark')")
    @PostMapping(value = "/remark", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑备注")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> remark(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "备注") @RequestParam("remark") String remark,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatUserService.setRemarkByOpenid(openid, remark, weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_synchronizeuser')")
    @PostMapping(value = "/synchronize", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "同步用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> synchronize(
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatUserService.synchronize(weChatId));
        return message;
    }
}
