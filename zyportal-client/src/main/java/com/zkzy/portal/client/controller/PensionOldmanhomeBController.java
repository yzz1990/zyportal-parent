package com.zkzy.portal.client.controller;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.PensionOldmanhomeB;
import com.zkzy.zyportal.system.api.service.PensionOldmanhomeBService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *  Created by Zhangzy  on 2017/5/11.
 */
@Validated
@RestController
@RequestMapping("/Pension/OldmanHome")
@Api(tags = "老人院信息controller")
public class PensionOldmanhomeBController extends BaseController {
    @Autowired
    private PensionOldmanhomeBService pensionOldmanhomeBService;
    /**
     * 增加养老院信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加养老院信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> InsertOldmanHome(
            @ApiParam(required = true, value = "养老院名称") @RequestParam("name") String name,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "养老院地址") @RequestParam("address") String address,
            @ApiParam(required = true, value = "所属区") @RequestParam("area") String area
    ){
        PensionOldmanhomeB Oldmanhome=new PensionOldmanhomeB();
        Oldmanhome.setName(name);
        Oldmanhome.setPhone(phone);
        Oldmanhome.setaddress(address);
        Oldmanhome.setArea(area);
        return makeMessage( pensionOldmanhomeBService.InsertOldmanHome(Oldmanhome));
    }

    /**
     * 删除养老院信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除养老院信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
   public Map<String,Object> DelOldmanHome(
           @ApiParam(required = true, value = "养老院ID") @RequestParam("id") String id
           ){
                return makeMessage(pensionOldmanhomeBService.DeleteById(id));
    }

    /**
     * 修改养老院信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改养老院信息")
    @ApiImplicitParams(
            {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                    dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> UpdateOldmanHome(
            @ApiParam(required = true, value = "养老院ID") @RequestParam("id") String id,
            @ApiParam(required = true, value = "养老院名称") @RequestParam("name") String name,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "养老院地址") @RequestParam("address") String address,
            @ApiParam(required = true, value = "所属区") @RequestParam("area") String area
    ){
        PensionOldmanhomeB Oldmanhome=new PensionOldmanhomeB();
        Oldmanhome.setId(id);
        Oldmanhome.setName(name);
        Oldmanhome.setPhone(phone);
        Oldmanhome.setaddress(address);
        Oldmanhome.setArea(area);
        return makeMessage( pensionOldmanhomeBService.UpdateById(Oldmanhome));
    }
    /**
     * 查询单个养老院信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/SelectOldmanhomeById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个养老院信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object>  SelectOldmanhomeById(
            @ApiParam(required = true, value = "养老院ID") @RequestParam("id") String id
    ){
        PensionOldmanhomeB pension=pensionOldmanhomeBService.SelectById(id);
        if(pension!=null){
            return makeMessage(ReturnCode.SUCCESS,pension);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    /**
     * 查询所有养老院信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/getOldmanHomes", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有养老院信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
}
    )
    public GridModel getOldmanHomes(
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize,
            @ModelAttribute PensionOldmanhomeB pensionOldmanhomeB
    ){
        String sqlParam="";
        if(!"".equals(pensionOldmanhomeB.getName())&&pensionOldmanhomeB.getName()!=null){
            sqlParam+="and name like '%"+pensionOldmanhomeB.getName()+"%'";
        }
        if(!"".equals(pensionOldmanhomeB.getaddress())&&pensionOldmanhomeB.getaddress()!=null){
            sqlParam+="and address like '%"+pensionOldmanhomeB.getaddress()+"%'";;
        }
        if(!"".equals(pensionOldmanhomeB.getArea())&&pensionOldmanhomeB.getArea()!=null){
            sqlParam+="and area like '%"+pensionOldmanhomeB.getArea()+"%'";
        }
        PageInfo pageInfo=pensionOldmanhomeBService.SelectAll(Integer.valueOf(pageNumber),Integer.valueOf(pageSize),sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }
    /**
     * 查询所有养老院名称和ID
     * Created by Zhangzy  on 2017/5/12.
     * @return the map
     */
    @PostMapping(value = "/SelectNameId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有养老院名称和ID")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> SelectNameId(){

        List<PensionOldmanhomeB> list=pensionOldmanhomeBService.SelectName();
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }
}
