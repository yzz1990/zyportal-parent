package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import com.zkzy.zyportal.system.api.viewModel.SimilarMap;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import com.zkzy.zyportal.system.provider.mapper.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/6/26 0026.1
 */
@Service("systemMenuServiceImpl")
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;
    @Autowired
    private SystemLogMapper systemLogMapper;
    @Autowired
    private SystemIpMapper systemIpMapper;

    @Override
    public List<ZtreeSimpleView> getMenuZTree(String roleId) {
        List<ZtreeSimpleView> treeList = new ArrayList<ZtreeSimpleView>();
        //所有的systemmenu
        List<SystemMenu> meunList = systemMenuMapper.findAllList();
        for (SystemMenu menu : meunList) {
            String permissionId = menu.getId() == null ? null : menu.getId().toString();
            if (permissionId != null) {
                ZtreeSimpleView ztreeSimpleView = new ZtreeSimpleView();

                ztreeSimpleView.setId(permissionId);
                ztreeSimpleView.setpId(menu.getParentId() == null ? null : menu.getParentId().toString());
                ztreeSimpleView.setName(menu.getName() == null ? null : menu.getName());
                //该节点是否被选中
                List roleMenuList = systemRoleMenuMapper.selectAll(" and ROLE_ID ='" + roleId + "' and PERMISSION_ID='" + permissionId + "' ");
                ztreeSimpleView.setChecked(roleMenuList.size() > 0);
                ztreeSimpleView.setValue(menu.getPermission());
                ztreeSimpleView.setOpen(true);
                treeList.add(ztreeSimpleView);
            }
        }

        return treeList;
    }

    @Override
    public CodeObject saveRoleMenu(String roleId, String permissionid) {
        try {
            if (roleId != null) {
                //先删除原来的
                systemRoleMenuMapper.deleteByRoleId(roleId);
                //新增新的
                if (permissionid != null && permissionid.trim().length() > 0) {
                    String[] permissionIds = permissionid.split(",");
                    for (int i = 0; i < permissionIds.length; i++) {
                        if (permissionIds[i].trim().length() > 0) {
                            SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
                            systemRoleMenu.setId(RandomHelper.uuid());
                            systemRoleMenu.setRoleId(roleId);
                            systemRoleMenu.setPermissionId(Long.valueOf(permissionIds[i]));
                            systemRoleMenu.setStatus("1");

                            Date date = new Date();
                            systemRoleMenu.setCreateDate(date);
                            systemRoleMenu.setUpdateDate(date);

                            systemRoleMenuMapper.insert(systemRoleMenu);
                        }
                    }
                }


            } else {
                return ReturnCode.FAILED;//失败
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnCode.FAILED;//失败
        }
        return ReturnCode.SUCCESS;//成功
    }

    public void saveLog(SystemLog systemLog) {
        try {
            systemLogMapper.insert(systemLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<SystemLog> queryLog() {
        List<SystemLog> list = systemLogMapper.selectAll();
        return list;
    }

    @Override
    public void saveIp(SystemIp systemIp) {
        try {
            systemIpMapper.insert(systemIp);
        } catch (Exception e) {
            systemIpMapper.updateByPrimaryKey(systemIp);
        }

    }

    @Override
    public void updateTime(SystemIp systemIp) {
        systemIpMapper.updateTime(systemIp);
    }

    @Override
    public PageInfo<SystemIp> queryIp(Paging page, SystemIp systemIp) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<SystemIp> list = systemIpMapper.selectAll(systemIp);
            return new PageInfo<>(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SystemIp queryIpAll(SystemIp systemIp) {
        try {
            return systemIpMapper.selectByPrimaryKey(systemIp.getIp());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void bannedIP(SystemIp systemIp) {
        systemIpMapper.updateStatus(systemIp);
    }

    @Override
    public Map<String, Object> findParamByUserId(String userId, String permission, String username) {
        int type = 5;
        Map<String, Object> newMap = new LinkedHashMap();
        if (SysUser.ADMIN_USER_ID.equals(userId)) {
            List<SystemMenu> list = systemMenuMapper.findAllParam(userId, permission, String.valueOf(type));
            for (int i = 0; i < list.size(); i++) {
                String perm = list.get(i).getPermission();
                String name = list.get(i).getName();
                List<SystemMenu> list2 = systemMenuMapper.findAllParam(userId, perm, String.valueOf(type + 1));
                newMap.put(name, list2);
            }
            return newMap;
        } else {
            List<SystemMenu> list = systemMenuMapper.findParamByUserId(userId, permission, String.valueOf(type));
            for (int i = 0; i < list.size(); i++) {
                String perm = list.get(i).getPermission();
                String name = list.get(i).getName();
                List<SystemMenu> list2 = systemMenuMapper.findParamByUserId(userId, perm, String.valueOf(type + 1));
                newMap.put(name, list2);
            }
            return newMap;
        }

    }

    @Override
    public List<SystemIp> queryAllSystemIp() {
        return systemIpMapper.queryAllnoWhere();
    }
}


