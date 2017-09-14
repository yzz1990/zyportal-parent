package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.web.security.AbstractTokenUtil;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemIp;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.viewModel.BrowserType;
import com.zkzy.zyportal.system.api.viewModel.OsType;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Validated
@RestController
@RequestMapping("/system/user")
@Api(tags = "系统用户管理")
@ApiModel(value = "systemUser")
public class SystemUserController extends BaseController {

    /**
     * 系统用户服务
     */
    @Autowired
    private ISystemUserService systemUserService;

    @Autowired
    private RedisRepository redisRepository;


    /**
     * 获取用户列表
     */
    @PreAuthorize("hasAuthority('userList')")
    @PostMapping(value = "userList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取用户列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> list(
//                          @RequestParam(name = "sqlParam",required = false) String sqlParam,
                          @ModelAttribute SystemUser systemUser,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize,
                          @RequestParam(name = "orgids",required = true,defaultValue = "")String orgids,
                          @RequestParam(name = "roleids",required = true,defaultValue = "")String roleids){
        GridModel grid = new GridModel();

        try {
            String param="";
            if(systemUser != null){
                if(systemUser.getUsername()!=null && systemUser.getUsername().trim().length()>0){
                    param+=" and username like '%"+systemUser.getUsername()+"%' ";
                }
                if(systemUser.getRealname()!=null && systemUser.getRealname().trim().length()>0){
                    param+=" and realname like '%"+systemUser.getRealname()+"%' ";
                }
                if(systemUser.getStatus()!=null && systemUser.getStatus().trim().length()>0){
                    param+=" and status='"+systemUser.getStatus()+"' ";
                }
            }

            PageInfo pageInfo = systemUserService.userList(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize),orgids,roleids);
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        }catch (Exception e){
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,grid);
        }
