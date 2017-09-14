package com.zkzy.zyportal.system.provider.config.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

@Component
public class Triggerlistener implements TriggerListener {



    public Triggerlistener() {
        super();
    }

    @Override
    public String getName() {
        return "Trigger监听";
    }

    /**     (1)
     * Trigger被激发 它关联的job即将被运行
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    /**    (2)
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * @param trigger
     * @param context
     * @return
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        return false;
    }

    /**
     * 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     *  那么有的触发器就有可能超时，错过这一轮的触发。
     * @param trigger
     */
    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    /**
     * 任务完成时触发
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }
}
