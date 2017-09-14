package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.service.WeChatTagService;
import com.zkzy.zyportal.system.api.entity.WeChatTag;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Jisy on 2017/7/14.
 */

@Validated
@RestController
@RequestMapping("/WeChatPlatform/tagManage")
@Api(tags = "微信公众号标签Controller")
public class WeChatTagController extends BaseController {

    @Autowired
    private WeChatTagService weChatTagService;


    @PreAuthorize("hasAuthority('wx_tagManage_addtag')")
    @PostMapping(value = "/add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> add(
            @ApiParam(required = true, value = "标签名称") @RequestParam("tagName") String tagName,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage( weChatTagService.addTag(tagName,weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_tagManage_deletetag')")
    @PostMapping(value = "/delete", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delete(
            @ApiParam(required = true, value = "标签id") @RequestParam("tagid") String tagid,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatTagService.deleteTag(Integer.parseInt(tagid),weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_tagManage_updatetag')")
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> update(
            @ApiParam(required = true, value = "标签id") @RequestParam("tagid") String tagid,
            @ApiParam(required = true, value = "标签名称") @RequestParam("tagName") String tagName,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatTagService.updateTag(Integer.parseInt(tagid), tagName, weChatId));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_tagManage_selectByTagid')")
    @PostMapping(value = "/selectByTagid", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询标签信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public WeChatTag selectByTagid(
            @ApiParam(required = true, value = "标签id") @RequestParam("tagid") int tagid
    ){
        return weChatTagService.selectByTagid(tagid);
    }

    @PreAuthorize("hasAuthority('wx_tagManage_selectTagsByUser')")
    @PostMapping(value = "/selectTagsByUser", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询用户标签信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public List<WeChatTag> selectTagsByUser(
            @ApiParam(required = true, value = "openid") @RequestParam("openid") String openid,
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ){

        return weChatTagService.queryTagsByOpenid(openid,weChatId);
    }

    @PreAuthorize("hasAuthority('wx_tagManage_selectAll')")
    @PostMapping(value = "/selectAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel selectAll(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @ModelAttribute WeChatTag weChatTag
    ) {
        String param="";
        if(weChatTag.getWechatid()!=null&&!"".equals(weChatTag.getWechatid().trim())){
            param = "and wechatid='"+ weChatTag.getWechatid()+"' ";
        }if(weChatTag.getTagname()!=null&&!"".equals(weChatTag.getTagname().trim())){
            param += "and tagname like '%"+ weChatTag.getTagname()+"%' ";
        }
        PageInfo pageInfo = weChatTagService.selectAll(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), param);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    @PreAuthorize("hasAuthority('wx_tagManage_synchronize')")
    @PostMapping(value = "/synchronize", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "同步标签")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> synchronize(
            @ApiParam(required = true, value = "公众号id") @RequestParam("weChatId") String weChatId
    ) {
        Map<String,Object> message=makeMessage(weChatTagService.synchronize(weChatId));
        return message;
    }




}
