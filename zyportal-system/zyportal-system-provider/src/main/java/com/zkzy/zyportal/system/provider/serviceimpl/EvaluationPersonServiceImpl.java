package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.EvaluationPerson;
import com.zkzy.zyportal.system.api.service.EvaluationPersonService;
import com.zkzy.zyportal.system.provider.mapper.EvaluationPersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by admin on 2017/5/10.
 */
@Service("evaluationPersonService")
public class EvaluationPersonServiceImpl implements EvaluationPersonService {
    @Autowired
    private EvaluationPersonMapper evaluationPersonMapper;

    @Override
    public CodeObject InsertName(EvaluationPerson department) {
        if(department !=null){
            List<EvaluationPerson> list= evaluationPersonMapper.selectByName(department);
            if(list.size()>0){
                return  ReturnCode.INSERT_FAILED;
            }
            Date time=new Date();
            String localtime=formatDateTime(time);
            department.setId(uuid());
            department.setCreatetime(localtime);
            department.setModifytime(localtime);
            evaluationPersonMapper.insert(department);
            return ReturnCode.AREA_SUCCESS;
        }else{
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject DeleteById(String ID) {
        if(ID!=null){
            evaluationPersonMapper.deleteByPrimaryKey(ID);
            return ReturnCode.DEL_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }

    }

    @Override
    public CodeObject UpdateById(EvaluationPerson name) {
        if(name !=null){
            Date time=new Date();
            String localtime=formatDateTime(time);
            name.setModifytime(localtime);
            evaluationPersonMapper.updateByPrimaryKey(name);
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return ReturnCode.UPDATE_FAILED;
        }
    }

   @Override
    public EvaluationPerson SelectById(String ID) {
       EvaluationPerson name=null;
        if(ID!=null){
           name=evaluationPersonMapper.selectByPrimaryKey(ID);
        }
        return name;
    }


    @Override
    public PageInfo SelectAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);//分页
        List<EvaluationPerson> list=evaluationPersonMapper.selectAll();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
    @Override
    public List<EvaluationPerson> SelectName() {
        return evaluationPersonMapper.selectName();
    }

}
