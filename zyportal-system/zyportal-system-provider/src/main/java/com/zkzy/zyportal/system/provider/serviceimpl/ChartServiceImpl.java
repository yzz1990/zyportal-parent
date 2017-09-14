package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.zyportal.system.api.service.ChartService;
import com.zkzy.zyportal.system.api.viewModel.LogCount;
import com.zkzy.zyportal.system.provider.mapper.SystemLogMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
@Service("chartServiceImpl")
public class ChartServiceImpl implements ChartService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    /**
     * 用户操作分析 日期维度
     * @param type
     * @param who
     */
    @Override
    public Map<String,Object> userOperationChart(String type, String who) {
        int topCount=5;
        Date nowDate=new Date();
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(nowDate);
        List<Map<String,Object>> seriesList=new ArrayList<Map<String,Object>>();
        int xAxisSize=0;
        String startTime="";
        String endTime="";
        String[] xDatas=null;
        if("recent7Day".equals(type)){//近7天
            xAxisSize=7;
            xDatas=new String[xAxisSize];
            xDatas[xAxisSize-1]=DateHelper.getDate();
            for(int i=0;i<xAxisSize-1;i++){
                calendar.add(Calendar.DATE,-1);
                xDatas[xAxisSize-2-i]=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd");
            }
            startTime=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd")+" 00:00:00";
            endTime= DateHelper.getDate()+" 23:59:59";
        }else if("thisWeek".equals(type)){//本周
            xAxisSize=7;
            xDatas=new String[xAxisSize];
            int d = 0;
            if(calendar.get(Calendar.DAY_OF_WEEK)==1){
                d = -6;
            }else{
                d = 2-calendar.get(Calendar.DAY_OF_WEEK);
            }
            calendar.add(Calendar.DAY_OF_WEEK, d);
            startTime=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd")+" 00:00:00";
            xDatas[0]=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd");
            for(int i=0;i<xAxisSize-1;i++){
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                xDatas[1+i]=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd");
            }
            endTime=DateHelper.formatDate(calendar.getTime(),"yyyy-MM-dd")+" 23:59:59";
        }else if("thisMonth".equals(type)){//本月
            xAxisSize=calendar.getActualMaximum(Calendar.DATE);
            xDatas=new String[xAxisSize];
            startTime=DateHelper.formatDate(calendar.getTime(),"yyyy-MM")+"-01 00:00:00";
            endTime=DateHelper.formatDate(calendar.getTime(),"yyyy-MM")+"-"+xAxisSize+" 23:59:59";
            for(int i=1;i<=xAxisSize;i++){
                xDatas[-1+i]=DateHelper.formatDate(calendar.getTime(),"yyyy-MM")+"-"+(i<10?"0"+i:i);
            }
        }
        //每一天的登录统计
        String loginS=" AND URI='/Permission/auth/token' "+
                " AND SUBSTR(a.TIME,0,10)>='"+startTime.substring(0,10)+"' " +
                " AND SUBSTR(a.TIME,0,10)<='"+endTime.substring(0,10)+"' ";
        if(who!=null){
            loginS+="AND USERNAME='"+who+"'";
        }
        List<LogCount> loginlList=systemLogMapper.selectPermissionCountDateGroup(loginS);
        Map<String,LogCount> loginMap=new HashedMap();
        if(loginlList!=null && loginlList.size()>0){
            for(LogCount lc:loginlList){
                loginMap.put(lc.getTime(),lc);
            }
        }

        //操作统计(汇总)
        String param="";
        if(who!=null){
            param+=" AND USERNAME = '"+who+"' ";
        }
        param+=" AND TIME>='"+startTime+"' AND TIME<='"+endTime+"' ";
        //获取操作次数前 ?topCount
        List<LogCount> list=systemLogMapper.queryLogCount(param);
        if(list!=null && list.size()>0){
            for(int i=0;i<list.size();i++){
                if(i<topCount){
                    Object[] seriesData=new Object[xAxisSize];
                    LogCount logCount=list.get(i);
                    String permission=logCount.getPermission();//权限的编码
                    String permissionName=logCount.getPermissionname();
                    String totalCount=logCount.getCount();
                    //每一天的操作统计
                    String s= " AND a.PERMISSION ='"+permission+"' " +
                              " AND SUBSTR(a.TIME,0,10)>='"+startTime.substring(0,10)+"' " +
                              " AND SUBSTR(a.TIME,0,10)<='"+endTime.substring(0,10)+"' ";
                    if(who!=null){
                        s+=" AND a.USERNAME = '"+who+"' ";
                    }
                    List<LogCount> detailList=systemLogMapper.selectPermissionCountDateGroup(s);
                    Map<String,LogCount> detailMap=new HashedMap();
                    if(detailList!=null && detailList.size()>0){
                        for(LogCount lc:detailList){
                            detailMap.put(lc.getTime(),lc);
                        }
                    }
                    for(int j=0;j<xAxisSize;j++){
                        String day=xDatas[j];
                        Object[] arrO=new Object[]{day,0,0};
                        if(detailMap.containsKey(day)){
                            arrO[1]=Integer.valueOf(detailMap.get(day).getCount());
                        }
                        //当天该用户登录次数
                        if(loginMap.containsKey(day)){
                            arrO[2]=Integer.valueOf(loginMap.get(day).getCount());
                        }
                        seriesData[j]=arrO;
                    }
                    Map<String,Object> m=new HashedMap();
                    m.put("legend",permissionName);
                    m.put("totalCount",totalCount);
                    m.put("seriesData",seriesData);//时间段数据
                    seriesList.add(m);
                }
            }
        }
        Map<String,Object> map=new HashedMap();
        map.put("xDatas",xDatas);
        map.put("seriesList",seriesList);
        return  map;
    }

}
