package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemEditor;

/**
 * Created by admin on 2017/6/26.
 */
public interface EditorService {
    PageInfo selectAll(int currentPage, int pageSize, String param);
    //插入新富文本编辑器
    CodeObject insertEditor(SystemEditor systemEditor);
    //根据ID查询富文本编辑器
    SystemEditor selectById (String ID);
    //根据Id更新
    CodeObject updateById(SystemEditor systemEditor);
    //根据I都删除
    CodeObject delByID(SystemEditor systemEditor);
}
