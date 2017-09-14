package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.AreaAdministrationareaB;
import com.zkzy.zyportal.system.api.service.AreaAdministrationareaBService;
import com.zkzy.zyportal.system.provider.mapper.AreaAdministrationareaBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;
import static com.zkzy.portal.common.utils.DateHelper.getDate;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by Zhangzy on 2017/5/10.
 */
@Service("areaAdministrationareaBService")
public class AreaAdministrationareaBServiceImpl  implements AreaAdministrationareaBService{

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private AreaAdministrationareaBMapper  areaAdministrationareaBMapper;

    @Override
    public CodeObject InsertArea(AreaAdministrationareaB area) {
        if(area !=null){
            List<AreaAdministrationareaB> list= areaAdministrationareaBMapper.selectByArea(area);
            if(list.size()>0){
                return  ReturnCode.INSERT_FAILED;
            }
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            area.setId(uuid());
            area.setCreatetime(localtime);
            area.setModifytime(localtime);
            areaAdministrationareaBMapper.insert(area);
            return ReturnCode.AREA_SUCCESS;
        }else{
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject DeleteById(String ID) {
        if(ID!=null){
            areaAdministrationareaBMapper.deleteByPrimaryKey(ID);
            return ReturnCode.DEL_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }

    }

    @Override
    public CodeObject UpdateById(AreaAdministrationareaB area) {
        if(area !=null){
          List<AreaAdministrationareaB> list= areaAdministrationareaBMapper.selectByArea(area);
            if(list.size()>0&&!(list.get(0).getId().equals(area.getId()))){
                return  ReturnCode.UPDATE_FAILED;
            }
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            area.setModifytime(localtime);
            areaAdministrationareaBMapper.updateByPrimaryKey(area);
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return ReturnCode.UPDATE_FAILED;
        }
    }

    @Override
    public AreaAdministrationareaB SelectById(String ID) {
        AreaAdministrationareaB area=null;
        if(ID!=null){
           area=areaAdministrationareaBMapper.selectByPrimaryKey(ID);
        }
        return area;
    }

    @Override
    public PageInfo SelectAll(int currentPage, int pageSize,String city) {
        PageHelper.startPage(currentPage,pageSize);//分页
        List<AreaAdministrationareaB> list=areaAdministrationareaBMapper.selectAll(city);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public List<AreaAdministrationareaB> selectAreas() {
        List<AreaAdministrationareaB> list=areaAdministrationareaBMapper.selectAreas();
        return list;
    }


}
