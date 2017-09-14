package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;

import com.zkzy.zyportal.system.api.entity.SystemParameter;

import java.util.List;

/**
 * Created by admin on 2017/6/19.
 */
public interface ParameterService {
    PageInfo selectAll(int currentPage, int pageSize,String sqlParam);
    PageInfo selectGroup(int currentPage, int pageSize);
    CodeObject insertParam(SystemParameter parameter);
    CodeObject updateParamById(SystemParameter parameter);
    CodeObject delparamById(SystemParameter parameter);
    SystemParameter seleclByMyId(String Id);
 }
