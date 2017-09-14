package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.SystemRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_ROLE
     *
     * @mbggenerated Mon Apr 24 09:22:37 CST 2017
     */
    int insert(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_ROLE
     *
     * @mbggenerated Mon Apr 24 09:22:37 CST 2017
     */
    List<SystemRole> selectAll(String param);


    List<SystemRole> findListByUserId(String userId);

    List<SystemRole> findList(SystemRole role);

    void delRoleByid(String id);

    SystemRole getRoleById(String id);

    SystemRole getRoleByName(String name);

    void update(SystemRole systemRole);

    void delRole_Menu(String id);

    SystemRole selectByPrimaryKey(String id);
}