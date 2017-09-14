package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemRole;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.SystemRoleService;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Validated
@RestController
@RequestMapping("/system/role")
@Api(tags = "角色管理")
@ApiModel("systemRole")
public class SystemRoleController extends BaseController {


    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private SystemRoleService systemRoleServiceImpl;

//    /**
//     * 获取角色列表
//     */
//    @GetMapping(value = "permissionList", produces = "application/json; charset=UTF-8")
//    @ApiOperation(value = "获取权限列表")
//    @ApiImplicitParams(
//            {
//                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
//                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
//            }
//    )
//    public List perlist(){
//        //GridModel grid = new GridModel();
//
//        try {
//            List<TreeGridView> list=new ArrayList<TreeGridView>();
//            TreeGridView treeGridView=new TreeGridView();
//            treeGridView.setId("1");
//            treeGridView.setName("系统设置");
//            treeGridView.setUrl("url");
//            treeGridView.setParentId(null);
//            list.add(treeGridView);
//
//            TreeGridView treeGridView1=new TreeGridView();
//            treeGridView1.setId("2");
//            treeGridView1.setName("aaa");
//            treeGridView1.setUrl("url");
//            treeGridView1.setParentId(null);
//            list.add(treeGridView1);
//
//            TreeGridView treeGridView2=new TreeGridView();
//            treeGridView2.setId("3");
//            treeGridView2.setName("bbb");
//            treeGridView2.setUrl("url");
//            treeGridView2.setParentId(null);
//            list.add(treeGridView2);
//
//            TreeGridView treeGridView3=new TreeGridView();
//            treeGridView3.setId("11");
//            treeGridView3.setName("系统设置新增");
//            treeGridView3.setParentId("1");
//            list.add(treeGridView3);
//            return list;
//            //grid.setRows(list);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//            //grid.setRet(false);
//        }
//
//    }

