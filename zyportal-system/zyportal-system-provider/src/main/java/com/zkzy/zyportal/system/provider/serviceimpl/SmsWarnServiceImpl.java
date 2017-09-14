package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SmsRecordB;
import com.zkzy.zyportal.system.api.entity.SmsWarnB;
import com.zkzy.zyportal.system.api.service.ISmsWarnService;
import com.zkzy.zyportal.system.api.util.CCPSendSMSUtil;
import com.zkzy.zyportal.system.provider.config.SmsConfig;
import com.zkzy.zyportal.system.provider.mapper.SmsRecordBMapper;
import com.zkzy.zyportal.system.provider.mapper.SmsWarnBMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 Created by yupc on 2017/4/8.
 */
@Service("smsWarnServiceImpl")
public class SmsWarnServiceImpl implements ISmsWarnService{

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

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 新建短信预警
     *
     * @param warn 短信预警信息
     */
    @Override
    public CodeObject addWarn(SmsWarnB warn){
        try {
            if (warn.getId()==null || warn.getId().trim().length()==0){
                warn.setId(RandomHelper.uuid());
                String strToMans = warn.getTomans();
                if(null!=strToMans && strToMans.trim().length()>0) {
                    //解析发送人员和手机号码
                    String[] arrToMans = strToMans.split(",");
                    String toManName = "";
                    String toManTelephone = "";
                    for (int i = 0; i < arrToMans.length; i++) {
                        String strMans = arrToMans[i];
                        String[] arrMans = strMans.split(":");
                        toManName += arrMans[0] + ",";
                        toManTelephone += arrMans[1] + ",";
                    }
                    if (toManName.length() > 0) toManName = toManName.substring(0, toManName.length() - 1);
                    if (toManTelephone.length() > 0)
                        toManTelephone = toManTelephone.substring(0, toManTelephone.length() - 1);
                    //重新设置发送人员和手机号码
                    warn.setTomans(toManName);
                    warn.setTotels(toManTelephone);
                }
                smsWarnBMapper.insert(warn);
            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    /**
     * 修改短信预警
     *
     * @param warn 短信预警信息
     */
    @Override
    public CodeObject updateWarn(SmsWarnB warn){
        try {
            if (warn.getId()!=null && warn.getId().trim().length()>0){
                String strToMans = warn.getTomans();
                if(null!=strToMans && strToMans.trim().length()>0) {
                    //解析发送人员和手机号码
                    String[] arrToMans = strToMans.split(",");
                    String toManName = "";
                    String toManTelephone = "";
                    for (int i = 0; i < arrToMans.length; i++) {
                        String strMans = arrToMans[i];
                        String[] arrMans = strMans.split(":");
                        toManName += arrMans[0] + ",";
                        toManTelephone += arrMans[1] + ",";
                    }
                    if (toManName.length() > 0) toManName = toManName.substring(0, toManName.length() - 1);
                    if (toManTelephone.length() > 0)
                        toManTelephone = toManTelephone.substring(0, toManTelephone.length() - 1);
                    //重新设置发送人员和手机号码
                    warn.setTomans(toManName);
                    warn.setTotels(toManTelephone);
                }
                smsWarnBMapper.updateByPrimaryKey(warn);
            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    /**
     * 删除短信预警
     *
     * @param warn 短信预警信息
     */
    @Override
    public CodeObject deleteWarn(SmsWarnB warn){
        try {
            if (warn.getId()!=null && warn.getId().trim().length()>0){
                smsWarnBMapper.deleteByPrimaryKey(warn.getId());
            }else{
                return ReturnCode.DEL_FAILED;//删除失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除成功
    }

    @Override
    public PageInfo warnList(String param, Integer pageNumber, Integer pageSize) {
        if(param==null){
            param="";
        }
        PageInfo pageInfo=null;
        try {
            PageHelper.startPage(pageNumber,pageSize);//分页
            List<SmsWarnB> list= smsWarnBMapper.selectAll(param);
            pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static boolean flag = true;
    /**
     * 发送预警信息
     *
     */
    public CodeObject sendSmsWarn4Water() {
        try {
            if(flag){
                flag = false;
                String jobcode = "smsWarnServiceImpl";
                String param = " and jobcode='"+jobcode+"'";
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
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnCode.FAILED;//失败
        } finally {

        }
        return ReturnCode.SUCCESS;//成功
    }

    /**
     * 发送短信
     *
     */
    private void sendMessage(String fromman,String tomans,String totels,String content){
        //初始化配置
        if(CCPSendSMSUtil.restAPI==null) SmsConfig.initSMS();
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
