package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemIp;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.entity.SystemMenu;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface SystemMenuService {
    List<ZtreeSimpleView> getMenuZTree(String roleId);
    CodeObject saveRoleMenu(String roleId,String permissionid);
    void saveLog(SystemLog systemLog);
    List<SystemLog> queryLog();
    void saveIp(SystemIp systemIp);
    void updateTime(SystemIp systemIp);
    PageInfo<SystemIp> queryIp(Paging page,SystemIp systemIp);
    SystemIp queryIpAll(SystemIp systemIp);
    void bannedIP(SystemIp systemIp);
    Map<String,Object> findParamByUserId(String userId, String permission, String username);
    List<SystemIp> queryAllSystemIp();
}
