package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SmsWarnB;

public interface ISmsWarnService {

    /**
     * 新建短信预警
     *
     * @param warn 短信预警信息
     */
    CodeObject addWarn(SmsWarnB warn);

    /**
     * 修改短信预警
     *
     * @param Warn 短信预警信息
     */
    CodeObject updateWarn(SmsWarnB Warn);

    /**
     * 删除短信预警
     *
     * @param warn 短信预警信息
     */
    CodeObject deleteWarn(SmsWarnB warn);

    /**
     * 分页查询
     */
    PageInfo warnList(String sqlParam, Integer pageNumber, Integer pageSize);

}
