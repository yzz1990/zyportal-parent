package com.zkzy.zyportal.system.provider.config;

import com.zkzy.zyportal.system.api.util.CCPSendSMSUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wangzl on 2017/7/26.
 */
@Configuration
@ConfigurationProperties(prefix="sms")
public class SmsConfig {
    //初始化静态变量
    private static String serverIP;
    private static String serverPort;
    private static String accountSid;
    private static String accountToken;
    private static String appId;
    private static String templateId;

    public static String getServerIP() {
        return serverIP;
    }

    public static void setServerIP(String serverIP) {
        SmsConfig.serverIP = serverIP;
    }

    public static String getServerPort() {
        return serverPort;
    }

    public static void setServerPort(String serverPort) {
        SmsConfig.serverPort = serverPort;
    }

    public static String getAccountSid() {
        return accountSid;
    }

    public static void setAccountSid(String accountSid) {
        SmsConfig.accountSid = accountSid;
    }

    public static String getAccountToken() {
        return accountToken;
    }

    public static void setAccountToken(String accountToken) {
        SmsConfig.accountToken = accountToken;
    }

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        SmsConfig.appId = appId;
    }

    public static String getTemplateId() {
        return templateId;
    }

    public static void setTemplateId(String templateId) {
        SmsConfig.templateId = templateId;
    }

    public static void initSMS() {
        CCPSendSMSUtil.initSMS(serverIP, serverPort, accountSid, accountToken, appId, templateId);
    }

}

