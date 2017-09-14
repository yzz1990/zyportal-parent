package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.entity.*;

public interface EvaluationService {
    public String saveEvaluation(Evaluation evaluation);
    public String saveOldManInfo(EvaluationOldmaninfo evaluationOldmaninfo);
    public String saveRecent(EvaluationRecent evaluationRecent);
    public String savePoint(EvaluationPoint evaluationPoint);
    public String saveReport(EvaluationReport evaluationReport);
}
