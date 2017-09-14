package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.PgdaService;
import com.zkzy.zyportal.system.provider.mapper.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Service("pgdaService")
public class PgdaServiceImpl implements PgdaService {
    @Autowired
    private EvaluationMapper evaluationMapper;
    @Autowired
    private EvaluationOldmaninfoMapper evaluationOldmaninfoMapper;
    @Autowired
    private EvaluationRecentMapper evaluationRecentMapper;
    @Autowired
    private EvaluationReportMapper evaluationReportMapper;
    @Autowired
    private EvaluationPointMapper evaluationPointMapper;


    /**
     * 根据老人id获得评估档案信息
     * @param oldManid
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> getPgdaInfoList(String oldManid, String param,int currentPage,int pageSize) {
        if(param==null){
            param="";
        }
        param+=" and code='"+oldManid+"' and STATUS='1' ";//已完成
        Map<String, Object> info=new HashedMap();
        Map<String, Object> infoDetails=new HashedMap();
        try {
            PageHelper.startPage(currentPage,pageSize);//分页
            List<Evaluation> baseList=evaluationMapper.selectAll(param);
            for(Evaluation evaluation:baseList){
                Map<String,Object> oneMap=new HashedMap();
                String oldManId=evaluation.getBasicId();//老人基本信息id
                String oldRecentId=evaluation.getRecentId();//老人近况id
                String oldReportId=evaluation.getReportId();//评估报告id
                String questionId=evaluation.getQuestionId();//问题的编号
                //老人基本信息
                EvaluationOldmaninfo evaluationOldmaninfo=evaluationOldmaninfoMapper.selectByPrimaryKey(oldManId);
                //近况信息
                EvaluationRecent evaluationRecent=evaluationRecentMapper.selectByPrimaryKey(oldRecentId);
                //评测报告
                EvaluationReport evaluationReport=evaluationReportMapper.selectByPrimaryKey(oldReportId);
                //问题
                EvaluationPoint evaluationPoint=evaluationPointMapper.selectByPrimaryKey(questionId);

                oneMap.put("evaluationOldmaninfo",evaluationOldmaninfo);
                oneMap.put("evaluationRecent",evaluationRecent);
                oneMap.put("evaluationReport",evaluationReport);
                oneMap.put("evaluationPoint",evaluationPoint);

                infoDetails.put(evaluation.getId(),oneMap);

            }
            PageInfo pageInfo=new PageInfo(baseList);
            info.put("baselist",pageInfo);//基本的信息主表
            info.put("infoDetails",infoDetails);//详细数据
            return info;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {

        }
    }
}
