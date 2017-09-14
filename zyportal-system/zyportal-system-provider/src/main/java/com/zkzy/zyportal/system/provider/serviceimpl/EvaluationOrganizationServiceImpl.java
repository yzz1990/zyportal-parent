package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.EvaluationOrganization;
import com.zkzy.zyportal.system.api.entity.EvaluationPerson;
import com.zkzy.zyportal.system.api.service.EvaluationOrganizationService;
import com.zkzy.zyportal.system.provider.mapper.EvaluationOrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by admin on 2017/5/10.
 */
@Service("evaluationOrganizationService")
public class EvaluationOrganizationServiceImpl implements EvaluationOrganizationService {
    @Autowired
    private EvaluationOrganizationMapper evaluationOrganizationMapper;

    @Override
    public CodeObject InsertDepartment(EvaluationOrganization department) {
        if(department !=null){
            List<EvaluationOrganization> list= evaluationOrganizationMapper.selectByDepertment(department);
            if(list.size()>0){
                return  ReturnCode.INSERT_FAILED;
            }
            Date time=new Date();
            String localtime=formatDateTime(time);
            department.setId(uuid());
            department.setCreatetime(localtime);
            department.setModifytime(localtime);
            evaluationOrganizationMapper.insert(department);
            return ReturnCode.AREA_SUCCESS;
        }else{
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject DeleteById(String ID) {
        if(ID!=null){
            evaluationOrganizationMapper.deleteByPrimaryKey(ID);
            return ReturnCode.DEL_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }

    }

    @Override
    public CodeObject UpdateById(EvaluationOrganization department) {
        if(department !=null){
            Date time=new Date();
            String localtime=formatDateTime(time);
            department.setModifytime(localtime);
            evaluationOrganizationMapper.updateByPrimaryKey(department);
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return ReturnCode.UPDATE_FAILED;
        }
    }

    @Override
    public EvaluationOrganization SelectById(String ID) {
        EvaluationOrganization department=null;
        if(ID!=null){
            department=evaluationOrganizationMapper.selectByPrimaryKey(ID);
        }
        return department;
    }

    @Override
    public PageInfo SelectAll(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);//分页
        List<EvaluationOrganization> list=evaluationOrganizationMapper.selectAll();
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
    @Override
    public List<EvaluationOrganization> SelectName() {
        return evaluationOrganizationMapper.selectDepertment();
    }

}
