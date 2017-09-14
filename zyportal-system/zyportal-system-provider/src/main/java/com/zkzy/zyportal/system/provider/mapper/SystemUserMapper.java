package com.zkzy.zyportal.system.provider.mapper;


import com.zkzy.portal.common.service.dao.CrudDao;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.viewModel.BrowserType;
import com.zkzy.zyportal.system.api.viewModel.OsType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by yupc on 2017/4/8.
 */
@Mapper
public interface SystemUserMapper extends CrudDao<SystemUser> {

    /**
     * 根据登录名称查询用户
     *
     * @param userName 登录名
     * @return SysUser by login name
     */
    SystemUser getByUserName(String userName);

    /**
     * 根据登录名称查询用户
     *
     * @param userName 登录名
     * @return SysUser by login name
     */
    SystemUser getByUserNameIgnoreStatus(String userName);

    /**
     * 冻结账户
     */
    void freezeUser(String id);

    /**
     * 删除用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int deleteUserRole(SystemUser user);

    /**
     * 插入用户角色关联数据
     *
     * @param user the user
     * @return the int
     */
    int insertUserRole(SystemUser user);

    /**
     * 保存用户信息
     *
     * @param user the user
     */
    void updateInfo(SystemUser user);

    void deleteUser_RoleById(String id);

    List<SystemUser> userList(String param);
    void addUserInfo(SystemUser systemUser);
    void updateByPrimaryKey(SystemUser systemUser);
    void deleteByPrimaryKey(SystemUser systemUser);

    void updateOSAndBroswer(String os,String browser,String username);

    List<SystemUser> getAllUserNum();
    List<SystemUser> getSexNum(int sex);
    List<SystemUser> getAgeNum(String param);

    List<OsType> selectAllOsTypes();
    List<BrowserType> selectBrowInnerTypes();
    List<BrowserType> selectBrowOuterTypes();
    int updateLNGLAT(SystemUser systemUser);
}
