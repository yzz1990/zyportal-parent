package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SystemOrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_ORGANIZATION
     *
     * @mbggenerated Mon Apr 17 11:20:12 CST 2017
     */
    int insert(SystemOrganization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SYSTEM_ORGANIZATION
     *
     * @mbggenerated Mon Apr 17 11:20:12 CST 2017
     */
    List<SystemOrganization> selectAll(String param);

    void update(SystemOrganization s);

    void del(String id);

    SystemOrganization selectByPrimaryKey(String organizationId);
    SystemOrganization selectByPermission(String myid);

}