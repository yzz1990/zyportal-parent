package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.portal.common.utils.StringHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemTableProperty;
import com.zkzy.zyportal.system.api.service.SystemTablePropertyService;
import com.zkzy.zyportal.system.provider.mapper.SystemTablePropertyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/19 0019.
 */
@Service("systemTablePropertyService")
public class SystemTablePropertyServiceImpl implements SystemTablePropertyService {

    @Autowired
    private SystemTablePropertyMapper systemTablePropertyMapper;

    @Override
    public SystemTableProperty selectByPrimaryKey(String stpid) {
        return systemTablePropertyMapper.selectByPrimaryKey(stpid);
    }

    @Override
    public CodeObject insert(SystemTableProperty systemTableProperty, String tablename, String tableid) {
        systemTableProperty.setId(RandomHelper.uuid());
        systemTableProperty.setFieldname(StringHelper.upperCase(systemTableProperty.getFieldname()));
        systemTableProperty.setTablename(tablename);
        systemTableProperty.setTableid(tableid);
        int i = systemTablePropertyMapper.insert(systemTableProperty);
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }

    @Override
    public List<SystemTableProperty> selectByTablename(String tablename) {
        return systemTablePropertyMapper.selectByTablename(tablename);
    }

    @Override
    public List<SystemTableProperty> selectToShow(String tableid) {
        return systemTablePropertyMapper.selectToShow(tableid);
    }

    @Override
    public List<SystemTableProperty> selectByTableid(String tableid) {
        return systemTablePropertyMapper.selectByTableid(tableid);
    }

    @Override
    public int deleteByTableid(String tableid) {
        return systemTablePropertyMapper.deleteByTableid(tableid);
    }

    @Override
    public CodeObject deleteByPrimaryKey(String systemTablePropertyid) {
        int i = systemTablePropertyMapper.deleteByPrimaryKey(systemTablePropertyid);
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }

    @Override
    public CodeObject update(SystemTableProperty systemTableProperty) {
        int i = systemTablePropertyMapper.updateByPrimaryKey(systemTableProperty);
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }

    @Override
    public int disableById(String id) {
        return systemTablePropertyMapper.disableById(id);
    }

    @Override
    public List<SystemTableProperty> selectDisabled(String tableId) {
        return systemTablePropertyMapper.selectDisabled(tableId);
    }
}
