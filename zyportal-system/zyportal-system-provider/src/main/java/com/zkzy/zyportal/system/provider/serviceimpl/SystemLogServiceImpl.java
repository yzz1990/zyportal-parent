package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.portal.common.utils.LogBack;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.service.SystemLogService;
import com.zkzy.zyportal.system.api.viewModel.LogPolyline;
import com.zkzy.zyportal.system.api.viewModel.LogTop;
import com.zkzy.zyportal.system.api.viewModel.UserLoginLog;
import com.zkzy.zyportal.system.provider.mapper.SystemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@Service("systemLogServiceImpl")
public class SystemLogServiceImpl implements SystemLogService {
    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public List<LogPolyline> queryLogGroupByDate(LogPolyline logPolyline) {
        List <LogPolyline> list=systemLogMapper.queryLogGroupByDate(logPolyline);
        return list;
    }

    @Override
    public List<LogPolyline> queryAllLogGroupByPermission() {
        List <LogPolyline> list=systemLogMapper.queryAllLogGroupByPermission();
        return list;
    }

    @Override
    public List<UserLoginLog> queryUserLoginLog(UserLoginLog userLoginLog) {
        List <UserLoginLog> list=systemLogMapper.queryUserLoginLog(userLoginLog);
        return list;
    }

    @Override
    public List<LogTop> queryMenuTop(String sql) {
        List <LogTop> list=systemLogMapper.queryMenuTop(sql);
        StringBuilder sb=new StringBuilder("");
        if(list.size()>0){
             for(LogTop lt:list){
                sb.append("'"+lt.getPermission()+"'"+",");
             }
        }
        String sbs="";
        if(sb.length()>0&&sb.toString().endsWith(",")){
            sbs=sb.substring(0,sb.length()-1);
        }
        String sq=" and PERMISSION not in("+sbs+")";
        int other_count=systemLogMapper.queryMenuTopOther(sq);
        LogTop ltt=new LogTop();
        ltt.setPermission("other");
        ltt.setCount(String.valueOf(other_count));
        ltt.setName("其他");
        ltt.setUrl("/other");
        list.add(ltt);
        return list;
    }
}
