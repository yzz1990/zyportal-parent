package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.AreaAdministrationareaB;
import com.zkzy.zyportal.system.api.service.AreaAdministrationareaBService;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Zhangzy on 2017/5/10.
 */
@Validated
@RestController
@RequestMapping("/Area/AdministrationArea")
@Api(tags = "行政区域Controller")
public class AreaAdministrationareaBController extends BaseController {
    @Autowired
    private AreaAdministrationareaBService areaAdministrationareaBService;

    /**
     * 增加行政区域信息
     * Created by Zhangzy  on 2017/5/11.
     *
     * @return the map
     */
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加行政区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )

    public Map<String, Object> InsertArea(
            @ApiParam(required = true, value = "所属省") @RequestParam("province") String province,
            @ApiParam(required = true, value = "所属市") @RequestParam("city") String city,
            @ApiParam(required = true, value = "所属区") @RequestParam("area") String area,
            @ApiParam(required = true, value = "所属街道") @RequestParam("street") String street
    ) {
        AreaAdministrationareaB Administration = new AreaAdministrationareaB();
        Administration.setProvince(province);
        Administration.setCity(city);
        Administration.setArea(area);
        Administration.setStreet(street);
        return makeMessage(areaAdministrationareaBService.InsertArea(Administration));
    }

    /**
     * 删除行政区域信息
     * Created by Zhangzy  on 2017/5/11.
     *
     * @return the map
     */
    @PostMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除行政区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> DelAreaByName(
            @ApiParam(required = true, value = "行政区域ID") @RequestParam("id") String id
    ) {
        return makeMessage(areaAdministrationareaBService.DeleteById(id));
    }

    /**
     * 修改行政区域信息
     * Created by Zhangzy  on 2017/5/11.
     *
     * @return the map
     */
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改行政区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> UpdateArea(
            @ApiParam(required = true, value = "行政区域ID") @RequestParam("id") String id,
            @ApiParam(required = true, value = "所属省") @RequestParam("province") String province,
            @ApiParam(required = true, value = "所属市") @RequestParam("city") String city,
            @ApiParam(required = true, value = "所属区") @RequestParam("area") String area,
            @ApiParam(required = true, value = "所属街道") @RequestParam("street") String street
    ) {
        AreaAdministrationareaB administration = new AreaAdministrationareaB();
        administration.setId(id);
        administration.setProvince(province);
        administration.setCity(city);
        administration.setArea(area);
        administration.setStreet(street);
        return makeMessage(areaAdministrationareaBService.UpdateById(administration));
    }

    /**
     * 查询单个行政区域信息
     * Created by Zhangzy  on 2017/5/11.
     *
     * @return the map
     */
    @PostMapping(value = "/SelectAreaById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个行政区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> SelectAreaById(
            @ApiParam(required = true, value = "行政区域ID") @RequestParam("id") String id
    ) {
        AreaAdministrationareaB area=areaAdministrationareaBService.SelectById(id);
        if (area!=null){
            return makeMessage(ReturnCode.SUCCESS,area);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    /**
     * 查询所有行政区域信息
     * Created by Zhangzy  on 2017/5/11.
     *
     * @return the map
     */
    @PostMapping(value = "/getAreas", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有行政区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel getAreas(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @ModelAttribute AreaAdministrationareaB areaAdministrationareaB
    ) {
        String sqlParam="";
        if(!"".equals(areaAdministrationareaB.getCity())&&areaAdministrationareaB.getCity()!=null){
            sqlParam+="and city like '%"+areaAdministrationareaB.getCity()+"%'";
        }
        if(!"".equals(areaAdministrationareaB.getProvince())&&areaAdministrationareaB.getProvince()!=null){
            sqlParam+="and province like '%"+areaAdministrationareaB.getProvince()+"%'";
        }
        if(!"".equals(areaAdministrationareaB.getArea())&&areaAdministrationareaB.getArea()!=null){
            sqlParam+="and area like '%"+areaAdministrationareaB.getArea()+"%'";
        }
        if(!"".equals(areaAdministrationareaB.getStreet())&&areaAdministrationareaB.getStreet()!=null){
            sqlParam+="and street like '%"+areaAdministrationareaB.getStreet()+"%'";
        }
        PageInfo pageInfo = areaAdministrationareaBService.SelectAll(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    /**
     * 查询所有区域信息
     * Created by Zhangzy  on 2017/5/17.
     *
     * @return the list
     */
    @PostMapping(value = "/SelectAreas", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询不重复区域信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> SelectAreas() {

        List<AreaAdministrationareaB> list=areaAdministrationareaBService.selectAreas();
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }

    }

}
