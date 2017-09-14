package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.entity.EvaluationOrganization;
import com.zkzy.zyportal.system.api.service.EvaluationOrganizationService;
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
@RequestMapping("/Evaluation/Organization")
@Api(tags = "评测机构信息controller")
public class EvaluationOrganizationController extends BaseController {
    @Autowired
    private EvaluationOrganizationService evaluationOrganizationService;
    /**
     * 增加评测机构信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加评测机构信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> InsertName(
            @ApiParam(required = true, value = "评测机构名字") @RequestParam("department") String department,
            @ApiParam(required = true, value = "联系人") @RequestParam("linkman") String linkman,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "评测机构地址") @RequestParam("address") String address,
            @ApiParam(required = true, value = "评测机构人数") @RequestParam("staffcount") String staffcount

    ){
        EvaluationOrganization organ=new EvaluationOrganization();
        organ.setDepartment(department);
        organ.setPhone(phone);
        organ.setLinkman(linkman);
        organ.setAddress(address);
        organ.setStaffcount(staffcount);
        return makeMessage( evaluationOrganizationService.InsertDepartment(organ));
    }

    /**
     * 删除评测机构信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @DeleteMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除评测机构信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
   public Map<String,Object> DelPerson(
           @ApiParam(required = true, value = "评测机构ID") @RequestParam("id") String id
           ){
                return makeMessage(evaluationOrganizationService.DeleteById(id));
    }

    /**
     * 修改评测机构信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改评测机构信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> UpdatePerson(
            @ApiParam(required = true, value = "评测机构ID") @RequestParam("id") String id,
            @ApiParam(required = true, value = "评测机构姓名") @RequestParam("department") String department,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "联系人") @RequestParam("linkman") String linkman,
            @ApiParam(required = true, value = "评测机构地址") @RequestParam("address") String address
    ){
        EvaluationOrganization person=new EvaluationOrganization();
        person.setId(id);
        person.setDepartment(department);
        person.setPhone(phone);
        person.setLinkman(linkman);
        person.setAddress(address);
        return makeMessage( evaluationOrganizationService.UpdateById(person));
    }
    /**
     * 查询单个评测机构信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/SelectPersonById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个评测机构信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public EvaluationOrganization SelectPersonById(
            @ApiParam(required = true, value = "评测机构ID") @RequestParam("id") String id
    ){
        return evaluationOrganizationService.SelectById(id);
    }

    /**
     * 查询所有评测机构信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/getPersons", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有评测机构信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
}
    )
    public PageInfo getPersons(
            @RequestParam(name = "currentPage",required = true,defaultValue = "1") String currentPage,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize
    ){
        PageInfo pageInfo=evaluationOrganizationService.SelectAll(Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        return pageInfo;
    }
    /**
     * 查询所有评测机构名称和ID
     * Created by Zhangzy  on 2017/5/12.
     * @return the map
     */
    @PostMapping(value = "/SelectNameId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有评测机构名称和ID")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public List<EvaluationOrganization> SelectNameId(){
        return evaluationOrganizationService.SelectName();
    }
}
