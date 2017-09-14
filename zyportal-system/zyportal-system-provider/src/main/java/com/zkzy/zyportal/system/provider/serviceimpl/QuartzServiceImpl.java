package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.quartz.QuartzConstant;
import com.zkzy.portal.common.quartz.QuartzServiceException;
import com.zkzy.portal.common.quartz.QuartzUtils;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.JobB;
import com.zkzy.zyportal.system.api.service.QuartzService;
import com.zkzy.zyportal.system.provider.mapper.JobBMapper;
import com.zkzy.zyportal.system.provider.quartz_jobs.SpringJobModel;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

@Service("quartzServiceImpl")
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobBMapper jobBMapper;
    @Autowired
    private QuartzUtils quartzUtils;

    @Bean
    public QuartzUtils quartzUtils()  {
        return new QuartzUtils();
    }


    /**
     * 所有任务列表
     */
    public PageInfo list(String param, int currentPage, int pageSize){
//        List<JobB> taskInfoList = new ArrayList<>();
        if(param==null){
            param="";
        }
        PageInfo pageInfo;
        try {
            PageHelper.startPage(currentPage,pageSize);//分页
            List<JobB> list=jobBMapper.selectAll(param);

            for(JobB oneJob:list){
                String jobName=oneJob.getJobname();//实现类名称
                String jobGroup=oneJob.getGroupid();//分组的id

                JobKey jobKey=quartzUtils.getJobKey(jobName, jobGroup);
                TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);

                Trigger trigger=quartzUtils.getTrigger(scheduler,triggerKey);
                Trigger.TriggerState triggerState = quartzUtils.getTriggerState(scheduler,triggerKey);
                JobDetail jobDetail = quartzUtils.getJobDetail(scheduler,jobKey);

                String cronExpression = oneJob.getCron();
                String nextTime="";

                oneJob.setJobname(jobKey.getName());
                oneJob.setGroupid(jobKey.getGroup());
                oneJob.setStatus(triggerState.name());

                if(QuartzConstant.QUARTZ_NORMAL.equals(oneJob.getStatus())){
                    nextTime= DateFormatUtils.format(trigger.getNextFireTime(), "yyyy-MM-dd HH:mm:ss");
                }
                oneJob.setCron(cronExpression);
                oneJob.setNexttime(nextTime);
                if(jobDetail!=null){
                    oneJob.setDescription(jobDetail.getDescription());
                }

//                taskInfoList.add(oneJob);

            }
            pageInfo=new PageInfo(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
//        pageInfo.setList(taskInfoList);

        return pageInfo;

    }

    /**
     * 新增定时任务基本表,不启动
     * @param info
     */
    public CodeObject addJob(JobB info) {
        try {
            //判断任务是否存在,根据jobname和jobgroupid


            JobB oldJob=jobBMapper.getJobByNameGroupid(info.getJobname(),info.getGroupid());
            if(oldJob!=null){//任务已存在
                return ReturnCode.EXIST_OBJECT;
            }

            String createTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
            //新增到自定义的基本表
            info.setId(RandomHelper.uuid());
            info.setCreatetime(createTime);
            info.setStatus(QuartzConstant.QUARTZ_NONE);
            info.setRuncount(new BigDecimal(0));
            jobBMapper.insert(info);
            return ReturnCode.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnCode.FAILED;
        }
    }

    /**
     * 修改定时任务基本表
     * @param info
     */
    public CodeObject edit(JobB info) {
        String modifyTime= DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        try {
            //更新到自定义的基本表
            info.setModifytime(modifyTime);
            jobBMapper.updateByPrimaryKey(info);

            String jobName=info.getJobname();
            String jobGroup=info.getGroupid();

            if(QuartzConstant.QUARTZ_PAUSED.equals(info.getStatus()) || QuartzConstant.QUARTZ_NONE.equals(info.getStatus())) {//暂停中,或不在调度中,修改之后也不需要启动
                if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {//存在
                    String cronExpression=info.getCron();
                    String jobDescription=info.getDescription();

                    TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);
                    JobKey jobKey = quartzUtils.getJobKey(jobName, jobGroup);

//                    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
//                    CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(cronScheduleBuilder).build();
                    CronTrigger cronTrigger=quartzUtils.getCronTrigger(triggerKey,jobKey,cronExpression,jobDescription);


                    JobDetail jobDetail = quartzUtils.getJobDetail(scheduler,jobKey);
                    jobDetail.getJobBuilder().withDescription(jobDescription);
                    jobDetail.getJobDataMap().put(QuartzConstant.BASE_JOB_ID,info.getId());
                    HashSet<Trigger> triggerSet = new HashSet<>();
                    triggerSet.add(cronTrigger);
                    //替换原来调度的配置
                    quartzUtils.scheduleJob(scheduler,jobDetail, triggerSet, true);

                    quartzUtils.pauseTrigger(scheduler,triggerKey);//暂停
                }else{
                    //因为不存在,因此不需要操作
                }
            }else{//需要启动
                if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {//存在
                    editJob(info);
                }else{
                    addStartJob(info);//加入调度启动
                }
            }
            return ReturnCode.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return ReturnCode.FAILED;
        }
    }

    /**
     * 将job加入调度
     * @param info
     */
    public void addStartJob(JobB info) {
        String jobName=info.getJobname();
        String jobGroup=info.getGroupid();
        String cronExpression=info.getCron();
        String jobDescription=info.getDescription();

        try {
            TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);
            JobKey jobKey = quartzUtils.getJobKey(jobName, jobGroup);

            if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {
                throw new QuartzServiceException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
//            CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
//            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(jobDescription).withSchedule(schedBuilder).build();
            CronTrigger cronTrigger=quartzUtils.getCronTrigger(triggerKey,jobKey,cronExpression,jobDescription);


            //判断是bean还是class类型
            JobDetail jobDetail;
            if("bean".equals(info.getClassorbean()) ){
                jobDetail=quartzUtils.initJobDetail(SpringJobModel.class,jobKey,jobDescription);

                jobDetail.getJobDataMap().put(SpringJobModel.SRPTING_BEAN_NAME, info.getJobname());
                jobDetail.getJobDataMap().put(SpringJobModel.SRPTING_METHOD_NAME, info.getUsemethod());
            }else{
                Class<? extends Job> clazz = (Class<? extends Job>)Class.forName(jobName);
//                jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription).build();
                jobDetail=quartzUtils.initJobDetail(clazz,jobKey,jobDescription);
            }

            jobDetail.getJobDataMap().put(QuartzConstant.BASE_JOB_ID,info.getId());
            quartzUtils.scheduleJob(scheduler,jobDetail, cronTrigger);

        } catch (SchedulerException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new QuartzServiceException("类名不存在或执行表达式错误");
        }
    }

    /**
     * 修改定时任务
     * @param info
     */
    public void editJob(JobB info) {
        String jobName=info.getJobname();
        String jobGroup=info.getGroupid();
        String cronExpression=info.getCron();
        String jobDescription=info.getDescription();
        String modifyTime=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

        try {

            if (!quartzUtils.checkExists(scheduler,jobName, jobGroup)) {
                throw new QuartzServiceException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
            TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);
            JobKey jobKey = quartzUtils.getJobKey(jobName, jobGroup);
//            CronScheduleBuilder cronScheduleBuilder =QuartzUtils.getCronScheduleBuilder(cronExpression);
//            CronTrigger cronTrigger =QuartzUtils.getCronTrigger(triggerKey,cronScheduleBuilder,cronExpression);
            CronTrigger cronTrigger=quartzUtils.getCronTrigger(triggerKey,jobKey,cronExpression,jobDescription);

            JobDetail jobDetail = quartzUtils.getJobDetail(scheduler,jobKey);
            jobDetail.getJobBuilder().withDescription(jobDescription);
            jobDetail.getJobDataMap().put(QuartzConstant.BASE_JOB_ID,info.getId());
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            quartzUtils.scheduleJob(scheduler,jobDetail, triggerSet, true);

        } catch (SchedulerException e) {
            throw new QuartzServiceException("类名不存在或执行表达式错误");
        }
    }


    /**
     * 删除定时任务
     */
    @Override
    public void delete(JobB info) {
        try {
            String jobName=info.getJobname();
            String jobGroup=info.getGroupid();
            TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);

            jobBMapper.deleteByPrimaryKey(info.getId());

            if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {
                quartzUtils.pauseTrigger(scheduler,triggerKey);
                quartzUtils.unscheduleJob(scheduler,triggerKey);
            }
        } catch (SchedulerException e) {
            throw new QuartzServiceException(e.getMessage());
        }
    }

    /**
     * 暂停定时任务
     */
    public void pause(JobB info){
        try {
            String jobName=info.getJobname();
            String jobGroup=info.getGroupid();
            TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);

            info.setStatus(QuartzConstant.QUARTZ_PAUSED);
            jobBMapper.updateByPrimaryKey(info);

            if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {
                quartzUtils.pauseTrigger(scheduler,triggerKey);
            }
        } catch (SchedulerException e) {
            throw new QuartzServiceException(e.getMessage());
        }
    }

    /**
     * 开始定时任务
     */
    public void resume(JobB info){
        try {
            String jobName=info.getJobname();
            String jobGroup=info.getGroupid();
            TriggerKey triggerKey =quartzUtils.getTriggerKey(jobName, jobGroup);

            //更新基本表
            info.setStatus(QuartzConstant.QUARTZ_NORMAL);
            jobBMapper.updateByPrimaryKey(info);

            if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {//已经存在了
                quartzUtils.resumeTrigger(scheduler,triggerKey);
            }else{
                addStartJob(info);//加入调度
            }
        } catch (SchedulerException e) {
            throw new QuartzServiceException(e.getMessage());
        }
    }

    /**
     * 从quartz中移除任务,但不删基本表
     * @param info
     */
    @Override
    public void removeJob(JobB info) {
        try {
            String jobName=info.getJobname();
            String jobGroup=info.getGroupid();
            TriggerKey triggerKey = quartzUtils.getTriggerKey(jobName, jobGroup);
            if (quartzUtils.checkExists(scheduler,jobName, jobGroup)) {
                quartzUtils.pauseTrigger(scheduler,triggerKey);
                quartzUtils.unscheduleJob(scheduler,triggerKey);
            }

            //更新基本表
            info.setStatus(QuartzConstant.QUARTZ_NONE);
            info.setModifytime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            info.setNexttime("");
            info.setStarttime("");
            info.setRuncount(new BigDecimal(0));

            jobBMapper.updateByPrimaryKey(info);

        } catch (SchedulerException e) {
            throw new QuartzServiceException(e.getMessage());
        }
    }


    /**
     * 测试bean类型调度任务
     */
    public void testBeanJob(){
        System.out.println("测试bean类型的调度"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }


}


