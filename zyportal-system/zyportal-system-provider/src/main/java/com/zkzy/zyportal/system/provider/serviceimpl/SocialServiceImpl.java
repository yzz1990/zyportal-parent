package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.ImDevelopAccount;
import com.zkzy.zyportal.system.api.entity.SocialContacts;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.service.SocialService;
import com.zkzy.zyportal.system.api.util.CcpRestHandler;
import com.zkzy.zyportal.system.api.util.EncryptUtil;
import com.zkzy.zyportal.system.provider.mapper.ImDevelopAccountMapper;
import com.zkzy.zyportal.system.provider.mapper.SocialContactsMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemOrganizationMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Administrator on 2017/7/13 0013.
 */
@Service("socialService")
public class SocialServiceImpl implements SocialService {


    @Autowired
    SocialContactsMapper socialContactsMapper;

    @Autowired
    SystemUserMapper systemUserMapper;

    @Autowired
    SystemOrganizationMapper systemOrganizationMapper;

    @Autowired
    ImDevelopAccountMapper imDevelopAccountMapper;


    CcpRestHandler c = new CcpRestHandler();

    @Override
    public String getSig(String account, String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        checkImServer();
        EncryptUtil eu = new EncryptUtil();
        String sig = c.APP_ID + account + timestamp + c.APP_TOKEN;
        return eu.md5Digest(sig);
    }

    /**
     * 2017-07-25修改返回值原socialcontacts   先map
     * {"id":"cb9c032ac49b4e3190d56677b692ed48","systemUserId":"18a42c1b2339483ca5167d54f2c60aa6",
     * "statusCode":"","subAccountSid":"","subToken":"","dateCreated":"2017-07-25 13:44:04",
     * "voipAccount":"","voipPwd":"","friendlyname":"张柏芝","photo":"","status":"","disabled":"0",
     * "shuoshuo":"管理员","account":"u_zbz20170725"}
     */

    @Override
    public HashMap<String, Object> loginAccount(String userid) {
        checkImServer();
        try {
            SocialContacts socialContacts = socialContactsMapper.selectByUserID(userid);
            //here
            return socialContacts2Map(socialContacts);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, List<HashMap<String, Object>>> getContacts() {
        checkImServer();
        List<SystemUser> users = systemUserMapper.userList(null);
        Map<String, List<HashMap<String, Object>>> resultMap = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            SystemUser systemUser = users.get(i);
            SocialContacts socialContacts = socialContactsMapper.selectByUserID(systemUser.getId());
            if (socialContacts != null) {
                socialContacts.setPhoto(systemUser.getPhotourl());
            }
            HashMap<String, Object> cell = socialContacts2Map(socialContacts);
            if (socialContacts != null) {
                if (resultMap.containsKey(systemUser.getOrganizeName())) {
                    resultMap.get(systemUser.getOrganizeName() == null ? "其他" : systemUser.getOrganizeName()).add(cell);
                } else {
                    List<HashMap<String, Object>> list = new ArrayList<>();
                    list.add(cell);
                    resultMap.put(systemUser.getOrganizeName() == null ? "其他" : systemUser.getOrganizeName(), list);
                }
            }

        }
        return resultMap;
    }

    @Override
    public List<SocialContacts> seachContacts(String name) {
        return socialContactsMapper.likeByFriendlyName(name);
    }

    //v1.0在API创建账号，持久化返回对象
    @Override
    public SocialContacts createSubAccount(String friendluName, SystemUser systemUser) {

        checkImServer();
        String jsonString = c.createSubAccount(friendluName);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);

        SocialContacts socialContacts = null;
        if (jsonObject.get("statusCode").equals("000000")) {
            socialContacts = new SocialContacts();

            JSONObject json = jsonObject.getJSONObject("SubAccount");
            socialContacts.setvoipAccount(json.getString("voipAccount"));
            socialContacts.setdateCreated(json.getString("dateCreated"));
            socialContacts.setvoipPwd(json.getString("voipPwd"));
            socialContacts.setsubToken(json.getString("subToken"));
            socialContacts.setsubAccountSid(json.getString("subAccountSid"));
            socialContacts.setstatusCode(jsonObject.getString("statusCode"));

            socialContacts.setId(RandomHelper.uuid());
            socialContacts.setSystemUserId(systemUser.getId());
            socialContacts.setFriendlyname(friendluName);
            socialContacts.setShuoshuo(systemUser.getRoleName());
            socialContacts.setDisabled(BigDecimal.valueOf(0));
            socialContacts.setPhoto(systemUser.getPhotourl());

            socialContactsMapper.insert(socialContacts);
        } else {
//            <Response><statusCode>111150</statusCode><statusMsg>【账号】子账户名称重复</statusMsg></Response>
//            throw new SocialException("exceptionCode : "+jsonObject.getString("statusCode") + " exceptionDesc : " + jsonObject.getString("statusMsg"));
        }
        return socialContacts;
    }

