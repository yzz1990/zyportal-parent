package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.EvaluationPerson;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface EvaluationPersonService {
    //插入评测人员信息
    CodeObject InsertName(EvaluationPerson name);
    //根据ID删除评测人员信息
    CodeObject DeleteById(String ID);
    //根据ID更新评测人员的信息
    CodeObject UpdateById(EvaluationPerson name);
    //根据ID查询评测人员信息
    EvaluationPerson SelectById(String ID);
    //查询所有评测人员信息(分页)
    PageInfo SelectAll(int currentPage, int pageSize);
    //查询所有养老院ID和名称(无分页)
    List<EvaluationPerson> SelectName();
}
