package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;

public class SystemLog implements Serializable{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.ID
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.IP
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.LOCATION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String location;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.USERAGENT
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String useragent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.USERNAME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.URI
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String uri;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.PERMISSION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String permission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_LOG.TIME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    private String time;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.ID
     *
     * @return the value of SYSTEM_LOG.ID
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.ID
     *
     * @param id the value for SYSTEM_LOG.ID
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.IP
     *
     * @return the value of SYSTEM_LOG.IP
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.IP
     *
     * @param ip the value for SYSTEM_LOG.IP
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.LOCATION
     *
     * @return the value of SYSTEM_LOG.LOCATION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getLocation() {
        return location;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.LOCATION
     *
     * @param location the value for SYSTEM_LOG.LOCATION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.USERAGENT
     *
     * @return the value of SYSTEM_LOG.USERAGENT
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getUseragent() {
        return useragent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.USERAGENT
     *
     * @param useragent the value for SYSTEM_LOG.USERAGENT
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setUseragent(String useragent) {
        this.useragent = useragent == null ? null : useragent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.USERNAME
     *
     * @return the value of SYSTEM_LOG.USERNAME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.USERNAME
     *
     * @param username the value for SYSTEM_LOG.USERNAME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.URI
     *
     * @return the value of SYSTEM_LOG.URI
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getUri() {
        return uri;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.URI
     *
     * @param uri the value for SYSTEM_LOG.URI
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setUri(String uri) {
        this.uri = uri == null ? null : uri.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.PERMISSION
     *
     * @return the value of SYSTEM_LOG.PERMISSION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.PERMISSION
     *
     * @param permission the value for SYSTEM_LOG.PERMISSION
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_LOG.TIME
     *
     * @return the value of SYSTEM_LOG.TIME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public String getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_LOG.TIME
     *
     * @param time the value for SYSTEM_LOG.TIME
     *
     * @mbggenerated Mon Jul 10 13:15:49 CST 2017
     */
    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }
}