package com.zkzy.portal.client.controller;

import com.aliyun.oss.ServiceException;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.JobB;
import com.zkzy.zyportal.system.api.service.QuartzService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 * quartz调度管理
 */

@RestController
@RequestMapping("/quartz/quartzMan")
@Api(tags = "Quartz调度任务QuartzController")
@ApiModel(value = "info")
public class QuartzController  extends BaseController {
    @Autowired
    private QuartzService quartzServiceImpl;

    /**
     * 任务列表
     * @return
     */

    @PreAuthorize("hasAuthority('quartz_list')")
    @PostMapping(value = "list", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "任务列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> list(
//                          @RequestParam(name = "sqlParam",required = false) String sqlParam,
                          @ModelAttribute JobB info,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();

        try {
            String param="";
            if (info!=null){
                if(info.getShowname()!=null && info.getShowname().trim().length()>0){
                    param+= " and showname like '%"+info.getShowname()+"%' ";
                }
            }

            PageInfo pageInfo = quartzServiceImpl.list(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        }catch (Exception e){
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,grid);
        }
    }

    /**
     * 新增定时任务
     * @param info
     */
    @PreAuthorize("hasAuthority('quartz_add')")
    @PostMapping(value = "addJob", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addJob(@ModelAttribute JobB info){
        try {
            CodeObject codeObject =quartzServiceImpl.addJob(info);
            return makeMessage(codeObject,null);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
    }

    /**
     * 编辑定时任务
     * @param info
     */
    @PreAuthorize("hasAuthority('quartz_edit')")
    @PostMapping(value = "updateJob", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateJob(@ModelAttribute JobB info){
        try {
            CodeObject codeObject=quartzServiceImpl.edit(info);
            return makeMessage(codeObject,null);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
    }

    /**
     * 删除定时任务
     */
    @PreAuthorize("hasAuthority('quartz_del')")
    @PostMapping(value = "delete", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delete(@ModelAttribute JobB info){
        try {
            quartzServiceImpl.delete(info);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
        return makeMessage(ReturnCode.SUCCESS,null);
    }

    /**
     * 暂停定时任务
     */
    @PreAuthorize("hasAuthority('quartz_pause')")
    @PostMapping(value = "pause", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "暂停定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> pause(@ModelAttribute JobB info){
        try {
            quartzServiceImpl.pause(info);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
        return makeMessage(ReturnCode.SUCCESS,null);
    }

    /**
     * 重新开始定时任务
     */
    @PreAuthorize("hasAuthority('quartz_resume')")
    @PostMapping(value = "resume", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "重新开始定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> resume(@ModelAttribute JobB info){
        try {
            quartzServiceImpl.resume(info);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
        return makeMessage(ReturnCode.SUCCESS,null);
    }


    /**
     * 移除定时任务,但不从基本表删除
     */
    @PreAuthorize("hasAuthority('quartz_remove')")
    @PostMapping(value = "removeJob", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "从Quartz移除定时任务")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> removeJob(@ModelAttribute JobB info){
        try {
            quartzServiceImpl.removeJob(info);
        } catch (ServiceException e) {
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,null);
        }
        return makeMessage(ReturnCode.SUCCESS,null);
    }


}


