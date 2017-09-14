package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.WeChatAccountDevelopInfo;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Created by Jisy on 2017/7/12.
 */
@Validated
@RestController
@RequestMapping("/WeChatPlatform/AccountDevelopInfo")
@Api(tags = "微信公众号Controller")
public class WeChatAccountDevelopInfoController extends BaseController {

    @Autowired
    private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

    @PreAuthorize("hasAuthority('wx_addgzh')")
    @PostMapping(value = "/add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> add(
            @ModelAttribute WeChatAccountDevelopInfo weChatAccountDevelopInfo
    ) {
        Map<String,Object> message=makeMessage(weChatAccountDevelopInfoService.add(weChatAccountDevelopInfo));
        return message;
    }


    @PreAuthorize("hasAuthority('wx_delgzh')")
    @PostMapping(value = "/delete", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public Map<String,Object> deleteById(
            @ApiParam(required = true, value = "微信公众号编号") @RequestParam("id") String id
    ){
        Map<String,Object> message=makeMessage(weChatAccountDevelopInfoService.DeleteById(id));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_updategzh')")
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public Map<String,Object> update(
            @ModelAttribute WeChatAccountDevelopInfo weChatAccountDevelopInfo
    ){
        Map<String,Object> message=makeMessage(weChatAccountDevelopInfoService.UpdateById(weChatAccountDevelopInfo));
        return message;
    }

    @PreAuthorize("hasAuthority('wx_selectgzhbyid')")
    @PostMapping(value = "/selectById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public WeChatAccountDevelopInfo selectById(
            @ApiParam(required = true, value = "微信公众号编号") @RequestParam("id") String id
    ){
        return weChatAccountDevelopInfoService.selectById(id);
    }

    @PreAuthorize("hasAuthority('wx_selectgzhbytypecode')")
    @PostMapping(value = "/selectByTypecode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public GridModel selectByTypecode(
            @ApiParam(required = true, value = "微信公众号类型") @RequestParam("typecode") String typecode,
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize
    ){
        PageInfo pageInfo = weChatAccountDevelopInfoService.selectByTypecode(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), typecode);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    @PreAuthorize("hasAuthority('wx_selectgzh')")
    @PostMapping(value = "/selectAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询公众号信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )public GridModel selectAll(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @ModelAttribute WeChatAccountDevelopInfo weChatAccountDevelopInfo
    ){
        String param = "";
        if(weChatAccountDevelopInfo.getId()!=null&&!"".equals(weChatAccountDevelopInfo.getId())){
            param = "and id='"+ weChatAccountDevelopInfo.getId()+"' ";
        }if(weChatAccountDevelopInfo.getTypecode()!=null&&!"".equals(weChatAccountDevelopInfo.getTypecode())){
            param += "and typecode='"+ weChatAccountDevelopInfo.getTypecode()+"' ";
        }
        PageInfo pageInfo = weChatAccountDevelopInfoService.selectAll(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), param);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }
}
