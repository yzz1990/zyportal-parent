package com.zkzy.zyportal.system.provider.serviceimpl;


import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemNodefile;
import com.zkzy.zyportal.system.api.service.SystemNodefileService;
import com.zkzy.zyportal.system.provider.mapper.SystemNodefileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.getEst8Date;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by admin on 2017/7/19.
 */
@Service("systemNodefileService")
public class SystemNodefileServiceImpl implements SystemNodefileService {
    @Autowired
 private SystemNodefileMapper systemNodefileMapper;


    @Override
    public List<SystemNodefile> selectAllbyPid(String param) {
        if(param!=null){
List<SystemNodefile> list=systemNodefileMapper.selectByPKandType(param);
 return  list;
        }else{
            return null;
        }

    }

    @Override
    public CodeObject saveUploadFile(SystemNodefile systemNodefile) {
        if(systemNodefile!=null){
            Date time=getEst8Date();
            systemNodefile.setCreatedate(time);
            systemNodefile.setUpdatedate(time);
            systemNodefile.setId(uuid());
            systemNodefileMapper.insert(systemNodefile);
            return ReturnCode.AREA_SUCCESS;
        }else {
            return  ReturnCode.AREA_FAILED;
        }

    }

    @Override
    public CodeObject delUploadFile(String id) {
        if(id!=null){
            systemNodefileMapper.deleteByPrimaryKey(id);
            return  ReturnCode.DEL_SUCCESS;
        }else {
            return  ReturnCode.DEL_FAILED;
        }
    }

    @Override
    public CodeObject updateFile(SystemNodefile systemNodefile,String status) {
        if(systemNodefile!=null){
            systemNodefile.setStatus(status);
            systemNodefileMapper.updateByPrimaryKey(systemNodefile);
            return  ReturnCode.TOPDF_SUCESS;
        }else{
            return ReturnCode.TOPDF_FAILED;
        }
    }
}
