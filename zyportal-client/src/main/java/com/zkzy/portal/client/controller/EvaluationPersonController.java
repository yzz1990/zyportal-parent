package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.entity.EvaluationPerson;
import com.zkzy.zyportal.system.api.service.EvaluationPersonService;
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
@RequestMapping("/Evaluation/Person")
@Api(tags = "评测员信息controller")
public class EvaluationPersonController extends BaseController {
    @Autowired
    private EvaluationPersonService evaluationPersonService;
    /**
     * 增加评测员信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加评测员信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> InsertName(
            @ApiParam(required = true, value = "评测员名字") @RequestParam("name") String name,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "评测员所属机构") @RequestParam("organization") String organization

    ){
        EvaluationPerson person=new EvaluationPerson();
        person.setName(name);
        person.setPhone(phone);
        person.setOrganization(organization);
        return makeMessage( evaluationPersonService.InsertName(person));
    }

    /**
     * 删除评测员信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @DeleteMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除评测员信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
   public Map<String,Object> DelPerson(
           @ApiParam(required = true, value = "评测员ID") @RequestParam("id") String id
           ){
                return makeMessage(evaluationPersonService.DeleteById(id));
    }

    /**
     * 修改评测员信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改评测员信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> UpdatePerson(
            @ApiParam(required = true, value = "评测员ID") @RequestParam("id") String id,
            @ApiParam(required = true, value = "评测员姓名") @RequestParam("name") String name,
            @ApiParam(required = true, value = "联系电话") @RequestParam("phone") String phone,
            @ApiParam(required = true, value = "评测员所属机构") @RequestParam("organization") String organization
    ){
        EvaluationPerson person=new EvaluationPerson();
        person.setId(id);
        person.setName(name);
        person.setPhone(phone);
        person.setOrganization(organization);
        return makeMessage( evaluationPersonService.UpdateById(person));
    }
    /**
     * 查询单个评测员信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/SelectPersonById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个评测员信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public EvaluationPerson SelectPersonById(
            @ApiParam(required = true, value = "评测员ID") @RequestParam("id") String id
    ){
        return evaluationPersonService.SelectById(id);
    }

    /**
     * 查询所有评测员信息
     * Created by Zhangzy  on 2017/5/11.
     * @return the map
     */
    @PostMapping(value = "/getPersons", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有评测员信息")
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
        PageInfo pageInfo=evaluationPersonService.SelectAll(Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        return pageInfo;
    }
    /**
     * 查询所有评测员名称和ID
     * Created by Zhangzy  on 2017/5/12.
     * @return the map
     */
    @PostMapping(value = "/SelectNameId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有评测员名称和ID")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public List<EvaluationPerson> SelectNameId(){
        return evaluationPersonService.SelectName();
    }
}
