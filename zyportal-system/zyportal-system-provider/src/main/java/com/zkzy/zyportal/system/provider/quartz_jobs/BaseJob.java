package com.zkzy.zyportal.system.provider.quartz_jobs;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/30 0030.
 * Base Job   job公共属性
 */
public class BaseJob implements Job{

    @Autowired
    private Scheduler scheduler;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(this.getClass().getName()+"正在执行"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss "));
    }


}
