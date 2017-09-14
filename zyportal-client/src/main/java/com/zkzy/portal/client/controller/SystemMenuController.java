package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.web.logFilter.LogFilter;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemMenu;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISystemService;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Sys menu controller.
 *
 * @author admin
 */

@Validated
@RestController
@RequestMapping("/system/menu")
@Api(tags="菜单管理")
public class SystemMenuController extends BaseController {
    /**
     * 系统菜单服务
     */
    @Autowired
    private ISystemService systemService;
    @Autowired
    private SystemMenuService systemMenuServiceImpl;
    @PreAuthorize("hasAuthority('zTreeMenus')")
    @PostMapping(value = "/zTree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取当前用户菜单树(Ztree)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getMenuZTree(@RequestParam(name = "roleId",required = true) String roleId) {
        AuthUser user = WebUtils.getCurrentUser();
        Map<String,Object> message=null;
        try {
            List<ZtreeSimpleView> zTreeList=systemMenuServiceImpl.getMenuZTree(roleId);
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,zTreeList);
        }catch (Exception e){
            e.printStackTrace();
            message=makeMessage(ReturnCode.FAILED);
            return message;
        }

        return message;
    }

    @PreAuthorize("hasAuthority('role_permission')")
    @PostMapping(value = "saveRoleMenu", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "保存角色和权限关系")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> saveRoleMenu(@RequestParam(name = "roleId",required = true) String roleId,
                                            @RequestParam(name = "permissionid",required = true) String permissionid) throws InvalidCaptchaException {
        return makeMessage(systemMenuServiceImpl.saveRoleMenu(roleId,permissionid));
    }


    @PreAuthorize("hasAuthority('getMenuAll')")
    @PostMapping(value = "/getMenuAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取所有菜单")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getMenuAll(
    ) {
        AuthUser user = WebUtils.getCurrentUser();
        List<SystemMenu> list=systemService.getMenuAll();
        LogFilter.setMenus(list);
        Map<String,Object> message=makeMessage(ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA,list);
        return message;
    }

    /*@PostMapping(value = "/ctree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取当前用户菜单树")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getMenuTree(
    ) {
        AuthUser user = WebUtils.getCurrentUser();

        List<SystemMenu> menuTree=systemService.getMenuTree(user.getId());
        Map<String,Object> message=makeMessage(ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA,menuTree);
        return message;
    }*/
    @PreAuthorize("hasAuthority('delmenu')")
    @PostMapping(value = "/del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据id，删除菜单树")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteMenu(
            @ApiParam(required = true, value = "id") @RequestParam("id") String menuId
    ) {
        return makeMessage(systemService.deleteMenuById(menuId));
    }
    @PreAuthorize("hasAuthority('updateMenus')")
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新菜单")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateMenu(
            @ApiParam(required = true, value = "id") @RequestParam("id") Long id,
            @ApiParam(required = true, value = "父节点") @RequestParam("parentId") Long parentid,
            @ApiParam(required = true, value = "名称") @RequestParam("name") String name,
            @ApiParam(required = true, value = "状态") @RequestParam("status") String status,
            @ApiParam(value = "排序") @RequestParam(name="sort",required = false) Long sort,
            @ApiParam(required = true, value = "类型") @RequestParam("type") String type,
            @ApiParam(required = true, value = "URL") @RequestParam("url") String url,
            @ApiParam(required = true, value = "旧权限标识") @RequestParam("oldPermission") String oldPermission,
            @ApiParam(required = true, value = "新权限标识") @RequestParam("permission") String permission,
            @ApiParam(required = true, value = "图标") @RequestParam("icon") String icon,
            @ApiParam(value = "描述") @RequestParam(name="description",required = false) String desc
    ) {

        SystemMenu sm=new SystemMenu();
        sm.setId(id);
        Date date=new Date();
        sm.setUpdatedate(date);
        sm.setDescription(desc);
        sm.setStatus(status);
        sm.setIcon(icon);
        sm.setName(name);
        sm.setParentId(parentid);
        sm.setType(type);
        sm.setPermission(permission);
        sm.setUrl(url);
        sm.setSort(sort);
        return makeMessage(systemService.saveMenu(oldPermission,sm));
    }
    @PreAuthorize("hasAuthority('permission')")
    @PostMapping(value = "/permission", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "权限分配")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> permission(
            @ApiParam(required = true, value = "角色id") @RequestParam("roleId") String roleId,
            @ApiParam(required = true, value = "权限id，以逗号分隔") @RequestParam("ids") String ids
           ) {
        return systemService.permission(roleId,ids);
    }
    @PreAuthorize("hasAuthority('findMenuByRoleId')")
    @PostMapping(value = "/findListByRoleId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据角色ID获取权限列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> findListByRoleId(
            @ApiParam(required = true, value = "id") @RequestParam("id") String roleId) {
        return  systemService.getIdsByRoleId(roleId);
    }





    @PostMapping(value = "/findParamByUserId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据当前用户角色获取权限列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> findParamByUserId(
            @ApiParam(required = true, value = "permission") @RequestParam("permission") String permission
    ){
        AuthUser user = WebUtils.getCurrentUser();
        Map<String,Object> list=systemMenuServiceImpl.findParamByUserId(user.getId(),permission,user.getLoginName());
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    @PreAuthorize("hasAuthority('addmenu')")
    @PostMapping(value = "/add")
    @ApiOperation(value = "增加菜单")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> saveMenu(
        @ApiParam(value = "父节点") @RequestParam(value="parentId",required = false) Long parentid,
        @ApiParam(required = true, value = "名称") @RequestParam("name") String name,
        @ApiParam(required = true, value = "状态") @RequestParam("status") String status,
        @ApiParam(value = "排序") @RequestParam(name="sort",required = false) Long sort,
        @ApiParam(required = true, value = "类型") @RequestParam("type") String type,
        @ApiParam(value = "URL") @RequestParam(value="url",required = false) String url,
        @ApiParam(required = true, value = "权限标识") @RequestParam("permission") String permission,
        @ApiParam(value = "图标") @RequestParam(value="icon",required = false) String icon,
        @ApiParam(value = "描述") @RequestParam(name="desc",required = false) String desc
    ) {
        SystemMenu sm=new SystemMenu();
        Date date=new Date();
        sm.setUpdatedate(date);
        sm.setDescription(desc);
        sm.setStatus(status);
        sm.setCreatedate(date);
        sm.setIcon(icon);
        sm.setName(name);
        sm.setParentId(parentid);
        sm.setType(type);
        sm.setPermission(permission);
        sm.setUrl(url);
        sm.setSort(sort);
        return makeMessage(systemService.saveMenu("",sm));
    }
}
