package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;
import java.util.Date;

public class SystemCode implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.CODE_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Long codeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.CODE_MYID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String codeMyid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.NAME
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.SORT
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Long sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.TYPE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.ICONCLS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String iconcls;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.STATE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String state;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.PERMISSIONID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Long permissionid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.PARENT_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.DESCRIPTION
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.STATUS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.CREATED
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Date created;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.LASTMOD
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private Date lastmod;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.CREATER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String creater;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_CODE.MODIFYER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    private String modifyer;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.CODE_ID
     *
     * @return the value of SYSTEM_CODE.CODE_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Long getCodeId() {
        return codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.CODE_ID
     *
     * @param codeId the value for SYSTEM_CODE.CODE_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setCodeId(Long codeId) {
        this.codeId = codeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.CODE_MYID
     *
     * @return the value of SYSTEM_CODE.CODE_MYID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getCodeMyid() {
        return codeMyid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.CODE_MYID
     *
     * @param codeMyid the value for SYSTEM_CODE.CODE_MYID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setCodeMyid(String codeMyid) {
        this.codeMyid = codeMyid == null ? null : codeMyid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.NAME
     *
     * @return the value of SYSTEM_CODE.NAME
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.NAME
     *
     * @param name the value for SYSTEM_CODE.NAME
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.SORT
     *
     * @return the value of SYSTEM_CODE.SORT
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Long getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.SORT
     *
     * @param sort the value for SYSTEM_CODE.SORT
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.TYPE
     *
     * @return the value of SYSTEM_CODE.TYPE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.TYPE
     *
     * @param type the value for SYSTEM_CODE.TYPE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.ICONCLS
     *
     * @return the value of SYSTEM_CODE.ICONCLS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getIconcls() {
        return iconcls;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.ICONCLS
     *
     * @param iconcls the value for SYSTEM_CODE.ICONCLS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setIconcls(String iconcls) {
        this.iconcls = iconcls == null ? null : iconcls.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.STATE
     *
     * @return the value of SYSTEM_CODE.STATE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.STATE
     *
     * @param state the value for SYSTEM_CODE.STATE
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.PERMISSIONID
     *
     * @return the value of SYSTEM_CODE.PERMISSIONID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Long getPermissionid() {
        return permissionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.PERMISSIONID
     *
     * @param permissionid the value for SYSTEM_CODE.PERMISSIONID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setPermissionid(Long permissionid) {
        this.permissionid = permissionid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.PARENT_ID
     *
     * @return the value of SYSTEM_CODE.PARENT_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.PARENT_ID
     *
     * @param parentId the value for SYSTEM_CODE.PARENT_ID
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.DESCRIPTION
     *
     * @return the value of SYSTEM_CODE.DESCRIPTION
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.DESCRIPTION
     *
     * @param description the value for SYSTEM_CODE.DESCRIPTION
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.STATUS
     *
     * @return the value of SYSTEM_CODE.STATUS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.STATUS
     *
     * @param status the value for SYSTEM_CODE.STATUS
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.CREATED
     *
     * @return the value of SYSTEM_CODE.CREATED
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Date getCreated() {
        return created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.CREATED
     *
     * @param created the value for SYSTEM_CODE.CREATED
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.LASTMOD
     *
     * @return the value of SYSTEM_CODE.LASTMOD
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public Date getLastmod() {
        return lastmod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.LASTMOD
     *
     * @param lastmod the value for SYSTEM_CODE.LASTMOD
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setLastmod(Date lastmod) {
        this.lastmod = lastmod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.CREATER
     *
     * @return the value of SYSTEM_CODE.CREATER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getCreater() {
        return creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.CREATER
     *
     * @param creater the value for SYSTEM_CODE.CREATER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_CODE.MODIFYER
     *
     * @return the value of SYSTEM_CODE.MODIFYER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public String getModifyer() {
        return modifyer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_CODE.MODIFYER
     *
     * @param modifyer the value for SYSTEM_CODE.MODIFYER
     *
     * @mbggenerated Mon Apr 17 09:59:31 CST 2017
     */
    public void setModifyer(String modifyer) {
        this.modifyer = modifyer;
    }


}