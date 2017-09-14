package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.ImDevelopAccount;
import com.zkzy.zyportal.system.api.entity.SocialContacts;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.exception.SocialException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public interface SocialService {

    Map<String, List<HashMap<String,Object>>> getContacts();

    SocialContacts createSubAccount(String friendlyName, SystemUser systemUser);

    CodeObject closeSubAccount(String userId);

    HashMap<String, Object> loginAccount(String userId);

    String getSig(String account,String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    SocialContacts createAccount(String friendlyName, SystemUser systemUser);

    int saveImdevelopAccount(ImDevelopAccount imDevelopAccount);

    String getAppId();

    ImDevelopAccount getImDevelopAccount();

    List<SocialContacts> seachContacts(String name);


}