    //v1.1自定义登录名方式登录API 2017-07-25
    @Override
    public SocialContacts createAccount(String friendluName, SystemUser systemUser) {
        checkImServer();
        SocialContacts socialContacts = new SocialContacts();
//            JSONObject json = jsonObject.getJSONObject("SubAccount");
//            socialContacts.setvoipAccount(json.getString("voipAccount"));
        socialContacts.setdateCreated(DateHelper.formatDateTime(new Date()));
//            socialContacts.setvoipPwd(json.getString("voipPwd"));
//            socialContacts.setsubToken(json.getString("subToken"));
//            socialContacts.setsubAccountSid(json.getString("subAccountSid"));
//            socialContacts.setstatusCode(jsonObject.getString("statusCode"));

        socialContacts.setId(RandomHelper.uuid());
        socialContacts.setSystemUserId(systemUser.getId());
        socialContacts.setFriendlyname(friendluName);
        socialContacts.setShuoshuo(systemUser.getRoleName());
        socialContacts.setDisabled(BigDecimal.valueOf(0));
        socialContacts.setPhoto(systemUser.getPhotourl());

        String account = "u_" + systemUser.getUsername() + RandomHelper.getRandNum(4);

        socialContacts.setAccount(account);
        socialContactsMapper.insert(socialContacts);
        return socialContacts;
    }

    @Override
    public CodeObject closeSubAccount(String userId) {
        checkImServer();
        CodeObject rcode = null;
        SocialContacts socialContacts = socialContactsMapper.selectByUserID(userId);
        if (socialContacts != null) {
            String code = c.closeSubAccount(socialContacts.getsubAccountSid());
            System.out.println(code);
            JSONObject jsonObject = JSONObject.parseObject(code);
            if (jsonObject.get("statusCode").equals("000000")) {
                socialContactsMapper.deleteBySubAccountSid(socialContacts.getsubAccountSid());
                rcode = ReturnCode.SUCCESS;
            } else {
                rcode = new CodeObject((String) jsonObject.get("statusCode"), (String) jsonObject.get("statusMsg"));
            }
        } else {
            rcode = ReturnCode.NULL_OBJECT;
        }
        return rcode;
    }


    private HashMap<String, Object> socialContacts2Map(SocialContacts socialContacts) {
        if (socialContacts != null) {
            HashMap<String, Object> hmAccount = new HashMap<>();
            hmAccount.put("friendlyname", socialContacts.getFriendlyname());
            hmAccount.put("photo", socialContacts.getPhoto());
            hmAccount.put("status", socialContacts.getStatus());
            hmAccount.put("disabled", socialContacts.getDisabled());
            hmAccount.put("shuoshuo", socialContacts.getShuoshuo());
            hmAccount.put("account", socialContacts.getAccount());
            return hmAccount;
        } else {
            return null;
        }
    }

    @Override
    public int saveImdevelopAccount(ImDevelopAccount imDevelopAccount) {
        return imDevelopAccountMapper.updateByPrimaryKey(imDevelopAccount);
    }


    @Override
    public String getAppId() {
        checkImServer();
        return c.APP_ID;
    }

    public ImDevelopAccount checkImServer() {
        ImDevelopAccount imDevelopAccount = null;
        if (c.APP_ID == null || c.AUTH_TOKEN == null || c.BASEURL == null) {
            imDevelopAccount = imDevelopAccountMapper.selectInUse();
            c.initImServer(imDevelopAccount.getServerIp(),
                    "" + imDevelopAccount.getServerPort(),
                    imDevelopAccount.getSoftversion(),
                    imDevelopAccount.getBaseurl(),
                    imDevelopAccount.getAccountSid(),
                    imDevelopAccount.getAuthToken(),
                    imDevelopAccount.getAppId(),
                    imDevelopAccount.getAppToken());
        }
        return imDevelopAccount;
    }

    @Override
    public ImDevelopAccount getImDevelopAccount() {
        checkImServer();
        ImDevelopAccount imDevelopAccount = new ImDevelopAccount();
        imDevelopAccount.setAccountSid(c.ACCOUNT_SID);
        imDevelopAccount.setAuthToken(c.AUTH_TOKEN);
        imDevelopAccount.setAppId(c.APP_ID);
        imDevelopAccount.setAppToken(c.APP_TOKEN);
        imDevelopAccount.setBaseurl(c.BASEURL);
        imDevelopAccount.setServerIp(c.SERVER_IP);
        if (c.SERVER_PORT != null) {
            BigDecimal serport = new BigDecimal(c.SERVER_PORT);
            imDevelopAccount.setServerPort(serport);
        }

        imDevelopAccount.setSoftversion(c.SOFTVERSION);
        return imDevelopAccount;
    }

}
