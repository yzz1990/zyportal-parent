package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.EvaluationOrganization;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface EvaluationOrganizationService {
    //插入评测机构信息
    CodeObject InsertDepartment(EvaluationOrganization department);
    //根据ID删除评测机构信息
    CodeObject DeleteById(String ID);
    //根据ID更新评测机构的信息
    CodeObject UpdateById(EvaluationOrganization department);
    //根据ID查询评测机构信息
    EvaluationOrganization SelectById(String ID);
    //查询所有评测机构信息
    PageInfo SelectAll(int currentPage, int pageSize);
    //查询所有评测机构ID和名称(无分页)
    List<EvaluationOrganization> SelectName();
}
