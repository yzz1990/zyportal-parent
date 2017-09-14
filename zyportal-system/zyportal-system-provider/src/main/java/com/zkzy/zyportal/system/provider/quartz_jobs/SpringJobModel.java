package com.zkzy.zyportal.system.provider.quartz_jobs;

import org.quartz.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/6/19 0019.
 * quartz运行  bean对象
 */

@Component
@DisallowConcurrentExecution
public class SpringJobModel extends BaseJob implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static String SRPTING_BEAN_NAME = "beanName";

    public static String SRPTING_METHOD_NAME = "methodName";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        //System.out.println(CommonUtils.getNowFullDateString()+"执行定时任务"+data.getString(SRPTING_BEAN_NAME)+"方法"+data.getString(SRPTING_METHOD_NAME));
        try {
            invokeMethod(data.getString(SRPTING_BEAN_NAME),data.getString(SRPTING_METHOD_NAME), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void invokeMethod(String owner, String methodName, Object[] args)
            throws Exception {

//        Object ownerClass = ContextLoader.getCurrentWebApplicationContext().getBean(owner);
        Object ownerClass = getApplicationContext().getBean(owner);

        Method method = ownerClass.getClass().getMethod(methodName, null);
        method.invoke(ownerClass, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
