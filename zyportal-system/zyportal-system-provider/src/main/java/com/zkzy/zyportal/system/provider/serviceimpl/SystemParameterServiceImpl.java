package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.zyportal.system.api.entity.SystemParameter;
import com.zkzy.zyportal.system.api.service.SystemParameterService;
import com.zkzy.zyportal.system.provider.mapper.SystemParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

@Service("systemParameterService")
public class SystemParameterServiceImpl implements SystemParameterService {
    @Autowired
    private SystemParameterMapper systemParameterMapper;

    @Override
    public List<SystemParameter> selectAll(String param) {
        return systemParameterMapper.selectAll(param);
    }

}
