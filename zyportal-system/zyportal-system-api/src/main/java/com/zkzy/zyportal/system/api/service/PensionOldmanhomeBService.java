package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.PensionOldmanhomeB;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface PensionOldmanhomeBService {
    //插入养老院信息
    CodeObject InsertOldmanHome(PensionOldmanhomeB home);
    //根据ID删除养老院信息
    CodeObject DeleteById(String ID);
    // 更新养老院信息
    CodeObject UpdateById(PensionOldmanhomeB home);
    //根据ID查询养老院信息
    PensionOldmanhomeB  SelectById(String ID);
    //查询所有养老院信息(分页)
    PageInfo  SelectAll(int currentPage,int pageSize,String param);
    //查询所有养老院ID和名称(无分页)
    List<PensionOldmanhomeB> SelectName();


}
