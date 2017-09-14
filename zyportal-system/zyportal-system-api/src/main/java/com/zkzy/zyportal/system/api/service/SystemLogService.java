package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.viewModel.LogPolyline;
import com.zkzy.zyportal.system.api.viewModel.LogTop;
import com.zkzy.zyportal.system.api.viewModel.UserLoginLog;

import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface SystemLogService {
    List<LogPolyline> queryLogGroupByDate(LogPolyline logPolyline);
    List<LogPolyline> queryAllLogGroupByPermission();
    List<UserLoginLog> queryUserLoginLog(UserLoginLog userLoginLog);

    List<LogTop> queryMenuTop(String sql);
}
