package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.PensionOldmaninfoB;

/**
 * Created by Administrator on 2017/5/8 0008.
 */
public interface OldManInfoService {
    PageInfo getOldManInfoList(String param,int currentPage,int pageSize);
    CodeObject save(PensionOldmaninfoB pensionOldmaninfoB,String userId);
    CodeObject update(PensionOldmaninfoB pensionOldmaninfoB,String userId);
    CodeObject delete(String delId);
}
