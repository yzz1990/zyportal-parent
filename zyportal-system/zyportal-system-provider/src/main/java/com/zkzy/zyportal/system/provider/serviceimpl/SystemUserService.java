package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.Message;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.SocialService;
import com.zkzy.zyportal.system.api.viewModel.BrowserType;
import com.zkzy.zyportal.system.api.viewModel.OsType;
import com.zkzy.zyportal.system.provider.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 Created by yupc on 2017/4/8.
 */
@Service
@Transactional(readOnly = true)
public class SystemUserService implements ISystemUserService{
    /**
     * 系统用户Mapper
     */
    @Autowired
    private SystemUserMapper systemUserMapper;
    /**
     * 系统角色Mapper
     */
    @Autowired
    private SystemRoleMapper sysRoleMapper;
    /**
     * 系统菜单Mapper
     */
    @Autowired
    private SystemMenuMapper sysMenuMapper;

    @Autowired
    private SystemUserRoleMapper systemUserRoleMapper;

    @Autowired
    private SystemUserOrganizationMapper systemUserOrganizationMapper;

    @Autowired
    private SystemOrganizationMapper systemOrganizationMapper;


    @Autowired
    private RedisRepository redisRepository;


    @Autowired
    private SocialService socialService;


    @Override
    public PageInfo userList(String param, Integer pageNumber, Integer pageSize,String orgids,String roleids) {
        if(param==null){
            param="";
        }
        String str="";
        if(orgids!=null && orgids.trim().length()!=0){
            String[] organizationidArr=orgids.split(",");
            StringBuffer organizationidBuf=new StringBuffer();
            for(String s:organizationidArr){
                if(organizationidBuf.length()==0){
                    organizationidBuf.append("'"+s+"'");
                }else{
                    organizationidBuf.append(",'"+s+"'");
                }
            }
            if(str.length()==0){
                str += " select DISTINCT(userid) as useridid from SYSTEM_USER_ORGANIZATION where organizationid IN("+organizationidBuf+")";
            }else{
                str += " INTERSECT select DISTINCT(userid) as useridid from SYSTEM_USER_ORGANIZATION where organizationid IN("+organizationidBuf+")";

            }
        }
        if(roleids!=null && roleids.trim().length()!=0){
            String[] roleIdArr= roleids.split(",");
            StringBuffer roleidBuf=new StringBuffer();
            for(String s:roleIdArr){
                if(roleidBuf.length()==0){
                    roleidBuf.append("'"+s+"'");
                }else{
                    roleidBuf.append(",'"+s+"'");
                }
            }

            if(str.length()==0){
                str +=" select DISTINCT(user_id) as useridid from SYSTEM_USER_ROLE  where role_id IN("+roleidBuf+") ";
            }else{
                str +=" INTERSECT select DISTINCT(user_id) as useridid from SYSTEM_USER_ROLE  where role_id IN("+roleidBuf+") ";
            }
        }

        if(str.trim().length()>0){
            param+=" and id in ( "+str+" ) ";
        }

        PageInfo pageInfo=null;
        try {
            PageHelper.startPage(pageNumber,pageSize);//分页
            List<SystemUser> list=systemUserMapper.userList(param);
            //List<SystemUser> newList=new ArrayList<SystemUser>();
            for(SystemUser user:list){
                String orgIds=user.getOrganizeId();
                String roleIds=user.getRoleId();

                if(orgIds!=null && orgIds.trim().length()>0){
                    StringBuffer orgNameBuf=new StringBuffer();
                    String[] ids=orgIds.split(",");
                    for(String i:ids){
                        SystemOrganization org=systemOrganizationMapper.selectByPrimaryKey(i);
                        if(org!=null){
                            if(orgNameBuf.length()==0){
                                orgNameBuf.append(org.getFullName());
                            }else{
                                orgNameBuf.append(","+org.getFullName());
                            }
                        }
                    }
                    user.setOrganizeName(orgNameBuf.toString());
                }
                if(roleIds!=null && roleIds.trim().length()>0){
                    StringBuffer roleNameBuf=new StringBuffer();
                    String[] ids=roleIds.split(",");
                    for(String i:ids){
                        SystemRole systemRole=sysRoleMapper.selectByPrimaryKey(i);
                        if(systemRole!=null){
                            if(roleNameBuf.length()==0){
                                roleNameBuf.append(systemRole.getName());
                            }else{
                                roleNameBuf.append(","+systemRole.getName());
                            }
                        }
                    }
                    user.setRoleName(roleNameBuf.toString());
                }
                //newList.add(user);
            }
            pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CodeObject addUserInfo(SystemUser systemUser) {
        try {
            if (systemUser.getId()==null){
                //判断是否存在
                SystemUser oldUser = systemUserMapper.getByUserNameIgnoreStatus(systemUser.getUsername());
                if (oldUser != null) {//用户存在
                    return ReturnCode.USER_EXIST;
                }
                systemUser.setId(RandomHelper.uuid());
                systemUser.setCreateDate(new Date());
                systemUserMapper.addUserInfo(systemUser);
                //2017-07-10
//                socialService.createSubAccount(systemUser.getRealname(),systemUser);
                //2017-07-25
                socialService.createAccount(systemUser.getRealname(),systemUser);
                //新增组织关系
                String [] organizationNames=systemUser.getOrganizeName()==null?null:systemUser.getOrganizeName().split(",");
                String [] organizationIds= systemUser.getOrganizeId()==null?null:systemUser.getOrganizeId().split(",");
                if(organizationNames!=null && organizationIds!=null && organizationNames.length==organizationIds.length){
                    for (int i=0;i<organizationNames.length;i++){
                        if(organizationIds[i].trim().length()>0){
                            SystemUserOrganization systemUserOrganization=new SystemUserOrganization();
                            systemUserOrganization.setId(RandomHelper.uuid());
                            systemUserOrganization.setUserid(systemUser.getId());
                            systemUserOrganization.setOrganizationid(organizationIds[i]);

                            systemUserOrganizationMapper.insert(systemUserOrganization);
                        }

                    }
                }

                //新增角色关系
                String [] roleNames=systemUser.getRoleName()==null?null:systemUser.getRoleName().split(",");
                String [] roleIds=systemUser.getRoleId()==null?null:systemUser.getRoleId().split(",");
                if (roleNames!=null && roleIds!=null && roleNames.length==roleIds.length){
                    for(int j=0;j<roleNames.length;j++){
                        if(roleIds[j].trim().length()>0){
                            SystemUserRole systemUserRole=new SystemUserRole();
                            systemUserRole.setId(RandomHelper.uuid());
                            systemUserRole.setUserId(systemUser.getId());
                            systemUserRole.setRoleId(roleIds[j]);

                            systemUserRoleMapper.insert(systemUserRole);

                        }
                    }
                }


            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    @Override
    public CodeObject updateUserInfo(SystemUser systemUser) {
        try {
            if (systemUser.getId()!=null){
                //判断是否存在
                SystemUser oldUser = systemUserMapper.getByUserNameIgnoreStatus(systemUser.getUsername());
                if (oldUser != null) {//用户存在
                    //存在的用户和修改的用户名相同,但不是同一个
                    if(!oldUser.getId().equals(systemUser.getId())){
                        return ReturnCode.USER_EXIST;
                    }
                }

                systemUser.setUpdateDate(new Date());
                systemUserMapper.updateByPrimaryKey(systemUser);

                //更新组织关系
                    //先删除原来的关系
                systemUserOrganizationMapper.delByUserId(systemUser.getId());
                    //添加新的关系
                String [] organizationNames=systemUser.getOrganizeName()==null?null:systemUser.getOrganizeName().split(",");
                String [] organizationIds= systemUser.getOrganizeId()==null?null:systemUser.getOrganizeId().split(",");
                if(organizationNames!=null && organizationIds!=null && organizationNames.length==organizationIds.length){
                    for (int i=0;i<organizationNames.length;i++){
                        if(organizationIds[i].trim().length()>0){
                            SystemUserOrganization systemUserOrganization=new SystemUserOrganization();
                            systemUserOrganization.setId(RandomHelper.uuid());
                            systemUserOrganization.setUserid(systemUser.getId());
                            systemUserOrganization.setOrganizationid(organizationIds[i]);

                            systemUserOrganizationMapper.insert(systemUserOrganization);
                        }

                    }
                }

                //更新角色关系
                    //先删除原来的关系
                systemUserRoleMapper.deleteByUserId(systemUser.getId());
                    //添加新关系
                String [] roleNames=systemUser.getRoleName()==null?null:systemUser.getRoleName().split(",");
                String [] roleIds=systemUser.getRoleId()==null?null:systemUser.getRoleId().split(",");
                if (roleNames!=null && roleIds!=null && roleNames.length==roleIds.length){
                    for(int j=0;j<roleNames.length;j++){
                        if(roleIds[j].trim().length()>0){
                            SystemUserRole systemUserRole=new SystemUserRole();
                            systemUserRole.setId(RandomHelper.uuid());
                            systemUserRole.setUserId(systemUser.getId());
                            systemUserRole.setRoleId(roleIds[j]);

                            systemUserRoleMapper.insert(systemUserRole);

                        }
                    }
                }


            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    @Override
    public CodeObject delUserInfo(SystemUser systemUser) {
        try {
            if (systemUser.getId()!=null){
                systemUserMapper.deleteByPrimaryKey(systemUser);

                //删除组织关系,根据用户的id
                systemUserOrganizationMapper.delByUserId(systemUser.getId());

                //删除角色关系,根据用户的id
                systemUserRoleMapper.deleteByUserId(systemUser.getId());

                //删除IM账号
                socialService.closeSubAccount(systemUser.getId());

            }else{
                return ReturnCode.DEL_FAILED;//删除成功
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除失败
    }


//---------------------------------------------------------------------------------------------------------------------

    @Override
    public SystemUser getUserById(String id) {
        try {
            return systemUserMapper.get(id);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public SystemUser getUserByLoginName(String userName) {
        SystemUser user = systemUserMapper.getByUserName(userName);
        if (user == null) {
            return null;
        }
        String userId = user.getId();
        user.setRoles(sysRoleMapper.findListByUserId(userId));
        List<SystemMenu> menuList;
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
    public SystemUser getUserBasicInfoByName(String userName) {
        try {
            return systemUserMapper.getByUserName(userName);
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public CodeObject registryUser(SystemUser user){
        // 用户已存在不做处理，防止客户端重复提交
        SystemUser oldUser = systemUserMapper.getByUserNameIgnoreStatus(user.getUsername());
        if (oldUser != null) {
            return ReturnCode.USER_EXIST;
        }
        user.preInsert();
        systemUserMapper.insert(user);
        return ReturnCode.SUCCESS;
    }

    @Override
    public CodeObject updateInfo(SystemUser user) {
        SystemUser u = getUserByLoginName(user.getUsername());
        //用户不存在，不修改
        if (u == null) {
            return ReturnCode.USER_NOT_EXIST;
        }
        user.setId(u.getId());
        user.preUpdate();
        systemUserMapper.updateInfo(user);
        return ReturnCode.SUCCESS;
    }

    @Override
    public void deleteUserById(String id) {
        systemUserMapper.deleteById(id);
        systemUserMapper.deleteUser_RoleById(id);
    }

    @Override
    public PageInfo findUserList(Paging page, SystemUser user) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<SystemUser> list = systemUserMapper.findList(user);
        return new PageInfo<>(list);
    }

    @Override
    public CodeObject freezeUser(String username) {
        try {
            systemUserMapper.freezeUser(username);
        }catch(Exception e){
            return ReturnCode.UNKNOWN_ERROR;
        }
        return ReturnCode.SUCCESS;
    }
    @Override
    public void updateOSAndBroswer(String OS,String browser,String username){
        try {
            systemUserMapper.updateOSAndBroswer(OS,browser,username);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<SysMenu> getMenuNav(String userId) {
        return null;
    }

    @Override
    public List<SysMenu> getMenuList(String userId) {
        return null;
    }

    @Override
    public List<SysMenu> getMenuTree(String userId) {
        return null;
    }

    @Override
    public void deleteMenuById(String menuId) {

    }

    @Override
    public SysMenu getMenuById(String menuId) {
        return null;
    }

    @Override
    public CodeObject saveMenu(SysMenu menu) {
        return null;
    }

    @Override
    public PageInfo<SystemRole> findRolePage(Paging page, SystemRole role) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
            List<SystemRole> list = sysRoleMapper.findList(role);
            return new PageInfo<>(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public SystemRole getRoleById(String roleId) {
        SystemRole systemRole=null;
        try {
            systemRole=sysRoleMapper.getRoleById(roleId);
        }catch (Exception e){
            return null;
        }
        return systemRole;
    }

    @Override
    public CodeObject saveRole(SystemRole role) {
        try {
            if(role.getId()==null){
                SystemRole oldRole=sysRoleMapper.getRoleByName(role.getName());
                if(oldRole==null){
                    sysRoleMapper.insert(role);
                }else{
                    return ReturnCode.ROLE_EXIST;
                }
            }else{
                sysRoleMapper.update(role);
            }
        } catch (Exception e){
            return ReturnCode.UNKNOWN_ERROR;
        }
        return ReturnCode.SUCCESS;
    }

    @Override
    public CodeObject deleteRoleById(String id) {
        try {
            systemUserRoleMapper.deleteByRoleId(id);
            sysRoleMapper.delRoleByid(id);
            sysRoleMapper.delRole_Menu(id);
        }catch (Exception e){
            return ReturnCode.UNKNOWN_ERROR;
        }
        return ReturnCode.SUCCESS;
    }

    @Override
    public Map<String, Object> rolePermission(String userId, String ids) {
        try {
            systemUserRoleMapper.deleteByUserId(userId);
            if(ids!=null&&ids.length()!=0){
                String[] pids=ids.split(",");
                for(String pid:pids){
                    SystemUserRole sur=new SystemUserRole();
                    sur.setStatus("1");
                    Date date=new Date();
                    sur.setCreatedate(date);
                    sur.setUpdatedate(date);
                    sur.setUserId(userId);
                    sur.setRoleId(pid);
                    systemUserRoleMapper.insert(sur);
                }
            }
            return Message.makeMessage(ReturnCode.SUCCESS);
        } catch(Exception e){
            e.printStackTrace();
            return Message.makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public Map<String, Object> getIdsByUserId(String userId) {
        try {
            Map<String,Object> message=Message.makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,systemUserRoleMapper.getIdsByUserId(userId));
            return message;
        }catch (Exception e){
            e.printStackTrace();
            return Message.makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public int getAllUserNum() {
        return systemUserMapper.getAllUserNum().size();
    }

    @Override
    public int getSexNum(Integer sex) {
        if(sex!=null){
            return systemUserMapper.getSexNum(sex).size();
        }else{
            return 0;
        }
    }

    @Override
    public int getAgeNum(Integer bottomAge,Integer topAge) {
        String param = "";
        String year = DateHelper.getYear();
        String month = DateHelper.getMonth();
        String day = DateHelper.getDay();
        if(topAge != null && topAge != 0){
            String topAgeBirthday = (Integer.parseInt(year)-topAge)+"-"+month+"-"+day;
            param += "and birthday>'"+topAgeBirthday+"' ";
        }
        if(bottomAge != null && bottomAge != 0){
            String bottomAgeBirthday = (Integer.parseInt(year)-bottomAge)+"-"+month+"-"+day;
            param += "and birthday<='"+bottomAgeBirthday+"' ";
        }
        return systemUserMapper.getAgeNum(param).size();
    }

    @Override
    public List<OsType> selectAllOsTypes() {
        return systemUserMapper.selectAllOsTypes();
    }

    @Override
    public List<BrowserType> selectBrowInnerTypes() {
        return systemUserMapper.selectBrowInnerTypes();
    }

    @Override
    public List<BrowserType> selectBrowOuterTypes() {
        return systemUserMapper.selectBrowOuterTypes();
    }


    @Override
    public int updateLNGLAT(String username,String longitude,String latitude) {
       SystemUser systemUser = new SystemUser();
       systemUser.setUsername(username);
       systemUser.setLng(longitude);
       systemUser.setLat(latitude);
        return systemUserMapper.updateLNGLAT(systemUser);
    }

}
