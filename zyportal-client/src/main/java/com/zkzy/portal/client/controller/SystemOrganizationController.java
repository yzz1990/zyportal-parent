package com.zkzy.portal.client.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.SystemOrganizationService;
import com.zkzy.zyportal.system.api.viewModel.Tree;
import com.zkzy.zyportal.system.api.viewModel.TreeModel;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/system/organization")
@Api(tags = "组织管理")
public class SystemOrganizationController extends BaseController {

    /**
     * 系统用户服务
     */
    @Autowired
    private SystemOrganizationService systemOrganizationService;
    @PreAuthorize("hasAuthority('addOrganization')")
    @PostMapping(value = "/add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增组织")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> add(
            @ApiParam(required = true, value = "组织名称") @RequestParam("fullName") String fullName,
            @ApiParam(required = true, value = "组织编码") @RequestParam("myid") String myid,
            @ApiParam(value = "上层组织") @RequestParam(value="pid",required = false) String pid,
            @ApiParam(value = "组织图标") @RequestParam(value="iconcls",required = false) String iconcls,
            @ApiParam(value = "电话") @RequestParam(value="tel",required = false) String tel,
            @ApiParam(value = "传真") @RequestParam(value="fax",required = false) String fax,
            @ApiParam(value = "描述") @RequestParam(value="description",required = false) String description
            ) throws InvalidCaptchaException {

        SystemOrganization systemOrganization=new SystemOrganization();
        systemOrganization.setStatus("1");
        systemOrganization.setFullName(fullName);
        systemOrganization.setMyid(myid);
        systemOrganization.setPid(pid);
        systemOrganization.setIconcls(iconcls);
        systemOrganization.setTel(tel);
        systemOrganization.setFax(fax);
        systemOrganization.setDescription(description);

        return makeMessage(systemOrganizationService.saveOrganiza(systemOrganization,null));
    }
    @PreAuthorize("hasAuthority('updateOrganization')")
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新组织")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                             dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> update(
            @ApiParam(value = "组织id") @RequestParam(value="organizationId",required = false) String organizationId,
            @ApiParam(value = "老标识") @RequestParam(value="oldPermission",required = false) String oldPermission,
            @ApiParam(required = true, value = "组织名称") @RequestParam("fullName") String fullName,
            @ApiParam(required = true, value = "组织编码") @RequestParam("myid") String myid,
            @ApiParam(value = "上层组织") @RequestParam(value="pid",required = false) String pid,
            @ApiParam(value = "组织图标") @RequestParam(value="iconcls",required = false) String iconcls,
            @ApiParam(value = "电话") @RequestParam(value="tel",required = false) String tel,
            @ApiParam(value = "传真") @RequestParam(value="fax",required = false) String fax,
            @ApiParam(value = "描述") @RequestParam(value="description",required = false) String description
    ) throws InvalidCaptchaException {
        SystemOrganization systemOrganization=new SystemOrganization();
        systemOrganization.setOrganizationId(organizationId);
        systemOrganization.setStatus("1");
        systemOrganization.setFullName(fullName);
        systemOrganization.setMyid(myid);
        systemOrganization.setPid(pid);
        systemOrganization.setIconcls(iconcls);
        systemOrganization.setTel(tel);
        systemOrganization.setFax(fax);
        systemOrganization.setDescription(description);

        return makeMessage(systemOrganizationService.saveOrganiza(systemOrganization,oldPermission));
    }
    @PreAuthorize("hasAuthority('delOrganization')")
    @PostMapping(value = "/del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除组织")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> del(
            @ApiParam(value = "id") @RequestParam(value="id",required = false) String id
    ) throws InvalidCaptchaException {
        AuthUser user = WebUtils.getCurrentUser();
        return makeMessage(systemOrganizationService.delbyId(id));
    }

    @PreAuthorize("hasAuthority('organizationGrid')")
    @PostMapping(value = "/queryTree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询树状结构TreeGrid")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> queryTree(
    ) throws InvalidCaptchaException {
        Map<String,Object> message=null;
        message=makeMessage(ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA,systemOrganizationService.selectAll(""));
        return message;
    }
    @PreAuthorize("hasAuthority('organizationTree')")
    @PostMapping(value = "/tree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询树状结构tree")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> Tree(
    ) throws InvalidCaptchaException {
        Map<String,Object> message=null;
        message=makeMessage(ReturnCode.SUCCESS);
        List<SystemOrganization> list=systemOrganizationService.selectAll("");
        List<ZtreeSimpleView> zsList=new ArrayList<>();
        for(SystemOrganization so:list){
            ZtreeSimpleView zs=new ZtreeSimpleView();
            zs.setId(so.getOrganizationId());
            zs.setName(so.getFullName());
            zs.setpId(so.getPid());
            zsList.add(zs);
        }
        message.put(Message.RETURN_FIELD_DATA,zsList);
        return message;
    }

    @PreAuthorize("hasAuthority('queryOrganizationListTree')")
    @PostMapping(value = "/organizationListTree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "组织架构树状图")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> organizationListTree(
    ) throws InvalidCaptchaException {
        Map<String,Object> message=null;
        message=makeMessage(ReturnCode.SUCCESS);
        List<TreeModel> m=systemOrganizationService.selectAllTree("");
        Tree tree = new Tree(m);
        message.put(Message.RETURN_FIELD_DATA,tree.buildTree("组织架构图"));
        return message;
    }

}
