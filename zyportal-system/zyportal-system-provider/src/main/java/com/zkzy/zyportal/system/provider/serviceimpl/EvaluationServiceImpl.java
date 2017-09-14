package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.EvaluationService;
import com.zkzy.zyportal.system.api.service.OldManInfoService;
import com.zkzy.zyportal.system.provider.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

@Service("evaluationService")
public class EvaluationServiceImpl implements EvaluationService{
    @Autowired
    private EvaluationMapper evaluationMapper;
    @Autowired
    private EvaluationOldmaninfoMapper evaluationOldmaninfoMapper;
    @Autowired
    private EvaluationRecentMapper evaluationRecentMapper;
    @Autowired
    private EvaluationPointMapper evaluationPointMapper;
    @Autowired
    private EvaluationReportMapper evaluationReportMapper;


    @Override
    public String saveEvaluation(Evaluation evaluation) {
        String id = RandomHelper.uuid();
        try {
            evaluation.setId(id);
            evaluationMapper.insert(evaluation);
        } catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public String saveOldManInfo(EvaluationOldmaninfo evaluationOldmaninfo) {
        String id = RandomHelper.uuid();
        try {
            if(evaluationOldmaninfo.getId()==null){
                evaluationOldmaninfo.setId(id);
                evaluationOldmaninfoMapper.insert(evaluationOldmaninfo);
            }else{
                evaluationOldmaninfoMapper.updateByPrimaryKey(evaluationOldmaninfo);
            }
        } catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public String saveRecent(EvaluationRecent evaluationRecent) {
        String id = RandomHelper.uuid();
        try {
            if(evaluationRecent.getId()==null){
                evaluationRecent.setId(id);
                evaluationRecentMapper.insert(evaluationRecent);
            }else{
                evaluationRecentMapper.updateByPrimaryKey(evaluationRecent);
            }
        } catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public String savePoint(EvaluationPoint evaluationPoint) {
        String id = RandomHelper.uuid();
        try {

            if(evaluationPoint.getId()==null){
                evaluationPoint.setId(id);
                evaluationPointMapper.insert(evaluationPoint);
            }else{
                evaluationPointMapper.updateByPrimaryKey(evaluationPoint);
            }
        } catch (Exception e){
            return null;
        }
        return id;
    }

    @Override
    public String saveReport(EvaluationReport evaluationReport) {
        String id = RandomHelper.uuid();
        try {
            if(evaluationReport.getId()==null){
                evaluationReport.setId(id);
                evaluationReportMapper.insert(evaluationReport);
            }else{
                evaluationReportMapper.updateByPrimaryKey(evaluationReport);
            }
        } catch (Exception e){
            return null;
        }
        return id;
    }
}