    /**
     * 获取角色列表
     */
    @PreAuthorize("hasAuthority('role_list')")
    @PostMapping(value = "roleList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public  Map<String, Object> list(
//                          @RequestParam(name = "sqlParam",required = false) String sqlParam,
                          @ModelAttribute SystemRole systemRole,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();

        try {
            String param="";
            if(systemRole!=null){
                if(systemRole.getName()!=null && systemRole.getName().trim().length()>0){
                    param+=" and name like '%"+systemRole.getName()+"%' ";
                }
                if(systemRole.getDescription()!=null && systemRole.getDescription().trim().length()>0){
                    param+=" and description like '%"+systemRole.getDescription()+"%' ";
                }
            }

            Paging page=new Paging();
            page.setPageNum(Integer.valueOf(pageNumber));
            page.setPageSize(Integer.valueOf(pageSize));
            PageInfo pageInfo=systemRoleServiceImpl.roleList(param,page);
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

    @PreAuthorize("hasAuthority('role_add')")
    @PostMapping(value = "addRole", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增角色基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addRole(@ModelAttribute SystemRole systemRole) {
        return makeMessage(systemRoleServiceImpl.addRole(systemRole));
    }

    @PreAuthorize("hasAuthority('role-edit')")
    @PostMapping(value = "updateRole", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑角色基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateRole(@ModelAttribute SystemRole systemRole){
        return makeMessage(systemRoleServiceImpl.updateRole(systemRole));
    }

    @PreAuthorize("hasAuthority('role_prohibit')")
    @PostMapping(value = "prohibitRole", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "禁用/启用 角色")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> prohibitRole(@ModelAttribute SystemRole systemRole){
        return makeMessage(systemRoleServiceImpl.updateRole(systemRole));
    }

    @PreAuthorize("hasAuthority('role_del')")
    @PostMapping(value = "delRole", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除角色基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delRole( @ModelAttribute SystemRole systemRole){
        return makeMessage(systemRoleServiceImpl.delRole(systemRole));
    }

    @PostMapping(value = "getRoleZtreeSimpleData", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取角色Ztree简单类型的数据")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getRoleZtreeSimpleData(@RequestParam(name = "sqlParam",required = false) String sqlParam){
        Map<String,Object> message=null;
        try {
            List<ZtreeSimpleView> zTreeList=systemRoleServiceImpl.getRoleZtreeSimpleData(sqlParam);
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,zTreeList);
        }catch (Exception e){
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED);
        }
        return message;
    }



//----------------------------------------------------------------------------------------------------------------------



    @PostMapping(value = "/add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增角色")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> registryUser(
            @ApiParam(required = true, value = "角色名称") @RequestParam("name") String name,
            @Length(max = 250, message = "描述最大长度为250")
            @ApiParam(value = "描述") @RequestParam(name="desc",required = false) String desc,
            @ApiParam(value = "排序") @RequestParam(name="sort",required = false) Long sort,
            @Pattern(regexp = "^[a-zA-Z_]{1,16}$", message = "标识只允许字母下划线")
            @ApiParam(value = "标识",required = true) @RequestParam(name="identification") String identification,
            @Pattern(regexp = "^[0-1]{1}$", message = "status只允许0或1")
            @ApiParam(value = "是否启用") @RequestParam(name="status",required = false,defaultValue="1") String status
            ) throws InvalidCaptchaException {

        SystemRole systemRole=new SystemRole();
        systemRole.setName(name);
        systemRole.setStatus(status);
        Date date=new Date();
        systemRole.setCreatedate(date);
        systemRole.setUpdatedate(date);
        systemRole.setDescription(desc);
        systemRole.setSort(sort);
        systemRole.setIdentification(identification);
        return makeMessage(systemUserService.saveRole(systemRole));
    }

    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUser(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id,
            @Length(max = 25, message = "角色名称最大长度为25")
            @ApiParam(required = true, value = "角色名称") @RequestParam("name") String name,
            @Length(max = 250, message = "描述最大长度为250")
            @ApiParam(value = "描述") @RequestParam(name="description",required = false) String description,
            @ApiParam(value = "排序") @RequestParam(name="sort",required = false) Long sort,
            @ApiParam(value = "是否启用",required = true) @RequestParam(name="status") String status,
            @ApiParam(value = "创建时间",required = true) @RequestParam(name="createdate") String createdate,
            @ApiParam(value = "标识",required = true) @RequestParam(name="identification") String identification
    ) throws InvalidCaptchaException {
        SystemRole role=new SystemRole();
        role.setName(name);
        role.setSort(sort);
        role.setDescription(description);
        role.setStatus(status);
        role.setUpdatedate(new Date());
        role.setIdentification(identification);
        try {
            role.setCreatedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdate));
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
        role.setId(id);
        return makeMessage(systemUserService.saveRole(role));
    }

    @PostMapping(value = "/del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据id，删除角色")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )

    public Map<String, Object> delUser(
            @ApiParam(required = true, value = "id(若和角色或权限有关联，将解除)") @RequestParam("id") String id
    ) {
        return makeMessage(systemUserService.deleteRoleById(id));
    }



    @PostMapping(value = "/findList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "模糊分页查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> findList(
            @Length(max = 25, message = "角色名称最大长度为25")
            @ApiParam(value = "角色名称") @RequestParam(name="name",required = false) String name,
            @Length(max = 250, message = "描述最大长度为250")
            @ApiParam(value = "描述") @RequestParam(name="desc",required = false) String desc,
            @ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam(name="pageNum") int pageNum,
            @ApiParam(value = "页面大小",required = true,defaultValue = "10") @RequestParam(name="pageSize") int pageSize
    ) {
        SystemRole role = new SystemRole();
        role.setName(name);
        role.setDescription(desc);

        Paging page=new Paging();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        PageInfo pageInfo=systemUserService.findRolePage(page,role);
        Map<String,Object>message=null;
        try {
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,pageInfo);
        }catch (Exception e){
            message=makeMessage(ReturnCode.UNKNOWN_ERROR);
        }

        return message;
    }


    @PostMapping(value = "/findListByUserId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据用户id获取角色列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> findListByRoleId(
            @ApiParam(required = true, value = "id") @RequestParam("id") String userId) {
        return  systemUserService.getIdsByUserId(userId);
    }

    @PostMapping(value = "/rolePermission", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "角色分配")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> permission(
            @ApiParam(required = true, value = "用户id") @RequestParam("userId") String userId,
            @ApiParam(required = true, value = "角色id，以逗号分隔") @RequestParam("ids") String ids
    ) {
        return systemUserService.rolePermission(userId,ids);
    }

}
