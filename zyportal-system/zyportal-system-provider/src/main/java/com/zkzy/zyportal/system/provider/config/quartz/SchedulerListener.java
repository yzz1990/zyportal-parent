package com.zkzy.zyportal.system.provider.config.quartz;

import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

@Component
public class SchedulerListener implements org.quartz.SchedulerListener{

    /**
     * 部署新的job
     * @param trigger
     */
    @Override
    public void jobScheduled(Trigger trigger) {
        System.out.println(this.getClass().getName()+">>>"+trigger.getDescription()+"已部署");
    }

    /**
     * 卸载job
     * @param triggerKey
     */
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println(this.getClass().getName()+">>>"+triggerKey+"已卸载");
    }

    /**
     * 当一个 Trigger 来到了再也不会触发的状态时调用这个方法。除非这个 Job 已设置成了持久性，否则它就会从 Scheduler 中移除
     * @param trigger
     */
    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    /**
     * 调用这个方法是发生在一个 Trigger 或 Trigger 组被暂停时。假如是 Trigger 组的话，triggerName 参数将为 null。
     */
    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println(this.getClass().getName()+">>>"+triggerKey+"已暂停");
    }

    @Override
    public void triggersPaused(String triggerGroup) {
        System.out.println(this.getClass().getName()+">>>"+triggerGroup+"已暂停");
    }

    /**
     * 调用这个方法是发生成一个 Trigger 或 Trigger 组从暂停中恢复时。假如是 Trigger 组的话，triggerName 参数将为 null。
     */
    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println(this.getClass().getName()+">>>"+triggerKey+"已重启");
    }

    @Override
    public void triggersResumed(String triggerGroup) {
        System.out.println(this.getClass().getName()+">>>"+triggerGroup+"已重启");

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(this.getClass().getName()+jobDetail.getDescription()+">>>jobAdded");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(this.getClass().getName()+jobKey+">>>jobDeleted");
    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println(this.getClass().getName()+jobKey+">>>jobPaused");
    }

    @Override
    public void jobsPaused(String jobGroup) {
        System.out.println(this.getClass().getName()+jobGroup+">>>jobsPaused");
    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println(this.getClass().getName()+jobKey+">>>jobResumed");

    }

    @Override
    public void jobsResumed(String jobGroup) {
        System.out.println(this.getClass().getName()+jobGroup+">>>jobsResumed");

    }

    @Override
    public void schedulerError(String msg, SchedulerException cause) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {

    }

    @Override
    public void schedulerStarting() {

    }

    @Override
    public void schedulerShutdown() {

    }

    @Override
    public void schedulerShuttingdown() {

    }

    @Override
    public void schedulingDataCleared() {

    }

}
