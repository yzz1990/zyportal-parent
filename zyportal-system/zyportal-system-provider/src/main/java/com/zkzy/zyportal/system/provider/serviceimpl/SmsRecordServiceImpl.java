package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.zyportal.system.api.entity.SmsRecordB;
import com.zkzy.zyportal.system.api.util.CCPSendSMSUtil;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.ISmsRecordService;
import com.zkzy.zyportal.system.provider.config.SmsConfig;
import com.zkzy.zyportal.system.provider.mapper.SmsRecordBMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 Created by yupc on 2017/4/8.
 */
@Service("smsRecordServiceImpl")
public class SmsRecordServiceImpl implements ISmsRecordService{

    /**
     * 通讯记录Mapper
     */
    @Autowired
    private SmsRecordBMapper smsRecordBMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 新建通讯记录
     *
     * @param record 通讯记录信息
     */
    @Override
    public CodeObject addRecord(SmsRecordB record){
        try {
            if (record.getId()==null){
                record.setId(RandomHelper.uuid());
                record.setSubtime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                record.setState(BigDecimal.valueOf(0));
                record.setType(BigDecimal.valueOf(1));
                String strToMans = record.getTomans();
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
                    record.setTomans(toManName);
                    record.setTotels(toManTelephone);
                }
                smsRecordBMapper.insert(record);
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
     * 修改通讯记录
     *
     * @param record 通讯记录信息
     */
    @Override
    public CodeObject updateRecord(SmsRecordB record){
        try {
            if (record.getId()!=null){
                smsRecordBMapper.updateByPrimaryKey(record);
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
     * 删除通讯记录
     *
     * @param record 通讯记录信息
     */
    @Override
    public CodeObject deleteRecord(SmsRecordB record){
        try {
            if (record.getId()!=null){
                smsRecordBMapper.deleteByPrimaryKey(record.getId());
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
    public PageInfo recordList(String param, Integer pageNumber, Integer pageSize) {
        if(param==null){
            param="";
        }
        PageInfo pageInfo=null;
        try {
            PageHelper.startPage(pageNumber,pageSize);//分页
            List<SmsRecordB> list= smsRecordBMapper.selectAll(param);
            pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送短信
     *
     */
    @Override
    public CodeObject sendSmsRecord(){
        try{
            //查询所有未发送的任务
            List<SmsRecordB> list= smsRecordBMapper.selectAll(" and STATE=0");
            for(SmsRecordB record:list){
                String recordId=record.getId()==null?null:record.getId().toString();
                if(recordId!=null){
                    //判断是否定时
                    BigDecimal istimed = record.getIstimed()==null?BigDecimal.valueOf(0):record.getIstimed();
                    String totels = record.getTotels()==null?"":record.getTotels().toString();
                    String[] totelsArray = totels.split(",");
                    String content = record.getContent()==null?"":record.getContent().toString();
                    if(istimed.compareTo(BigDecimal.valueOf(1))==0) {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String deftime = record.getDeftime()==null?"":record.getDeftime().toString();
                        Date defDate = df.parse(deftime);
                        //当前时间大于等于定时时间
                        if(new Date().getTime()>=defDate.getTime()){
                            //发短信
                            sendMessage(totelsArray,content,record);
                        }
                    }else{
                        //发短信
                        sendMessage(totelsArray,content,record);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.FAILED;//失败
        }finally {

        }
        return ReturnCode.SUCCESS;//成功
    }

    /**
     * 发送短信
     *
     */
    private void sendMessage(String[] totelsArray,String content,SmsRecordB record){
        //初始化配置
        if(CCPSendSMSUtil.restAPI==null)SmsConfig.initSMS();
        int success=0;
        int failure=0;
        for(int i =0;i<totelsArray.length;i++){
            String totel = totelsArray[i];
            String statusCode = CCPSendSMSUtil.sendSMS(totel,SmsConfig.getTemplateId(),new String[]{content});
            if("000000".equals(statusCode))success++;
            else failure++;
        }
        record.setSuccess(BigDecimal.valueOf(success));
        record.setFailure(BigDecimal.valueOf(failure));
        record.setState(BigDecimal.valueOf(1));
        record.setSendtime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        smsRecordBMapper.updateByPrimaryKey(record);
    }

}
