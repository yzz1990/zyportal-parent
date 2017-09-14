package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.utils.LogBack;
import com.zkzy.portal.common.web.logFilter.LogFilter;
import com.zkzy.portal.common.web.security.AbstractTokenUtil;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemIp;
import com.zkzy.zyportal.system.api.entity.SystemMenu;
import com.zkzy.zyportal.system.api.entity.SystemRole;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISystemService;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import com.zkzy.zyportal.system.api.viewModel.SimilarMap;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/system/ip")
@Api(tags = "用户IP管理")
public class SystemIpController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuServiceImpl;
    @Autowired
    private RedisRepository redisRepository;

    @PreAuthorize("hasAuthority('updateIp')")
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "update")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateMenu(
            @ApiParam(required = true, value = "ip") @RequestParam("ip") String ip,
            @ApiParam(required = true, value = "开始时间") @RequestParam("starttime") String starttime,
            @ApiParam(required = true, value = "结束时间") @RequestParam("endtime") String endtime,
            @ApiParam(required = true, value = "操作者") @RequestParam("recentOperator") String recentOperator
    ) throws ParseException {
        SystemIp systemIp = new SystemIp();
        systemIp.setIp(ip);
        systemIp.setStarttime(starttime);
        systemIp.setEndtime(endtime);
        systemMenuServiceImpl.updateTime(systemIp);
        if (!starttime.equals("") && !endtime.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            Date now = new Date();
            Date sdt = sdf.parse(starttime);
            Date edt = sdf.parse(endtime);
            if (!(now.getTime() >= sdt.getTime() && now.getTime() <= edt.getTime())) {
                //不在范围就踢下去
                redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH + recentOperator);
                redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER + recentOperator);
            }
        }
        return makeMessage(ReturnCode.SUCCESS);
    }

    @PreAuthorize("hasAuthority('offline')")
    @PostMapping(value = "/offline", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "离线")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> offline(
            @ApiParam(required = true, value = "操作者") @RequestParam("recentOperator") String recentOperator
    ) throws ParseException {
        redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH + recentOperator);
        redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER + recentOperator);
        return makeMessage(ReturnCode.SUCCESS);
    }

    @PreAuthorize("hasAuthority('bannedIP')")
    @PostMapping(value = "/bannedIP", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "封IP")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> bannedIP(
            @ApiParam(required = true, value = "IP") @RequestParam("IP") String IP
    ) throws ParseException {
        SystemIp systemIp = new SystemIp();
        systemIp.setStatus("0");
        systemIp.setIp(IP);
        systemMenuServiceImpl.bannedIP(systemIp);
        SystemIp sip = systemMenuServiceImpl.queryIpAll(systemIp);
        String recentOperator = sip.getRecentOperator();
        redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH + recentOperator);
        redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER + recentOperator);
        return makeMessage(ReturnCode.SUCCESS);
    }

    @PreAuthorize("hasAuthority('unBannedIP')")
    @PostMapping(value = "/unBannedIP", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "解封IP")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> unBannedIP(
            @ApiParam(required = true, value = "IP") @RequestParam("IP") String IP
    ) throws ParseException {
        SystemIp systemIp = new SystemIp();
        systemIp.setStatus("1");
        systemIp.setIp(IP);
        systemMenuServiceImpl.bannedIP(systemIp);
        return makeMessage(ReturnCode.SUCCESS);
    }


    @PreAuthorize("hasAuthority('queryAlIP')")
    @PostMapping(value = "/queryAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel queryAll(
            @ModelAttribute SystemIp systemIp,
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") int pageSize
    ) {
        GridModel grid = new GridModel();
        try {
            Paging page = new Paging();
            page.setPageNum(Integer.valueOf(pageNumber));
            page.setPageSize(Integer.valueOf(pageSize));
            PageInfo pageInfo = systemMenuServiceImpl.queryIp(page, systemIp);
            if (pageInfo != null) {
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
                grid.setRet(true);
            } else {
                grid.setRet(false);
            }
        } catch (Exception e) {
            grid.setRet(false);
            LogBack.printByLevel(e.toString(), "error");
        }
        return grid;
    }


    /**
     * 获取某一区域的用户操作数量
     * select distinct LOCATION,count(*) as count from SYSTEM_IP group by LOCATION;
     * @return
     */
    @PostMapping(value = "/getLocationCount", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取操作位置数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
    })
    public Map<String, Object> getLocationCount() {
        Map<String, Object> map =makeMessage(ReturnCode.SUCCESS);
        List<SystemIp> ipList = systemMenuServiceImpl.queryAllSystemIp();
        map.put("ipLst",ipList);
        return map;
    }


}