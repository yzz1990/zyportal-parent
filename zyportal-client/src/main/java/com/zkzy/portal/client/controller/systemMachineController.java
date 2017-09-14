package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemMachine;
import com.zkzy.zyportal.system.api.service.SystemMachineService;
import com.zkzy.zyportal.system.api.viewModel.EquipmentModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/7 0007.
 * 设备管理
 */

@RestController
@RequestMapping("/system/machineMan")
@Api(tags = "设备管理Controller")
@ApiModel("systemMachine")
public class systemMachineController extends BaseController {
    @Autowired
    private SystemMachineService systemMachineServiceImpl;

    /**
     * 获取设备信息列表
     */
    @PreAuthorize("hasAuthority('equipment_list')")
    @PostMapping(value = "machineList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取设备信息列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> machineList(
//                          @RequestParam(name = "sqlParam",required = false) String sqlParam,
            @ModelAttribute SystemMachine systemMachine,
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();

        try {
            String param="";
            if(systemMachine!=null){
                if(systemMachine.getStcd()!=null && systemMachine.getStcd().trim().length()>0){
                    param+=" and stcd='"+systemMachine.getStcd()+"' ";
                }
                if(systemMachine.getMname()!=null && systemMachine.getMname().trim().length()>0){
                    param+=" and mname like '%"+systemMachine.getMname()+"%' ";
                }
                if(systemMachine.getMno()!=null && systemMachine.getMno().trim().length()>0){
                    param+=" and mno like '%"+systemMachine.getMno()+"%' ";
                }
                if(systemMachine.getMtype()!=null && systemMachine.getMtype().trim().length()>0){
                    param+=" and mtype='"+systemMachine.getMtype()+"' ";
                }
                if(systemMachine.getStatus()!=null && systemMachine.getStatus().trim().length()>0){
                    param+=" and status='"+systemMachine.getStatus()+"' ";
                }

            }

            Paging page=new Paging();
            page.setPageNum(Integer.valueOf(pageNumber));
            page.setPageSize(Integer.valueOf(pageSize));
            PageInfo pageInfo=systemMachineServiceImpl.machineList(param,page);
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        }catch (Exception e){
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,grid);
        }
//        return grid;
    }

    @PreAuthorize("hasAuthority('equipment_add')")
    @PostMapping(value = "addMachine", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增设备基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addMachine(@ModelAttribute SystemMachine systemMachine) {
        return makeMessage(systemMachineServiceImpl.addMachine(systemMachine));
    }

    @PreAuthorize("hasAuthority('equipment_edit')")
    @PostMapping(value = "updateMachine", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑设备基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateMachine(@ModelAttribute SystemMachine systemMachine) {
        return makeMessage(systemMachineServiceImpl.updateMachine(systemMachine));
    }

    @PreAuthorize("hasAuthority('equipment_del')")
    @PostMapping(value = "delMachine", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除设备基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delMachine( @ModelAttribute SystemMachine systemMachine){
        return makeMessage(systemMachineServiceImpl.delMachine(systemMachine));
    }

    /**
     * 获取设备历史信息
     */
    @PreAuthorize("hasAuthority('equipment_his')")
    @PostMapping(value = "machineHis", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取设备历史信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> machineHis(
            @ModelAttribute SystemMachine systemMachine,
//            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
//            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize.
            @RequestParam(name = "startTime",required = true) String startTime,
            @RequestParam(name = "endTime",required = true)String endTime){
        GridModel grid = new GridModel();
        try {
            List<EquipmentModel> eList=systemMachineServiceImpl.machineHis(systemMachine,startTime,endTime);
            if(eList!=null){
                grid.setTotal((long) eList.size());
                grid.setRows(eList);
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return makeMessage(ReturnCode.FAILED,null);
    }


}
