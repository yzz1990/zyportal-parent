package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import com.zkzy.zyportal.system.api.viewModel.TreeModel;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */
public interface SystemOrganizationService {
    List<SystemOrganization> selectAll(String param);

    List<TreeModel> selectAllTree(String param);

    List<SystemOrganization> queryOrganizationchildListByMyId(String codeMyid);


    CodeObject saveOrganiza(SystemOrganization systemOrganization,String oldPermission);
    CodeObject delbyId(String id);
    SystemOrganization getOrganizaByUserId(String id);
}
