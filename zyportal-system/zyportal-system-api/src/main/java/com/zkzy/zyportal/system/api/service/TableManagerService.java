package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.EvaluationTables;
import com.zkzy.zyportal.system.api.entity.SystemTableProperty;

import java.util.List;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
public interface TableManagerService {
    int synchronousTables();

    List<SystemTableProperty> selectPkey(String tablename);

    void createTable(EvaluationTables evaluationTables) throws Exception;

    void dropTable(String tablename) throws Exception;

    void updateTable(EvaluationTables evaluationTables) throws Exception;

    EvaluationTables findTableBytablenam(String tablename);
}
