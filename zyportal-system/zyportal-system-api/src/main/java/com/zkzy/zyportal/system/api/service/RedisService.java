package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.entity.SystemOrganization;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public interface RedisService {
      String getParameterValueByCode(String code);
      String getSystemCodeListByCode(String code);
      String getSystemCodeNameByCode(String code);
      String getOrganizationListByCode(String code);
      List<SystemOrganization> getOrganizationListBeanByCode(String code);
      String getOrganizationNameByCode(String code);

    String getOnlineUserList(String param);
}