//        return grid;
    }

    @PreAuthorize("hasAuthority('user_add')")
    @PostMapping(value = "addUserInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增用户基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addUserBaseInfo(@ModelAttribute SystemUser systemUser) throws InvalidCaptchaException {
        return makeMessage(systemUserService.addUserInfo(systemUser));
    }

    @PreAuthorize("hasAuthority('user_edit')")
    @PostMapping(value = "updateUserInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑用户基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUserBaseInfo(@ModelAttribute SystemUser systemUser) throws InvalidCaptchaException {
        return makeMessage(systemUserService.updateUserInfo(systemUser));
    }

    @PreAuthorize("hasAuthority('user_prohibit')")
    @PostMapping(value = "prohibitUserInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "禁用/启用 用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> prohibitUserInfo(@ModelAttribute SystemUser systemUser) throws InvalidCaptchaException {
        return makeMessage(systemUserService.updateUserInfo(systemUser));
    }

    @PreAuthorize("hasAuthority('user_del')")
    @PostMapping(value = "delUserInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除用户基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delUserBaseInfo(@ModelAttribute SystemUser systemUser){
        return makeMessage(systemUserService.delUserInfo(systemUser));
    }





//----------------------------------------------------------------------------------------------------------------------


    @PostMapping(value = "/registry", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "创建用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> registryUser(
            @Pattern(regexp = "^[0-9a-zA-Z_]{3,16}$", message = "用户名格式错误,允许3-16位数字、字母、下划线")
            @ApiParam(required = true, value = "用户名") @RequestParam("userName") String userName,
            @Length(max = 21, min=6,message = "密码格式错误,允许6-21位密码")
            @ApiParam(required = true, value = "密码") @RequestParam("password") String password,
            @ApiParam(value = "姓名") @RequestParam(name="realName",required=false)  String realName,
            @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式错误")
            @ApiParam(value = "邮箱") @RequestParam(name="email",required = false) String email,
            @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
            @ApiParam(value = "手机号") @RequestParam(name="mobile",required = false) String mobile,
            @ApiParam(value = "组织id") @RequestParam(name="organizeId",required = false) String organizeId,
            @ApiParam(value = "组织名称") @RequestParam(name="organizeName",required = false) String organizeName,
            @ApiParam(value = "描述") @RequestParam(name="description",required = false) String description,
            HttpServletRequest request
            ) throws InvalidCaptchaException {
        SystemUser user = new SystemUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setRealname(realName);
        user.setEmail(email);
        user.setTel(mobile);
        user.setOrganizeId(organizeId);
        user.setOrganizeName(organizeName);
        user.setDescription(description);
        // 注册

        return makeMessage(systemUserService.registryUser(user));
    }


    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateUser(
            @Pattern(regexp = "^[0-9a-zA-Z_]{3,16}$", message = "用户名格式错误,允许3-16位数字、字母、下划线")
            @ApiParam(required = true, value = "用户名") @RequestParam("userName") String userName,
            @Length(max = 21, min=6,message = "密码格式错误,允许6-21位密码")
            @ApiParam(required = true, value = "密码") @RequestParam("password") String password,
            @ApiParam(value = "姓名") @RequestParam(name="realName",required=false)  String realName,
            @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式错误")
            @ApiParam(value = "邮箱") @RequestParam(name="email",required = false) String email,
            @Pattern(regexp = "1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}", message = "手机号码格式错误")
            @ApiParam(value = "手机号") @RequestParam(name="mobile",required = false) String mobile,
            @ApiParam(value = "组织id") @RequestParam(name="organizeId",required = false) String organizeId,
            @ApiParam(value = "组织名称") @RequestParam(name="organizeName",required = false) String organizeName,
            @ApiParam(value = "描述") @RequestParam(name="description",required = false) String description,
            HttpServletRequest request
    ) throws InvalidCaptchaException {
        if(userName.equals("root")){
            return makeMessage(ReturnCode.NO_AUTHORITY);
        }
        SystemUser user = new SystemUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setRealname(realName);
        user.setEmail(email);
        user.setTel(mobile);
        user.setOrganizeId(organizeId);
        user.setOrganizeName(organizeName);
        user.setDescription(description);
        user.setStatus(SystemUser.ENABLED);
        return makeMessage(systemUserService.updateInfo(user));
    }

    @GetMapping(value = "/getBasic", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据Token，获取用户信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getUser() {
        AuthUser user = WebUtils.getCurrentUser();
        SystemUser systemUser = systemUserService.getUserBasicInfoByName(user.getLoginName());
        if(systemUser==null){
            return makeMessage(ReturnCode.USER_NOT_EXIST);
        }else{
            Map<String,Object>message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA, systemUser);
            return message;
        }
    }


    @PostMapping(value = "/del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据id，删除用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delUser(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id
    ) {
        try {
            if(id.equals("1")){
                return makeMessage(ReturnCode.NO_AUTHORITY);
            }
            String userName=systemUserService.getUserById(id).getUsername();
            systemUserService.deleteUserById(id);
            redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH+userName);
            redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER+userName);
            return makeMessage(systemUserService.freezeUser(id));
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }


    @PostMapping(value = "/freeze", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "冻结用户")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )

    public Map<String, Object> freezeUser(
            @ApiParam(required = true, value = "username") @RequestParam("username") String username
    ) {
        try {
            if(username.equals("root")){
                return makeMessage(ReturnCode.NO_AUTHORITY);
            }
            Map<String, Object> message=makeMessage(systemUserService.freezeUser(username));
            redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH+username);
            redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER+username);
            return message;
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }


    @GetMapping(value = "/findList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "模糊分页查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> findList(
            @ApiParam(value = "用户名") @RequestParam(name="userName",required = false) String userName,
            @ApiParam(value = "姓名") @RequestParam(name="realName",required=false)  String realName,
            @ApiParam(value = "邮箱") @RequestParam(name="email",required = false) String email,
            @ApiParam(value = "手机号") @RequestParam(name="mobile",required = false) String mobile,
            @ApiParam(value = "组织名称") @RequestParam(name="organizeName",required = false) String organizeName,
            @ApiParam(value = "描述") @RequestParam(name="description",required = false) String description,
            @ApiParam(value = "页码",required = true,defaultValue = "1") @RequestParam(name="pageNum") int pageNum,
            @ApiParam(value = "页面大小",required = true,defaultValue = "10") @RequestParam(name="pageSize") int pageSize,
            @ApiParam(value = "排序",required = true,defaultValue = "UPDATE_DATE") @RequestParam(name="orderBy") String orderBy
    ) {
        SystemUser user = new SystemUser();
        user.setUsername(userName);
        user.setRealname(realName);
        user.setEmail(email);
        user.setTel(mobile);
        user.setOrganizeName(organizeName);
        user.setDescription(description);
        user.setStatus(SystemUser.ENABLED);
        Paging page=new Paging();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setOrderBy(orderBy);
        PageInfo pageInfo=systemUserService.findUserList(page,user);
        Map<String,Object>message=null;
        try {
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,pageInfo);
        }catch (Exception e){
            message=makeMessage(ReturnCode.UNKNOWN_ERROR);
        }

        return message;
    }

    @PreAuthorize("hasAuthority('getAllUserNum')")
    @PostMapping(value = "/getAllUserNum", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有用户人数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getAllUserNum() {
        Integer num = systemUserService.getAllUserNum();
        Map<String, Object> message=null;
        try {
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,num);
            return message;
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('getSexNum')")
    @PostMapping(value = "/getSexNum", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询男女人数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getSexNum() {
        Integer malenum = systemUserService.getSexNum(0);
        Integer femalenum = systemUserService.getSexNum(1);
        Map<String, Object> message=null;
        try {
            message=makeMessage(ReturnCode.SUCCESS);
            message.put("male",malenum);
            message.put("female",femalenum);
            return message;
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('getAgeNum')")
    @PostMapping(value = "/getAgeNum", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询年龄分布人数")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getAgeNum() {
        InputStream in = this.getClass().getResourceAsStream("/user.properties");
        Properties p = new Properties();
        try {
            p.load(in);
            String[] splitAge = p.getProperty("splitAge").split(",");
            Map<String, Object> message=null;
            message=makeMessage(ReturnCode.SUCCESS);
            for(int i = 0; i<splitAge.length-1; i++){
                Integer num = systemUserService.getAgeNum(Integer.parseInt(splitAge[i]),Integer.parseInt(splitAge[i+1]));
                message.put(Message.RETURN_FIELD_DATA+i,num);
            }
            return message;
        } catch(Exception e){
            return makeMessage(ReturnCode.UNKNOWN_ERROR);
        }
    }


    @PostMapping(value = "/getOsType", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询用户操作系统版本")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public  Map<String,Object> selectAllOsTypes(){
        List<OsType> list=systemUserService.selectAllOsTypes();
        if (list != null) {
            return makeMessage(ReturnCode.SUCCESS, list);
        } else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR, null);
        }
    }

    @PostMapping(value = "/getAllBrowsers", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询用户浏览器类型和版本")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public  Map<String,Object> SelectAllBrowsers(){
        Map<String,Object> browsers=new HashedMap();
        List<BrowserType> inner=systemUserService.selectBrowInnerTypes();
        if(inner!=null){
            browsers.put("inner",inner);
        }
        List<BrowserType> outer=systemUserService.selectBrowOuterTypes();
        if(outer!=null){
            browsers.put("outer",outer);
        }
        if (browsers != null) {
            return makeMessage(ReturnCode.SUCCESS, browsers);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR, null);
        }
    }
    @PostMapping(value = "/updateLocation", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "H5更新用户坐标")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
    })
    public Map<String, Object> updateLocation(
            @RequestParam(name = "longitude", required = true, defaultValue = "") String longitude,
            @RequestParam(name = "latitude", required = true, defaultValue = "") String latitude) {
        Map<String, Object> map;
        AuthUser user = WebUtils.getCurrentUser();
        int i = systemUserService.updateLNGLAT(user.getUsername(),longitude,latitude);
        if (i > 0) {
            map = makeMessage(ReturnCode.SUCCESS);
        } else {
            map = makeMessage(ReturnCode.FAILED);
        }
        return map;
    }

}
