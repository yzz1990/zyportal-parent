package com.zkzy.zyportal.system.provider.config.quartz;

import com.zkzy.portal.common.quartz.QuartzConstant;
import com.zkzy.zyportal.system.api.entity.JobB;
import com.zkzy.zyportal.system.provider.mapper.JobBMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/6/30 0030.
 */

@Component
public class JobListener implements org.quartz.JobListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobListener.class);

    @Autowired
    private JobBMapper jobBMapper;

    /**
     * getName() 方法返回一个字符串用以说明 JobListener 的名称
     * @return
     */
    @Override
    public String getName() {
        return "任务监听";
    }

    /**
     * Scheduler 在 JobDetail 将要被执行时调用这个方法
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
//        System.out.println(context.getJobDetail().getKey()+":JobDetail 将要被执行");
    }

    /**
     * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
//        System.out.println(context.getJobDetail().getKey()+":JobDetail即将被执行，但又被 TriggerListener 否决了");
        JobDetail jobDetail=context.getJobDetail();
        JobDataMap jobDataMap=jobDetail.getJobDataMap();
        if(jobDataMap.containsKey(QuartzConstant.BASE_JOB_ID)){
            String baseJobId=jobDataMap.get(QuartzConstant.BASE_JOB_ID).toString();
            List<JobB> list=jobBMapper.selectAll(" and id='"+baseJobId+"' ");
            if(list.size()>0){
                JobB jobB=list.get(0);
                LOGGER.info(jobB.getShowname()+">>>"+jobB.getGroupname()+"第"+jobB.getRuncount().add(new BigDecimal(1))+"次执行被TriggerListener否决");
            }
        }
    }

    /**
     * Scheduler 在 JobDetail 被执行之后调用这个方法。
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
//        System.out.println(context.getJobDetail().getKey()+":JobDetail 已经执行");
        JobDetail jobDetail=context.getJobDetail();
        JobDataMap jobDataMap=jobDetail.getJobDataMap();
        if(jobDataMap.containsKey(QuartzConstant.BASE_JOB_ID)){
            String baseJobId=jobDataMap.get(QuartzConstant.BASE_JOB_ID).toString();
            List<JobB> list=jobBMapper.selectAll(" and id='"+baseJobId+"' ");
            if(list.size()>0){
                JobB jobB=list.get(0);
                BigDecimal runcount=jobB.getRuncount()==null?new BigDecimal(0):jobB.getRuncount();
                jobB.setRuncount(runcount.add(new BigDecimal(1)));//累加运行次数
                TriggerKey triggerKey=context.getTrigger().getKey();
                try {
                    jobB.setStatus(context.getScheduler().getTriggerState(triggerKey).name());//更新任务的状态
//                    LOGGER.info(jobB.getShowname()+">>>"+jobB.getGroupname()+"第"+jobB.getRuncount()+"次执行完毕,执行状态:"+jobB.getStatus());
                    LOGGER.info(jobB.getShowname()+">>>"+jobB.getGroupname()+"第"+jobB.getRuncount()+"次执行完毕");
                } catch (SchedulerException e) {
                    LOGGER.error(e.getMessage());
                }
                jobBMapper.updateByPrimaryKey(jobB);
            }
        }

    }
}
