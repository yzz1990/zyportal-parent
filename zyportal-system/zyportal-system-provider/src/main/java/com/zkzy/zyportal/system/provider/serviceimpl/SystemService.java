package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.StringHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.Message;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.ISystemService;
import com.zkzy.zyportal.system.provider.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author admin
 */
@Service
@Transactional(readOnly = true)
public class SystemService implements ISystemService {

    /**
     * 系统用户Mapper
     */
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 系统角色Mapper
     */
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 系统菜单Mapper
     */
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;
    //User

    @Override
    public SysUser getUserByLoginName(String loginName) {
        SysUser user = sysUserMapper.getByLoginName(loginName);
        if (user == null) {
            return null;
        }

        String userId = user.getId();
        user.setRoles(sysRoleMapper.findListByUserId(userId));

        List<SysMenu> menuList;
        //超级管理员
        if (SysUser.ADMIN_USER_ID.equals(userId)) {
            menuList = sysMenuMapper.findAllList();
        } else {
            menuList = sysMenuMapper.findListByUserId(userId);
        }

        user.setMenus(menuList);
        return user;
    }

    @Override
    public PageInfo<SysUser> findUserPage(Paging page, SysUser user) {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysUser> list = sysUserMapper.findList(user);
        return new PageInfo<>(list);
    }

