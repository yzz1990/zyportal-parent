package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.ChartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 * 图表统计
 */

@RestController
@RequestMapping("/system/chart")
@Api(tags = "图表分析统计")
public class ChartController extends BaseController {

    @Autowired
    private ChartService chartServiceImpl;

    /**
     * 用户操作分析(时间维度)
     */
    @PreAuthorize("hasAuthority('yhcztj_chart')")
    @PostMapping(value = "userOperationChart", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "用户操作分析(时间维度)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> userOperationChart(
            @RequestParam(name = "type",required = true) String type){
        try {
            //当前登录的用户 账号
            String who=WebUtils.getCurrentUser().getUsername();
            Map<String,Object> map=chartServiceImpl.userOperationChart(type,who);
            return makeMessage(ReturnCode.SUCCESS,map);
        }catch (Exception e){
            return makeMessage(ReturnCode.FAILED,null);
        }
    }





}
