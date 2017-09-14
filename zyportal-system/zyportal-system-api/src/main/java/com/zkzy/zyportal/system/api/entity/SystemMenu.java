package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SystemMenu implements Serializable{


    private List<SystemMenu> childrens;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.PARENT_ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.NAME
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.SORT
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private Long sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.TYPE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.URL
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String url;

    private String permission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.ICON
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.STATUS
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.DESCRIPTION
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.CREATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private Date createdate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MENU.UPDATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    private Date updatedate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.ID
     *
     * @return the value of SYSTEM_MENU.ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.ID
     *
     * @param id the value for SYSTEM_MENU.ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.PARENT_ID
     *
     * @return the value of SYSTEM_MENU.PARENT_ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.PARENT_ID
     *
     * @param parentId the value for SYSTEM_MENU.PARENT_ID
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.NAME
     *
     * @return the value of SYSTEM_MENU.NAME
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.NAME
     *
     * @param name the value for SYSTEM_MENU.NAME
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.SORT
     *
     * @return the value of SYSTEM_MENU.SORT
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public Long getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.SORT
     *
     * @param sort the value for SYSTEM_MENU.SORT
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setSort(Long sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.TYPE
     *
     * @return the value of SYSTEM_MENU.TYPE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.TYPE
     *
     * @param type the value for SYSTEM_MENU.TYPE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.URL
     *
     * @return the value of SYSTEM_MENU.URL
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.URL
     *
     * @param url the value for SYSTEM_MENU.URL
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.ICON
     *
     * @return the value of SYSTEM_MENU.ICON
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.ICON
     *
     * @param icon the value for SYSTEM_MENU.ICON
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.STATUS
     *
     * @return the value of SYSTEM_MENU.STATUS
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.STATUS
     *
     * @param status the value for SYSTEM_MENU.STATUS
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.DESCRIPTION
     *
     * @return the value of SYSTEM_MENU.DESCRIPTION
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.DESCRIPTION
     *
     * @param description the value for SYSTEM_MENU.DESCRIPTION
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.CREATEDATE
     *
     * @return the value of SYSTEM_MENU.CREATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.CREATEDATE
     *
     * @param createdate the value for SYSTEM_MENU.CREATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MENU.UPDATEDATE
     *
     * @return the value of SYSTEM_MENU.UPDATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MENU.UPDATEDATE
     *
     * @param updatedate the value for SYSTEM_MENU.UPDATEDATE
     *
     * @mbggenerated Mon Apr 24 15:31:24 CST 2017
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<SystemMenu> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<SystemMenu> childrens) {
        this.childrens = childrens;
    }
}