    @Override
    public SysUser getUserById(String userId) {
        SysUser user = sysUserMapper.get(userId);
        if (user != null) {
            user.setRoles(sysRoleMapper.findListByUserId(userId));
        }
        return sysUserMapper.get(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public SysUser saveUser(SysUser user) {
        if (StringHelper.isBlank(user.getId())) {
            user.preInsert();
            sysUserMapper.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate();
            sysUserMapper.update(user);
            sysUserMapper.deleteUserRole(user);
        }

        // 更新用户与角色关联
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            sysUserMapper.insertUserRole(user);
        }

        return user;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserInfo(SysUser user) {
        // 更新用户数据
        user.preUpdate();
        sysUserMapper.updateInfo(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUserById(String userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateUserPasswordById(String userId, String newPassword) {
        SysUser user = new SysUser(userId);
        user.setPassword(newPassword);
        sysUserMapper.updatePasswordById(user);
    }

    //Menu

   /* @Override
    public List<SysMenu> getMenuNav(String userId) {
        return makeTree(getMenuListByUserId(userId), true);
    }

    @Override
    public List<SysMenu> getMenuTree(String userId) {
        return makeTree(getMenuListByUserId(userId), false);
    }*/

   @Override
   public List<SystemMenu> getMenuAll(){
       try {
           return systemMenuMapper.selectAll();
       } catch (Exception e){
           return null;
       }
   }


 @Override
    public List<SystemMenu> getMenuTree(String userId) {
        try {
            return getMenuListByUserId(userId);
        } catch (Exception e){
            return null;
        }
    }

    /*private SystemMenu getMenuTree(List<SystemMenu> sourceList) {
        Iterator<SystemMenu> it = sourceList.iterator();
        while(it.hasNext()){
            SystemMenu s = it.next();
            if(s.getParentId()==0){
                FilterMenu(s,sourceList);
                return s;
            }
        }
        return null;
    }

    private void FilterMenu(SystemMenu pmenu,List<SystemMenu> sourceList){
        List<SystemMenu> chindrens=new ArrayList<SystemMenu>();
        Iterator<SystemMenu> it = sourceList.iterator();
        while(it.hasNext()){
            SystemMenu s = it.next();
            if(s.getParentId()==pmenu.getId()){
                chindrens.add(s);
                it.remove();
            }
        }
        pmenu.setChildrens(chindrens);

        for(SystemMenu s:chindrens){
            FilterMenu(s,sourceList);
        }
    }*/

    /**
     * 获得用户菜单列表，超级管理员可以查看所有菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    private List<SystemMenu> getMenuListByUserId(String userId) {
        List<SystemMenu> menuList;
        //超级管理员
        if (SystemUser.ADMIN_USER_ID.equals(userId)) {
            menuList = systemMenuMapper.findAllList();
        } else {
            menuList = systemMenuMapper.findListByUserId(userId);
        }
        return menuList;
    }

    /**
     * 构建树形结构
     *
     * @param originals 原始数据
     * @param useShow   是否使用开关控制菜单显示隐藏
     * @return 菜单列表
     */
    private List<SysMenu> makeTree(List<SysMenu> originals, boolean useShow) {

        // 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
        Map<String, SysMenu> dtoMap = new HashMap<>();
        for (SysMenu node : originals) {
            // 原始数据对象为Node，放入dtoMap中。
            String id = node.getId();
            if (node.getShow() || !useShow) {
                dtoMap.put(id, node);
            }
        }

        List<SysMenu> result = new ArrayList<>();
        for (Map.Entry<String, SysMenu> entry : dtoMap.entrySet()) {
            SysMenu node = entry.getValue();
            String parentId = node.getParentId();
            if (dtoMap.get(parentId) == null) {
                // 如果是顶层节点，直接添加到结果集合中
                result.add(node);
            } else {
                // 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
                SysMenu parent = dtoMap.get(parentId);
                parent.addChild(node);
                parent.setLeaf(false);
            }
        }

        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public CodeObject deleteMenuById(String menuId) {
        try {
            systemMenuMapper.deleteById(menuId);
            systemRoleMenuMapper.deleteByMenuId(menuId);
           return ReturnCode.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UNKNOWN_ERROR;
        }
    }

    @Override
    public Map<String,Object> getIdsByRoleId(String roleId) {
        try {
            Map<String,Object> message=Message.makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,systemMenuMapper.getIdsByRoleId(roleId));
            return message;
        }catch (Exception e){
            return Message.makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public CodeObject saveMenu(String old,SystemMenu menu) {
        try {

            if(menu.getId()==null){
                if(systemMenuMapper.getByIdPermission(menu)!=null){
                    return new CodeObject("-1","权限标识已存在");
                }
                systemMenuMapper.insert(menu);
            }else{
                if(!menu.getPermission().equals(old)){
                    if(systemMenuMapper.getByIdPermission(menu)!=null){
                        return new CodeObject("-1","权限标识已存在");
                    }
                }
                systemMenuMapper.update(menu);
            }
        }catch (Exception e){
            return ReturnCode.UNKNOWN_ERROR;
        }
        return ReturnCode.SUCCESS;
        /*// 获取父节点实体
        SysMenu parentMenu = this.getMenuById(menu.getParentId());

        String parentIds = parentMenu == null ? "" : parentMenu.getParentIds();

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();

        // 设置新的父节点串
        menu.setParentIds(parentIds + menu.getParentId() + ",");

        // 保存或更新实体
        if (StringHelper.isBlank(menu.getId())) {
            menu.preInsert();
            sysMenuMapper.insert(menu);
        } else {
            menu.preUpdate();
            sysMenuMapper.update(menu);
        }

        // 更新子节点 parentIds
        SysMenu m = new SysMenu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<SysMenu> list = sysMenuMapper.findByParentIdsLike(m);
        for (SysMenu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            sysMenuMapper.updateParentIds(e);
        }

        return menu;*/
    }

    //Role


    @Override
    public Map<String, Object> permission(String roleId, String ids) {
        try {
            systemRoleMenuMapper.deleteByRoleId(roleId);
            if(ids!=null&&ids.length()!=0){
                String[] pids=ids.split(",");
                for(String pid:pids){
                    SystemRoleMenu srm=new SystemRoleMenu();
                    srm.setStatus("1");
                    Date date=new Date();
                    srm.setCreateDate(date);
                    srm.setUpdateDate(date);
                    srm.setPermissionId(Long.valueOf(pid));
                    srm.setRoleId(roleId);
                    systemRoleMenuMapper.insert(srm);
                }
            }
            return Message.makeMessage(ReturnCode.SUCCESS);
        } catch(Exception e){
            e.printStackTrace();
            return Message.makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public PageInfo<SysRole> findRolePage(Paging page, SysRole role) {
        // 执行分页查询
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SysRole> list = sysRoleMapper.findList(role);
        return new PageInfo<>(list);
    }

    @Override
    public List<SysRole> findAllRoleList() {
        return sysRoleMapper.findAllList();
    }

    @Override
    public SysRole getRoleById(String roleId) {

        SysRole role = sysRoleMapper.get(roleId);
        if (role != null) {
            role.setMenus(sysMenuMapper.findListByRoleId(roleId));
        }

        return role;
    }


    @Override
    @Transactional(readOnly = false)
    public SysRole saveRole(SysRole role) {
        if (StringHelper.isBlank(role.getId())) {
            role.preInsert();
            sysRoleMapper.insert(role);
        } else {
            // 更新角色数据
            role.preUpdate();
            sysRoleMapper.update(role);
            sysRoleMapper.deleteRoleMenu(role);
        }

        // 更新角色与菜单关联
        if (role.getMenus() != null && !role.getMenus().isEmpty()) {
            sysRoleMapper.insertRoleMenu(role);
        }

        return role;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteRoleById(String roleId) {
        sysRoleMapper.deleteById(roleId);
    }



}
