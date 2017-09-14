package com.zkzy.portal.client.controller;

import com.zkzy.zyportal.system.api.service.WxNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/22 0022.
 */


@Validated
@RestController
@RequestMapping("/WeChatPlatform/news")
@Api(tags = "微信公众平台新闻类信息发布")
public class WxNewsController {

    @Autowired
    private WxNewsService wxNewsService;


    @PreAuthorize("hasAuthority('saveOrUpdateNews')")
    @GetMapping(value = "/saveOrUpdateNews", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增或更新新闻")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> saveOrUpdateNews() {


        return null;
    }


}
