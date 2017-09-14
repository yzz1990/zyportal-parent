package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.AreaAdministrationareaB;

import java.util.List;

/**
 * Created by admin on 2017/5/10.
 */
public interface AreaAdministrationareaBService  {
    //插入行政区域信息
    CodeObject InsertArea(AreaAdministrationareaB area);
    //根据ID删除行政区域信息
    CodeObject DeleteById(String ID);
    //根据ID更新行政区域的信息
    CodeObject UpdateById(AreaAdministrationareaB area);
    //根据ID查询行政区域信息
    AreaAdministrationareaB SelectById(String ID);
    //查询所有行政区域信息
    PageInfo SelectAll(int currentPage, int pageSize,String city);
    //查询区域信息 distinct
    List<AreaAdministrationareaB> selectAreas();

}
