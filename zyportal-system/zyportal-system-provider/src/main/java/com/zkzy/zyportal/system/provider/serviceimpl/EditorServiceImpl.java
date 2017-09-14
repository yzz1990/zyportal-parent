package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemEditor;
import com.zkzy.zyportal.system.api.service.EditorService;
import com.zkzy.zyportal.system.provider.mapper.SystemEditorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;
import static com.zkzy.portal.common.utils.DateHelper.getDate;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by admin on 2017/6/26.
 */
@Service("editorService")
public class EditorServiceImpl implements EditorService {

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    SystemEditorMapper systemEditorMapper;

    @Override
    public PageInfo selectAll(int currentPage, int pageSize, String param) {
        PageHelper.startPage(currentPage,pageSize);//分页
        List<SystemEditor> list=systemEditorMapper.selectAll(param);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public CodeObject insertEditor(SystemEditor systemEditor) {
        if(systemEditor!=null){
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            systemEditor.setId(uuid());
            systemEditor.setCreatetime(localtime);
            systemEditor.setLastmodifytime(localtime);
            systemEditor.setIsshow("1");
            systemEditorMapper.insert(systemEditor);
            return ReturnCode.AREA_SUCCESS;
        }else{
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public SystemEditor selectById(String ID) {
       SystemEditor systemEditor=null;
        if(ID!= null){
            systemEditor=systemEditorMapper.selectByPrimaryKey(ID);
        }
        return systemEditor;
    }

    @Override
    public CodeObject updateById(SystemEditor systemEditor) {
        if(systemEditor!=null){
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            systemEditor.setLastmodifytime(localtime);
            systemEditor.setIsshow("1");
            systemEditorMapper.updateByPrimaryKey(systemEditor);
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return  ReturnCode.UPDATE_FAILED;
        }
    }

    @Override
    public CodeObject delByID(SystemEditor systemEditor) {
        if(systemEditor!=null){
            systemEditor.setIsshow("2");
            systemEditorMapper.deleteByPrimaryKey(systemEditor);
            return ReturnCode.DEL_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }
    }
}
