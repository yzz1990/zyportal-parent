package com.zkzy.zyportal.system.provider.quartz_jobs;

import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.entity.SmsRecordB;
import com.zkzy.zyportal.system.api.entity.SmsWarnB;
import com.zkzy.zyportal.system.api.util.CCPSendSMSUtil;
import com.zkzy.zyportal.system.provider.config.SmsConfig;
import com.zkzy.zyportal.system.provider.mapper.SmsRecordBMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zkzy.zyportal.system.provider.mapper.SmsWarnBMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by wangzl on 2017/8/1.
 */
@Component
@DisallowConcurrentExecution
public class SmsWarnJob4Water extends BaseJob  {

    @Autowired
    private Scheduler scheduler;
    /**
     * 短信预警Mapper
     */
    @Autowired
    private SmsWarnBMapper smsWarnBMapper;
    /**
     * 通讯记录Mapper
     */
    @Autowired
    private SmsRecordBMapper smsRecordBMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //当水位超警戒给指定人员发生短信
        System.out.println(this.getClass().getName()+"正在执行"+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss "));
        if(true){
            String jobcode = this.getClass().getName();
            String param = " and jobcode='"+jobcode+"'";
            if(null==smsWarnBMapper)return;
            List<SmsWarnB> list= smsWarnBMapper.selectAll(param);
            for(SmsWarnB warn:list){
                String jobname = warn.getJobname()==null?"":warn.getJobname().trim();
                String tomans = warn.getTomans()==null?"":warn.getTomans().trim();
                String totels = warn.getTotels()==null?"":warn.getTotels().trim();
                String content = "您好，长江水位超出历史最高水位，请加强防汛工作！";
                //发送短信
                sendMessage(jobname,tomans,totels,content);
            }
        }
    }

    /**
     * 发送短信
     *
     */
    private void sendMessage(String fromman,String tomans,String totels,String content){
        //初始化配置
        if(CCPSendSMSUtil.restAPI==null)SmsConfig.initSMS();
        String[] totelsArray = totels.split(",");
        int success=0;
        int failure=0;
        for(int i =0;i<totelsArray.length;i++){
            String totel = totelsArray[i];
            String statusCode = CCPSendSMSUtil.sendSMS(totel, SmsConfig.getTemplateId(),new String[]{content});
            if("000000".equals(statusCode))success++;
            else failure++;
        }
        SmsRecordB record = new SmsRecordB();
        record.setId(RandomHelper.uuid());
        record.setFromman(fromman);
        record.setSubtime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        record.setContent(content);
        record.setTomans(tomans);
        record.setTotels(totels);
        record.setSuccess(BigDecimal.valueOf(success));
        record.setFailure(BigDecimal.valueOf(failure));
        record.setState(BigDecimal.valueOf(1));
        record.setSendtime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        record.setType(BigDecimal.valueOf(2));
        smsRecordBMapper.insert(record);
    }
}
