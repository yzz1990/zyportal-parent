package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.EvaluationTables;
import com.zkzy.zyportal.system.api.entity.SystemTableProperty;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TableManagerMapper {

    List<EvaluationTables> selectTables();

    List<SystemTableProperty> getproperties(String tablename);

    List<SystemTableProperty> selectPkey(String tablename);

    EvaluationTables findTableBytablenam(String tablename);

    int dropColumn(String tablename , String columnName);

    int dropTable(String tablename);

}