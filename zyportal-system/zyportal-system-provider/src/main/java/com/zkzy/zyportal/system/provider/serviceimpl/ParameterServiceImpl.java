package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemParameter;
import com.zkzy.zyportal.system.api.service.ParameterService;
import com.zkzy.zyportal.system.api.viewModel.Json;
import com.zkzy.zyportal.system.provider.mapper.SystemParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;

/**
 * Created by Zhangzy on 2017/6/19.
 */
@Service("parameterService")
public class ParameterServiceImpl implements ParameterService {
    public static final String BASE_NAME="base";

    @Autowired
    private SystemParameterMapper parameterMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public PageInfo selectAll(int currentPage, int pageSize,String param) {
        PageHelper.startPage(currentPage,pageSize);
        List<SystemParameter> list=parameterMapper.selectParams(param);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }



    @Override
    public PageInfo selectGroup(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<SystemParameter> list=parameterMapper.selectGroup();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public CodeObject insertParam(SystemParameter parameter) {
        if(parameter!=null){
           String parameterId=parameterMapper.selectsequence();
            Date time=new Date();
            parameter.setParameterId(Long.parseLong(parameterId));
            parameter.setCreated(time);
            parameter.setLastmod(time);
            parameter.setStatus(ReturnCode.PERSISTENCE_STATUS);
            parameterMapper.insert(parameter);
            this.saveParameterRedis(parameter);
            return  ReturnCode.AREA_SUCCESS;
        }else {
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject updateParamById(SystemParameter parameter) {
if(parameter!=null){
    parameterMapper.updateParamById(parameter);
    SystemParameter list= parameterMapper.selectAllById(parameter);
  this.saveParameterRedis(list);
    return  ReturnCode.UPDATE_SUCCESS;
}else{
    return  ReturnCode.UPDATE_FAILED;
}
    }

    @Override
    public CodeObject delparamById(SystemParameter parameter) {
        if(parameter!=null){
            parameterMapper.delparamById(parameter);
            this.delParameterRedis(parameter);
            return  ReturnCode.DEL_SUCCESS;
        }else {
            return  ReturnCode.DEL_FAILED;
        }
    }

    @Override
    public SystemParameter seleclByMyId(String Id) {
if(!"".equals(Id)){
  return   parameterMapper.selectByMyID(Id);
}else{
    return null;
}

    }

    public void saveParameterRedis(SystemParameter parameter){
        try{
            String codeMyid = parameter.getMyid();
            Json json=new Json();
            json.setStatus(true);
            json.setParameter(parameter);
            String jsonStr = JSONObject.toJSONString(json);
            /**存入自己的值到redis*/
            redisTemplate.opsForValue().set(BASE_NAME+codeMyid,jsonStr);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("操作系统参数,数据缓存进redis结束");
        }
    }
    public void delParameterRedis(SystemParameter parameter){
        try{
            String codeMyid = parameter.getMyid();
            Json json=new Json();
            json.setStatus(true);
            json.setParameter(parameter);
            String jsonStr = JSONObject.toJSONString(json);
            /**存入自己的值到redis*/
            redisTemplate.delete(BASE_NAME+codeMyid);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("操作系统参数,数据缓存进redis结束");
        }
    }

}
