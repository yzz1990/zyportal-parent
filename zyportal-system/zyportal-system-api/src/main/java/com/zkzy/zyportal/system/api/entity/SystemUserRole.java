package com.zkzy.zyportal.system.api.entity;

import java.util.Date;

public class SystemUserRole {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.USER_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private String roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.STATUS
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.CREATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_USER_ROLE.UPDATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    private Date updatedate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.ID
     *
     * @return the value of SYSTEM_USER_ROLE.ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.ID
     *
     * @param id the value for SYSTEM_USER_ROLE.ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.USER_ID
     *
     * @return the value of SYSTEM_USER_ROLE.USER_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.USER_ID
     *
     * @param userId the value for SYSTEM_USER_ROLE.USER_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.ROLE_ID
     *
     * @return the value of SYSTEM_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.ROLE_ID
     *
     * @param roleId the value for SYSTEM_USER_ROLE.ROLE_ID
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.STATUS
     *
     * @return the value of SYSTEM_USER_ROLE.STATUS
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.STATUS
     *
     * @param status the value for SYSTEM_USER_ROLE.STATUS
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.CREATEDATE
     *
     * @return the value of SYSTEM_USER_ROLE.CREATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.CREATEDATE
     *
     * @param createdate the value for SYSTEM_USER_ROLE.CREATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_USER_ROLE.UPDATEDATE
     *
     * @return the value of SYSTEM_USER_ROLE.UPDATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_USER_ROLE.UPDATEDATE
     *
     * @param updatedate the value for SYSTEM_USER_ROLE.UPDATEDATE
     *
     * @mbggenerated Thu Apr 27 14:32:34 CST 2017
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }
}