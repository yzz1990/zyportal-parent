package com.zkzy.zyportal.system.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "老人基本信息对象")
public class PensionOldmaninfoB implements Serializable {

    @ApiModelProperty(value = "唯一标识,无需输入", required = false)
    private String id;
    @ApiModelProperty(value = "老人姓名", required = true)
    private String name;
    @ApiModelProperty(value = "性别", required = true)
    private String sex;
    @ApiModelProperty(value = "有无宗教信仰", required = true)
    private String hasreligion;
    @ApiModelProperty(value = "信仰的宗教", required = false)
    private String religion;
    @ApiModelProperty(value = "身份证", required = true)
    private String pcardid;
    @ApiModelProperty(value = "文化程度", required = true)
    private String culture;
    @ApiModelProperty(value = "户籍地址", required = true)
    private String registeraddress;
    @ApiModelProperty(value = "常住地址", required = true)
    private String residentaddress;
    @ApiModelProperty(value = "居住状况", required = true)
    private String livingstatus;
    @ApiModelProperty(value = "经济来源", required = true)
    private String economicsfrom;
    @ApiModelProperty(value = "医疗费用支付方式", required = false)
    private String medicalpaytype;
    @ApiModelProperty(value = "婚姻状况", required = true)
    private String maritalstatus;
    @ApiModelProperty(value = "出生日期", required = true)
    private String birth;
    @ApiModelProperty(value = "社保卡号", required = true)
    private String socialcardid;
    @ApiModelProperty(value = "名族", required = true)
    private String famousfamily;
    @ApiModelProperty(value = "联系人姓名", required = false)
    private String contactperson;
    @ApiModelProperty(value = "联系人电话", required = false)
    private String contacttel;
    @ApiModelProperty(value = "照片路径", required = true)
    private String photourl;
    @ApiModelProperty(value = "最近评估等级", required = false)
    private String recentrating;
    @ApiModelProperty(value = "创建时间", required = false)
    private String createtime;
    @ApiModelProperty(value = "修改时间", required = false)
    private String modifytime;
    @ApiModelProperty(value = "评估次数", required = false)
    private String evaluationcount;
    @ApiModelProperty(value = "养老院编号id", required = false)
    private String nursinghomeid;

    @ApiModelProperty(value = "养老院名称", required = false)
    private String ylyName;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getHasreligion() {
        return hasreligion;
    }

    public void setHasreligion(String hasreligion) {
        this.hasreligion = hasreligion == null ? null : hasreligion.trim();
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion == null ? null : religion.trim();
    }

    public String getPcardid() {
        return pcardid;
    }

    public void setPcardid(String pcardid) {
        this.pcardid = pcardid == null ? null : pcardid.trim();
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture == null ? null : culture.trim();
    }

    public String getRegisteraddress() {
        return registeraddress;
    }

    public void setRegisteraddress(String registeraddress) {
        this.registeraddress = registeraddress == null ? null : registeraddress.trim();
    }

    public String getResidentaddress() {
        return residentaddress;
    }

    public void setResidentaddress(String residentaddress) {
        this.residentaddress = residentaddress == null ? null : residentaddress.trim();
    }

    public String getLivingstatus() {
        return livingstatus;
    }

    public void setLivingstatus(String livingstatus) {
        this.livingstatus = livingstatus == null ? null : livingstatus.trim();
    }

    public String getEconomicsfrom() {
        return economicsfrom;
    }

    public void setEconomicsfrom(String economicsfrom) {
        this.economicsfrom = economicsfrom == null ? null : economicsfrom.trim();
    }

    public String getMedicalpaytype() {
        return medicalpaytype;
    }

    public void setMedicalpaytype(String medicalpaytype) {
        this.medicalpaytype = medicalpaytype == null ? null : medicalpaytype.trim();
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus == null ? null : maritalstatus.trim();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    public String getSocialcardid() {
        return socialcardid;
    }

    public void setSocialcardid(String socialcardid) {
        this.socialcardid = socialcardid == null ? null : socialcardid.trim();
    }

    public String getFamousfamily() {
        return famousfamily;
    }

    public void setFamousfamily(String famousfamily) {
        this.famousfamily = famousfamily == null ? null : famousfamily.trim();
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson == null ? null : contactperson.trim();
    }

    public String getContacttel() {
        return contacttel;
    }

    public void setContacttel(String contacttel) {
        this.contacttel = contacttel == null ? null : contacttel.trim();
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl == null ? null : photourl.trim();
    }

    public String getRecentrating() {
        return recentrating;
    }

    public void setRecentrating(String recentrating) {
        this.recentrating = recentrating == null ? null : recentrating.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getModifytime() {
        return modifytime;
    }

    public String getEvaluationcount() {
        return evaluationcount;
    }

    public void setEvaluationcount(String evaluationcount) {
        this.evaluationcount = evaluationcount;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime == null ? null : modifytime.trim();
    }

    public String getYlyName() {
        return ylyName;
    }

    public void setYlyName(String ylyName) {
        this.ylyName = ylyName;
    }

    public String getNursinghomeid() {
        return nursinghomeid;
    }
    public void setNursinghomeid(String nursinghomeid) {
        this.nursinghomeid = nursinghomeid;
    }
}