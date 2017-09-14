package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SmsManB;
import com.zkzy.zyportal.system.api.service.ISmsManService;
import com.zkzy.zyportal.system.provider.mapper.SmsManBMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 Created by yupc on 2017/4/8.
 */
@Service("smsManServiceImpl")
public class SmsManServiceImpl implements ISmsManService{
    /**
     * 通讯人员Mapper
     */
    @Autowired
    private SmsManBMapper smsManBMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 新建通讯人员
     *
     * @param man 通讯人员信息
     */
    @Override
    public CodeObject addMan(SmsManB man){
        try {
            if (man.getId()==null || man.getId().trim().length()==0){
                man.setId(RandomHelper.uuid());
                smsManBMapper.insert(man);
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
     * 修改通讯人员
     *
     * @param man 通讯人员信息
     */
    @Override
    public CodeObject updateMan(SmsManB man){
        try {
            if (man.getId()!=null && man.getId().trim().length()>0){
                smsManBMapper.updateByPrimaryKey(man);
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
     * 删除通讯人员
     *
     * @param man 通讯人员信息
     */
    @Override
    public CodeObject deleteMan(SmsManB man){
        try {
            if (man.getId()!=null && man.getId().trim().length()>0){
                smsManBMapper.deleteByPrimaryKey(man.getId());
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
    public PageInfo manList(String param, Integer pageNumber, Integer pageSize) {
        if(param==null){
            param="";
        }
        PageInfo pageInfo=null;
        try {
            PageHelper.startPage(pageNumber,pageSize);//分页
            List<SmsManB> list= smsManBMapper.selectAll(param);
            pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
