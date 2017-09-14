package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SmsManB;

public interface ISmsManService {

    /**
     * 新建通讯人员
     *
     * @param man 通讯人员信息
     */
    CodeObject addMan(SmsManB man);

    /**
     * 修改通讯人员
     *
     * @param man 通讯人员信息
     */
    CodeObject updateMan(SmsManB man);

    /**
     * 删除通讯人员
     *
     * @param man 通讯人员信息
     */
    CodeObject deleteMan(SmsManB man);

    /**
     * 分页查询
     */
    PageInfo manList(String sqlParam, Integer pageNumber, Integer pageSize);

}
