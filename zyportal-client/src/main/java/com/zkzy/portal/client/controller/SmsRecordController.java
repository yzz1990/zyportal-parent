package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.SmsRecordB;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISmsRecordService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/sms/record")
@Api(tags = "短信通讯记录管理")
@ApiModel(value = "smsRecord")
public class SmsRecordController extends BaseController {

    /**
     * 通讯记录服务
     */
    @Autowired
    private ISmsRecordService smsRecordServiceImpl;
    @Autowired
    private RedisRepository redisRepository;

    /**
     * 获取通讯记录列表
     */
    @PreAuthorize("hasAuthority('recordList')")
    @PostMapping(value = "recordList", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取通讯记录列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel RecordList(
                          @ModelAttribute SmsRecordB smsRecordB,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();
        try {
            String param="";
            if(smsRecordB != null){
                if(smsRecordB.getFromman()!=null && smsRecordB.getFromman().trim().length()>0){
                    param+=" and fromman like '%"+smsRecordB.getFromman()+"%' ";
                }
                if(smsRecordB.getType()!=null){
                    param+=" and type = "+smsRecordB.getType();
                }
                if(smsRecordB.getState()!=null){
                    param+=" and state = "+smsRecordB.getState();
                }
            }
            PageInfo pageInfo = smsRecordServiceImpl.recordList(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
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

    @PreAuthorize("hasAuthority('addRecord')")
    @PostMapping(value = "addRecord", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新建通讯记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addRecord(@ModelAttribute SmsRecordB Record) throws InvalidCaptchaException {
        return makeMessage(smsRecordServiceImpl.addRecord(Record));
    }

    @PreAuthorize("hasAuthority('updateRecord')")
    @PostMapping(value = "updateRecord", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改通讯记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateRecord(@ModelAttribute SmsRecordB Record) throws InvalidCaptchaException {
        return makeMessage(smsRecordServiceImpl.updateRecord(Record));
    }

    @PreAuthorize("hasAuthority('deleteRecord')")
    @PostMapping(value = "deleteRecord", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除通讯记录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteRecord(@ModelAttribute SmsRecordB Record){
        return makeMessage(smsRecordServiceImpl.deleteRecord(Record));
    }

}
