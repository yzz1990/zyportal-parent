package com.zkzy.portal.common.web.logFilter;

import com.zkzy.portal.common.web.util.IpUtils;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.service.SystemMenuService;

/**
 * Created by Administrator on 2017/7/6.
 */
public class LogDao implements Runnable{

    private SystemMenuService systemMenuService;
    private SystemLog systemLog;
    public LogDao(SystemMenuService s,SystemLog log){
        systemMenuService=s;
        systemLog=log;
    }
    @Override
    public void run(){
        //归属地
        //systemLog.setLocation(IpUtils.getJuheData(systemLog.getIp()).get(IpUtils.AREA));
        systemMenuService.saveLog(systemLog);
    }

}
