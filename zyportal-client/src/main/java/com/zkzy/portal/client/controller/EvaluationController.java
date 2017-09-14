package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.*;
import com.zkzy.zyportal.system.api.service.EvaluationService;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * The type Sys menu controller.
 *
 * @author admin
 */
@Validated
@RestController
@RequestMapping("/system/eva")
@Api(tags="老人评测")
public class EvaluationController extends BaseController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping(value = "/add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "保存老人基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> add(
            @ApiParam(required = true, value = "基本信息") @RequestParam("basic") String basic,
            @ApiParam(required = true, value = "老人近况") @RequestParam("recent") String recent,
            @ApiParam(required = true, value = "评测分数") @RequestParam("scale") String scale,
            @ApiParam(required = true, value = "评测报告") @RequestParam("report") String report,
            @ApiParam(required = true, value = "评测状态") @RequestParam("status") String status,
            @ApiParam(required = true, value = "评测次数") @RequestParam("count") String count


    ) throws InvocationTargetException, IllegalAccessException {
        Evaluation evaluation=new Evaluation();
        EvaluationOldmaninfo evaluationOldmaninfo=new EvaluationOldmaninfo();
        EvaluationRecent evaluationRecent=new EvaluationRecent();
        EvaluationPoint evaluationPoint=new EvaluationPoint();
        EvaluationReport evaluationReport =new EvaluationReport();

        analysis(basic,evaluationOldmaninfo);
        analysis(recent,evaluationRecent);
        analysis(scale,evaluationPoint);
        analysis(report,evaluationReport);
        evaluation.setCode(evaluationOldmaninfo.getId());
        evaluationOldmaninfo.setId(null);
        String basic_id=evaluationService.saveOldManInfo(evaluationOldmaninfo);
        String recent_id=evaluationService.saveRecent(evaluationRecent);
        String point_id=evaluationService.savePoint(evaluationPoint);
        evaluationReport.setAgency("1");
        evaluationReport.setAssessor("2");
        String report_id=evaluationService.saveReport(evaluationReport);
        evaluation.setBasicId(basic_id);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        evaluation.setCreateTime(sdf.format(new Date()));
        evaluation.setQuestionId(point_id);
        evaluation.setRecentId(recent_id);
        evaluation.setReportId(report_id);
        evaluation.setStatus(status);
        evaluation.setCount(count);
        evaluationService.saveEvaluation(evaluation);
        Map<String,Object> message=makeMessage(ReturnCode.SUCCESS);
        return message;
    }

    private void analysis(String param,Object o){

        String [] arr=param.split("&amp;");
        if(arr.length==0){
            return;
        }
        for(String p:arr){
            int index=p.indexOf("=");
            String key=p.substring(0,index);
            String value=p.substring(index+1);
            try {
                BeanUtils.setProperty(o,key,value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
