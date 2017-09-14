package com.zkzy.zyportal.system.provider.config.quartz;

import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/6/16 0016.
 * Quartz配置
 *
 * JobListener、Triggerlistener、SchedulerListener
 * JobListener 和 TriggerListener 可被注册为全局或非全局监听器。
 * 一个全局监听器能接收到所有的 Job/Trigger 的事件通知。
 * 而一个非全局监听器(或者说是一个标准的监听器) 只能接收到那些在其上已注册了监听器的 Job 或 Triiger 的事件。
 */
@Component
@ConfigurationProperties(prefix="spring.datasource.write") //application.yml中的myProps下的属性
public class QuartzConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @Autowired
    private JobListener jobListener;
    @Autowired
    private Triggerlistener triggerlistener;
    @Autowired
    private SchedulerListener schedulerListener;


    @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
        Scheduler scheduler = schedulerFactory.getScheduler();
        //事件监听
        ListenerManager listenerManager=scheduler.getListenerManager();
        listenerManager.addJobListener(jobListener);
        listenerManager.addTriggerListener(triggerlistener);
        listenerManager.addSchedulerListener(schedulerListener);
        scheduler.start();
        return scheduler;
    }



    /**
     * 设置quartz属性
     * @return
     * @throws IOException
     */
    public Properties quartzProperties() throws IOException {
//        System.out.println("QuartConfig设置属性:username>>>"+this.driverClassName);
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.scheduler.batchTriggerAcquisitionMaxCount", "20");

        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "20");


        prop.put("org.quartz.dataSource.quartzDataSource.driver", this.driverClassName);
        prop.put("org.quartz.dataSource.quartzDataSource.URL", this.url);
        prop.put("org.quartz.dataSource.quartzDataSource.user", this.username);
        prop.put("org.quartz.dataSource.quartzDataSource.password", this.password);
        prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "20");
        return prop;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}
