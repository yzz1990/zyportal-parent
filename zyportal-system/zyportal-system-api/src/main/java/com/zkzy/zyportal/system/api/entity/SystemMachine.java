package com.zkzy.zyportal.system.api.entity;

import java.io.Serializable;

public class SystemMachine implements Serializable {

    private String stnm;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.ID
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.MNO
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String mno;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.STCD
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String stcd;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.MNAME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String mname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.MTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String mtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.IP
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.PORT
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String port;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.STATUS
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.DESCRIPTION
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.SETTINGTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String settingtype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.CREATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String createtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.UPDATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String updatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.LASTLINKEDTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String lastlinkedtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.LASTDISTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String lastdistime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SYSTEM_MACHINE.GETDATATIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    private String getdatatime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.ID
     *
     * @return the value of SYSTEM_MACHINE.ID
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.ID
     *
     * @param id the value for SYSTEM_MACHINE.ID
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.MNO
     *
     * @return the value of SYSTEM_MACHINE.MNO
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getMno() {
        return mno;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.MNO
     *
     * @param mno the value for SYSTEM_MACHINE.MNO
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setMno(String mno) {
        this.mno = mno == null ? null : mno.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.STCD
     *
     * @return the value of SYSTEM_MACHINE.STCD
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getStcd() {
        return stcd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.STCD
     *
     * @param stcd the value for SYSTEM_MACHINE.STCD
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setStcd(String stcd) {
        this.stcd = stcd == null ? null : stcd.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.MNAME
     *
     * @return the value of SYSTEM_MACHINE.MNAME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getMname() {
        return mname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.MNAME
     *
     * @param mname the value for SYSTEM_MACHINE.MNAME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.MTYPE
     *
     * @return the value of SYSTEM_MACHINE.MTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getMtype() {
        return mtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.MTYPE
     *
     * @param mtype the value for SYSTEM_MACHINE.MTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setMtype(String mtype) {
        this.mtype = mtype == null ? null : mtype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.IP
     *
     * @return the value of SYSTEM_MACHINE.IP
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.IP
     *
     * @param ip the value for SYSTEM_MACHINE.IP
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.PORT
     *
     * @return the value of SYSTEM_MACHINE.PORT
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getPort() {
        return port;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.PORT
     *
     * @param port the value for SYSTEM_MACHINE.PORT
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.STATUS
     *
     * @return the value of SYSTEM_MACHINE.STATUS
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.STATUS
     *
     * @param status the value for SYSTEM_MACHINE.STATUS
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.DESCRIPTION
     *
     * @return the value of SYSTEM_MACHINE.DESCRIPTION
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.DESCRIPTION
     *
     * @param description the value for SYSTEM_MACHINE.DESCRIPTION
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.SETTINGTYPE
     *
     * @return the value of SYSTEM_MACHINE.SETTINGTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getSettingtype() {
        return settingtype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.SETTINGTYPE
     *
     * @param settingtype the value for SYSTEM_MACHINE.SETTINGTYPE
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setSettingtype(String settingtype) {
        this.settingtype = settingtype == null ? null : settingtype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.CREATETIME
     *
     * @return the value of SYSTEM_MACHINE.CREATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.CREATETIME
     *
     * @param createtime the value for SYSTEM_MACHINE.CREATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.UPDATETIME
     *
     * @return the value of SYSTEM_MACHINE.UPDATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getUpdatetime() {
        return updatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.UPDATETIME
     *
     * @param updatetime the value for SYSTEM_MACHINE.UPDATETIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.LASTLINKEDTIME
     *
     * @return the value of SYSTEM_MACHINE.LASTLINKEDTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getLastlinkedtime() {
        return lastlinkedtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.LASTLINKEDTIME
     *
     * @param lastlinkedtime the value for SYSTEM_MACHINE.LASTLINKEDTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setLastlinkedtime(String lastlinkedtime) {
        this.lastlinkedtime = lastlinkedtime == null ? null : lastlinkedtime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.LASTDISTIME
     *
     * @return the value of SYSTEM_MACHINE.LASTDISTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getLastdistime() {
        return lastdistime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.LASTDISTIME
     *
     * @param lastdistime the value for SYSTEM_MACHINE.LASTDISTIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setLastdistime(String lastdistime) {
        this.lastdistime = lastdistime == null ? null : lastdistime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SYSTEM_MACHINE.GETDATATIME
     *
     * @return the value of SYSTEM_MACHINE.GETDATATIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public String getGetdatatime() {
        return getdatatime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SYSTEM_MACHINE.GETDATATIME
     *
     * @param getdatatime the value for SYSTEM_MACHINE.GETDATATIME
     *
     * @mbggenerated Fri Jul 07 14:04:27 CST 2017
     */
    public void setGetdatatime(String getdatatime) {
        this.getdatatime = getdatatime == null ? null : getdatatime.trim();
    }

    public String getStnm() {
        return stnm;
    }
    public void setStnm(String stnm) {
        this.stnm = stnm;
    }
}