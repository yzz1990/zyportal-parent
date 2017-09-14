package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.ImDevelopAccount;
import com.zkzy.zyportal.system.api.entity.SocialContacts;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.exception.SocialException;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.SocialService;
import com.zkzy.zyportal.system.api.util.CcpRestHandler;
import com.zkzy.zyportal.system.api.util.EncryptUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13 0013.
 */
@Validated
@RestController
@Api(tags = "IM平台接口")
@RequestMapping("/social")
@ApiModel(value = "social")
public class SocialController extends BaseController {

    /**
     * 2017/7/25
     * 之前使用voipAccount+voipPassword方式登录API平台，需将对应的详细信息返回前端
     * 现改用自定义方式登录API平台，扔使用之前的创建子账号方式创建账号，使用voipAccount+SIg方式返回前端
     */


    @Autowired
    private ISystemUserService iSystemUserService;


    @Autowired
    private SocialService socialService;

    CcpRestHandler ccpRestHandler = new CcpRestHandler();


    @PostMapping(value = "/getContacts", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取通讯录")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> getContacts() {

        Map<String, List<HashMap<String, Object>>> contacts = socialService.getContacts();
        Map<String, Object> result = makeMessage(ReturnCode.SUCCESS);
        result.put("data", contacts);
        return result;


    }

    @PostMapping(value = "/seachContacts", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "搜索联系人")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> seachContacts(
            @RequestParam(name = "name", required = true, defaultValue = "") String name
    ) {

        List<SocialContacts> cs = socialService.seachContacts(name);
        HashMap<String, List<SocialContacts>> contacts = new HashMap<>();
        contacts.put("搜索的联系人",cs);
        Map<String, Object> result = makeMessage(ReturnCode.SUCCESS);
        result.put("data", contacts);
        return result;


    }

    @PostMapping(value = "/getServerInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取服务信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> getServerInfo() {
        Map<String, Object> result = makeMessage(ReturnCode.SUCCESS);
        result.put("app_id", socialService.getAppId());
//        result.put("app_token", CcpRestHandler.getAppToken());
//        result.put("base_url", ccpRestHandler.getBASEURL());
        return result;
    }

    @PostMapping(value = "/getSig", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取SIG")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> getSig(
            @RequestParam(name = "account", required = true, defaultValue = "") String account,
            @RequestParam(name = "timestamp", required = true, defaultValue = "") String timestamp
    ) {
        Map<String, Object> result = makeMessage(ReturnCode.SUCCESS);
        try {
            String signature = socialService.getSig(account, timestamp);
            result.put("signature", signature);
        } catch (Exception e) {
            result = makeMessage(ReturnCode.FAILED);
        }
        return result;
    }

    /**
     * {"id":"cb9c032ac49b4e3190d56677b692ed48","systemUserId":"18a42c1b2339483ca5167d54f2c60aa6",
     * "statusCode":"","subAccountSid":"","subToken":"","dateCreated":"2017-07-25 13:44:04",
     * "voipAccount":"","voipPwd":"","friendlyname":"张柏芝","photo":"","status":"","disabled":"0",
     * "shuoshuo":"管理员","account":"u_zbz20170725"}
     *
     * @param userId
     * @return
     */

    @PostMapping(value = "/loginSubAccount", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "IM子账号登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> loginSubAccount(
            @RequestParam(name = "userId", required = true, defaultValue = "") String userId) {
        Map<String, Object> result = null;
        HashMap<String, Object> socialContacts = socialService.loginAccount(userId);
        if (socialContacts != null) {
            result = makeMessage(ReturnCode.SUCCESS);
            result.put("data", socialContacts);
        } else {
            result = makeMessage(ReturnCode.NULL_OBJECT);
        }
        return result;
    }

    @PostMapping(value = "/getDevelopAccount", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取开发者账号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> getDevelopAccount() {
        Map<String, Object> result = makeMessage(ReturnCode.SUCCESS);
        result.put("data", socialService.getImDevelopAccount());
        return result;
    }

    @PostMapping(value = "/updateDevelopAccount", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新开发者账号信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> updateDevelopAccount(
            @ModelAttribute("imDevelopAccount") ImDevelopAccount imDevelopAccount
    ) {
        Map<String, Object> result = null;

        int i = socialService.saveImdevelopAccount(imDevelopAccount);
        if (i > 0) {
            result = makeMessage(ReturnCode.SUCCESS);
        } else {
            result = makeMessage(ReturnCode.FAILED);
        }
        return result;
    }


}
