package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemMachine;
import com.zkzy.zyportal.system.api.service.SystemMachineService;
import com.zkzy.zyportal.system.api.viewModel.EquipmentModel;
import com.zkzy.zyportal.system.provider.mapper.SystemMachineMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
@Service("systemMachineServiceImpl")
@ConfigurationProperties(prefix="equipment")
public class SystemMachineServiceImpl implements SystemMachineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemMachineServiceImpl.class);

    private static final String ON="on";
    private static final String OFF="off";
    private static final String REDISPRE="machineOnline:";

    private static String datalogurl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private SystemMachineMapper systemMachineMapper;

    @Override
    public PageInfo machineList(String param, Paging page) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<SystemMachine> list = systemMachineMapper.selectAll(param);
            for(SystemMachine machine:list){
                //根据站点编号关联名称
                machine.setStnm("根据编号获取");
                //在线的实时信息,从redis中获取
                machine.setStatus(OFF);
                String mNo=machine.getMno();//设备序列号
                if(mNo!=null && mNo.trim().length()>0){
                    String jsonStr = redisTemplate.opsForValue().get(REDISPRE+mNo);
                    if(jsonStr!=null){
                        machine.setStatus(ON);//在线
                        JSONObject jsonObject = JSON.parseObject(jsonStr);

                        String lastlinkedtime=jsonObject.get("lastlinkedtime")==null?null:jsonObject.get("lastlinkedtime").toString();
                        String lastdistime=jsonObject.get("lastdistime")==null?null:jsonObject.get("lastdistime").toString();
                        String getdatatime=jsonObject.get("getdatatime")==null?null:jsonObject.get("getdatatime").toString();

                        machine.setLastlinkedtime(lastlinkedtime);
                        machine.setLastdistime(lastdistime);
                        machine.setGetdatatime(getdatatime);

                    }
                }

            }
            return new PageInfo<>(list);
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }


    @Override
    public CodeObject addMachine(SystemMachine systemMachine) {
        try {
            if (systemMachine.getId()==null){
                //判断设备是否存在
                SystemMachine oldMach=systemMachineMapper.isExistsByNO(systemMachine.getMno());
                if(oldMach!=null){//设备已存在
                    return ReturnCode.EXIST_OBJECT;
                }

                systemMachine.setId(RandomHelper.uuid());
                systemMachine.setCreatetime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                systemMachine.setStatus(OFF);//新增时默认离线

                systemMachineMapper.insert(systemMachine);
            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    @Override
    public CodeObject updateMachine(SystemMachine systemMachine) {
        try {
            if (systemMachine.getId()!=null){
                //判断设备是否存在
                SystemMachine oldMach=systemMachineMapper.isExistsByNO(systemMachine.getMno());
                if(oldMach!=null){//设备已存在
                    if(!systemMachine.getId().equals(oldMach.getId())){//存在的设备与正在编辑的不是同一个
                        return ReturnCode.EXIST_OBJECT;
                    }
                }
                systemMachine.setUpdatetime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

                systemMachineMapper.updateByPrimaryKey(systemMachine);
            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    @Override
    public CodeObject delMachine(SystemMachine systemMachine) {
        try {
            if (systemMachine.getId()!=null){
                systemMachineMapper.deleteByPrimaryKey(systemMachine.getId());
            }else{
                return ReturnCode.DEL_FAILED;//删除失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除成功
    }


    /**
     * 设备历史
     * 解析日志文件获取
     * @param systemMachine
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<EquipmentModel> machineHis(SystemMachine systemMachine,String startTime,String endTime) throws ParseException, IOException {
        List<EquipmentModel> eList=new ArrayList<EquipmentModel>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfAll=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal=Calendar.getInstance();
        String mNo=systemMachine.getMno();//设备序列号
        String startYMD=sdf.format(sdfAll.parse(startTime));//起始的年月日
//        String endYMD=sdf.format(sdfAll.parse(endTime));//结束的年月日
        InputStreamReader isr =null;
        BufferedReader reader =null;
        try {
            Date cursorDate=sdfAll.parse(startYMD+" 00:00:00");
//            Date cursorDate=sdfAll.parse(endYMD+" 23:59:59");
            Date startDate=sdfAll.parse(startTime);
            Date endDate=sdfAll.parse(endTime);
            cal.setTime(cursorDate);
            while (cursorDate.before(endDate)  || cursorDate.equals(startDate)){
//            while (cursorDate.after(startDate) || cursorDate.equals(startDate)){
                String fileName=sdf.format(cursorDate)+".log";//log文件名
                cal.add(Calendar.DATE,1);
//                cal.add(Calendar.DATE,-1);
                cursorDate=cal.getTime();
                //日志文件解析
                File file = new File(datalogurl+"/"+fileName);
                if(!file.exists()){//文件不存在
                    continue;
                }
                isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
                reader = new BufferedReader(isr);
                String tempString = null;
                while ((tempString = reader.readLine()) != null) {
                    if(tempString.contains(REDISPRE)){//在线设备的日志
                        String jsonAll=tempString.split(REDISPRE)[1];
                        JSONObject jsonObject = JSON.parseObject(jsonAll);
                        //设备编号
                        String mid=jsonObject.get("mno")==null?null:jsonObject.get("mno").toString();
                        if(mNo!=null && mNo.equals(mid)){//设备编号匹配
                            //数据上报时间
                            String getdatatime=jsonObject.get("getdatatime")==null?null:jsonObject.get("getdatatime").toString();
                            Date dataDate=sdfAll.parse(getdatatime);//数据上报时间
                            if((dataDate.after(startDate) || dataDate.equals(startDate)) &&
                                dataDate.before(endDate) || dataDate.equals(endDate)){//在时间范围内
                                //连接时间
                                String linkedtime=jsonObject.get("linkedtime")==null?null:jsonObject.get("linkedtime").toString();
                                //断开时间
                                String distime=jsonObject.get("distime")==null?null:jsonObject.get("distime").toString();
                                //数据Josn字符串
                                String dataJosn=jsonObject.get("data")==null?null:jsonObject.get("data").toString();

                                EquipmentModel eModel=new EquipmentModel();
                                eModel.setMno(mNo);
                                eModel.setStcd(systemMachine.getStcd());
                                eModel.setLinkedtime(linkedtime);
                                eModel.setDistime(distime);
                                eModel.setGetdatatime(getdatatime);
                                eModel.setData(dataJosn);

                                eList.add(eModel);
                            }else{
                                continue;
                            }
                        }else{
                            continue;
                        }
                    }
                }
            }
        } finally {
            if(reader!=null){
                reader.close();
            }
            if(isr!=null){
                isr.close();
            }
        }
        Collections.reverse(eList);//倒序
        return eList;
    }

    public static String getDatalogurl() {
        return datalogurl;
    }

    public static void setDatalogurl(String datalogurl) {
        SystemMachineServiceImpl.datalogurl = datalogurl;
    }

}

