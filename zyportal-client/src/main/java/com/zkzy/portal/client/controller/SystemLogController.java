package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.utils.LogBack;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.service.SystemLogService;
import com.zkzy.zyportal.system.api.viewModel.LogPolyline;
import com.zkzy.zyportal.system.api.viewModel.LogTop;
import com.zkzy.zyportal.system.api.viewModel.UserLoginLog;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/system/log")
@Api(tags = "系统日志")
public class SystemLogController extends BaseController {

    @Autowired
    private SystemLogService systemLogService;

    @PreAuthorize("hasAuthority('queryLogGroupByDate')")
    @PostMapping(value = "/queryLogGroupByDate", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询日志")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> queryLogGroupByDate(
            @ApiParam(required = true, value = "菜单标识") @RequestParam(name = "permission") String permission,
            @ApiParam(required = true, value = "开始时间") @RequestParam(name = "startDate") String startDate,
            @ApiParam(required = true, value = "结束时间") @RequestParam(name = "endDate") String endDate
    ) {
        Map<String, Object> map;
        try {
            startDate += " 00:00:00";
            endDate += " 23:59:59";
            LogPolyline logPolyline = new LogPolyline();
            logPolyline.setPermission(permission);
            logPolyline.setStartDate(startDate);
            logPolyline.setEndDate(endDate);
            List<LogPolyline> list = systemLogService.queryLogGroupByDate(logPolyline);
            map = makeMessage(ReturnCode.SUCCESS);
            map.put(Message.RETURN_FIELD_DATA, list);
        } catch (Exception e) {
            map = makeMessage(ReturnCode.FAILED);
            LogBack.printByLevel(e, "error");
            return map;
        }
        return map;
    }


    @PreAuthorize("hasAuthority('queryAllLogGroupByPermission')")
    @PostMapping(value = "/queryAllLogGroupByPermission", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询日志菜单")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> queryLogGroupByDate(
    ) {
        Map<String, Object> map;
        try {
            List<LogPolyline> list = systemLogService.queryAllLogGroupByPermission();
            map = makeMessage(ReturnCode.SUCCESS);
            map.put(Message.RETURN_FIELD_DATA, list);
        } catch (Exception e) {
            map = makeMessage(ReturnCode.FAILED);
            LogBack.printByLevel(e, "error");
            return map;
        }
        return map;
    }

    @PreAuthorize("hasAuthority('queryMenuTop')")
    @PostMapping(value = "/queryMenuTop", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询菜单TOP10")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> queryMenuTop(
            @ApiParam(value = "开始时间") @RequestParam(name = "st",required = false) String st,
            @ApiParam(value = "结束时间") @RequestParam(name = "et",required = false) String et,
            @ApiParam(required=true, value = "类型") @RequestParam(name = "type") String type
    ) {
        Map<String, Object> map;
        try {
            String username=null;
            if("0".equals(type)){
                //当前用户
                username=WebUtils.getCurrentUser().getUsername();
            }
            String sql=" ";
            if(st!=null&&et!=null){
                st += " 00:00:00";
                et += " 23:59:59";
                sql+=" and TIME between '"+st+"' and '"+et+"' ";
            }
            if(username!=null){
                sql+=" and USERNAME='"+username+"' ";
            }
            List<LogTop> list = systemLogService.queryMenuTop(sql);
            map = makeMessage(ReturnCode.SUCCESS);
            map.put(Message.RETURN_FIELD_DATA, list);
            return map;
        } catch (Exception e) {
            map = makeMessage(ReturnCode.FAILED);
            LogBack.printByLevel(e, "error");
            return map;
        }
    }

    @PostMapping(value = "/queryUserLoginLog", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取用户登录次数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> queryUserLoginLog(
            @ApiParam(required = true, value = "菜单标识") @RequestParam(name = "uri") String uri,
            @ApiParam(required = true, value = "开始时间") @RequestParam(name = "startDate") String startDate,
            @ApiParam(required = true, value = "结束时间") @RequestParam(name = "endDate") String endDate
    ) {
        Map<String, Object> map = new HashedMap();
        startDate += " 00:00:00";
        endDate += " 23:59:59";
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setStartDate(startDate);
        userLoginLog.setEndDate(endDate);
        userLoginLog.setUri(uri);
        List<UserLoginLog> alllist = systemLogService.queryUserLoginLog(userLoginLog);
        if (alllist != null) {
            map.put("all", alllist);
        }
        AuthUser user = WebUtils.getCurrentUser();
        userLoginLog.setUsername(user.getLoginName());
        List<UserLoginLog> list = systemLogService.queryUserLoginLog(userLoginLog);
        if (list != null) {
            map.put("user", list);
        }
        if (map != null) {
            return makeMessage(ReturnCode.SUCCESS, map);
        } else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR, null);
        }
    }



}
