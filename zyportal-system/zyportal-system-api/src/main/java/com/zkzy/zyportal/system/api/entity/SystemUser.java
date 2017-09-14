package com.zkzy.zyportal.system.api.entity;

import com.zkzy.portal.common.api.DataEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yupc on 2017/4/8.
 */
public class SystemUser extends DataEntity{

	/**
	 * 超级管理用户ID
	 */
	public static final String ADMIN_USER_ID = "1";


	/**
	 * 角色列表
	 */
	private List<SystemRole> roles = new ArrayList<>();
	/**
	 * 菜单列表
	 */
	private List<SystemMenu> menus = new ArrayList<>();

	private String username;
	private String password;
	private String realname;
	private String organizeId;
	private String organizeName;
	private Integer dutyId;
	private Integer titleId;
	private String email;
	private String lang;
	private String theme;
	private Date firstVisit;
	private Date previousVisit;
	private Date lastVisits;
	private Integer loginCount;
	/**
	 * 账户状态
	 */
	private String status;
	//冻结
	public static final String DISENABLED = "0";
	//正常
	public static final String ENABLED = "1";


	private String ip;
	private String description;
	private Integer isonline;
	private String tel;

	private String isemployee;
	private String creater;
	private String modifyer;
	private String roleName;
	private String roleId;
	private String area;
	private String location;
	private String photourl;
	private Integer sex;
	private String birthday;
	private String ostype;
	private String browsertype;
	private String lng;
	private String lat;



	// Constructors
	public SystemUser() {
		super();
	}

	public SystemUser(String id) {
		super(id);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getOrganizeId() {
		return organizeId;
	}

	public void setOrganizeId(String organizeId) {
		this.organizeId = organizeId;
	}

	public String getOrganizeName() {
		return organizeName;
	}

	public void setOrganizeName(String organizeName) {
		this.organizeName = organizeName;
	}

	public Integer getDutyId() {
		return dutyId;
	}

	public void setDutyId(Integer dutyId) {
		this.dutyId = dutyId;
	}

	public Integer getTitleId() {
		return titleId;
	}

	public void setTitleId(Integer titleId) {
		this.titleId = titleId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Date getFirstVisit() {
		return firstVisit;
	}

	public void setFirstVisit(Date firstVisit) {
		this.firstVisit = firstVisit;
	}

	public Date getPreviousVisit() {
		return previousVisit;
	}

	public void setPreviousVisit(Date previousVisit) {
		this.previousVisit = previousVisit;
	}

	public Date getLastVisits() {
		return lastVisits;
	}

	public void setLastVisits(Date lastVisits) {
		this.lastVisits = lastVisits;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsonline() {
		return isonline;
	}

	public void setIsonline(Integer isonline) {
		this.isonline = isonline;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public List<SystemRole> getRoles() {
		return roles;
	}

	public List<SystemMenu> getMenus() {
		return menus;
	}

	public void setRoles(List<SystemRole> roles) {
		this.roles = roles;
	}

	public void setMenus(List<SystemMenu> menus) {
		this.menus = menus;
	}

	public String getIsemployee() {
		return isemployee;
	}
	public void setIsemployee(String isemployee) {
		this.isemployee = isemployee;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getModifyer() {
		return modifyer;
	}

	public void setModifyer(String modifyer) {
		this.modifyer = modifyer;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public void preInsert() {
		super.preInsert();
		this.status=ENABLED;
	}
	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype == null ? null : ostype.trim();
	}

	public String getBrowsertype() {
		return browsertype;
	}

	public void setBrowsertype(String browsertype) {
		this.browsertype = browsertype == null ? null : browsertype.trim();
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
}
