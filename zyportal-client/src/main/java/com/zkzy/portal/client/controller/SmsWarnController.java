package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.SmsWarnB;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISmsWarnService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/sms/warn")
@Api(tags = "短信预警管理")
@ApiModel(value = "smsWarn")
public class SmsWarnController extends BaseController {

    /**
     * 短信预警服务
     */
    @Autowired
    private ISmsWarnService smsWarnServiceImpl;
    @Autowired
    private RedisRepository redisRepository;

    /**
     * 获取短信预警列表
     */
    @PreAuthorize("hasAuthority('warnList')")
    @PostMapping(value = "warnList", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取短信预警列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel warnList(
                          @ModelAttribute SmsWarnB smsWarnB,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();
        try {
            String param="";
            if(smsWarnB != null){
                if(smsWarnB.getJobname()!=null && smsWarnB.getJobname().trim().length()>0){
                    param+=" and jobname like '%"+smsWarnB.getJobname()+"%' ";
                }
            }
            PageInfo pageInfo = smsWarnServiceImpl.warnList(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
                grid.setRet(true);
            }else{
                grid.setRet(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            grid.setRet(false);
        }
        return grid;
    }

    @PreAuthorize("hasAuthority('addWarn')")
    @PostMapping(value = "addWarn", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新建短信预警")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addWarn(@ModelAttribute SmsWarnB Warn) throws InvalidCaptchaException {
        return makeMessage(smsWarnServiceImpl.addWarn(Warn));
    }

    @PreAuthorize("hasAuthority('updateWarn')")
    @PostMapping(value = "updateWarn", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改短信预警")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateWarn(@ModelAttribute SmsWarnB Warn) throws InvalidCaptchaException {
        return makeMessage(smsWarnServiceImpl.updateWarn(Warn));
    }

    @PreAuthorize("hasAuthority('deleteWarn')")
    @PostMapping(value = "deleteWarn", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除短信预警")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteWarn(@ModelAttribute SmsWarnB Warn){
        return makeMessage(smsWarnServiceImpl.deleteWarn(Warn));
    }

}
