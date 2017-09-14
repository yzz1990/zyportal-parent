package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.service.dao.JdbcDao;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.EvaluationTables;
import com.zkzy.zyportal.system.api.service.EvaluationTablesService;
import com.zkzy.zyportal.system.provider.mapper.EvaluationTablesMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Service("evaluationTablesService")
public class EvaluationTablesServiceImpl extends JdbcDao implements EvaluationTablesService {

    @Autowired
    EvaluationTablesMapper evaluationTablesMapper;


    /**
     * UPDATE TEST1 SET PASSWORD = '1132' WHERE ID='1221' and AGE='1' and USERNAME='2' and PASSWORD='12';
     *
     * @param tablename
     * @param fieldname
     * @param value
     * @param row
     * @return
     */
    @Override
    public CodeObject changDataValue(String tablename, String fieldname, String value, Map<String, Object> row) throws Exception {
        StringBuffer sqlString = new StringBuffer("UPDATE " + tablename + " SET " + fieldname + " = ? WHERE ");
        Object[] obj = new Object[row.size() + 1];
        obj[0] = value;
        int a = 1;
        for (String key : row.keySet()) {
            if (row.get(key).equals("") || row.get(key) == null) {
//                sqlString.append(key + " IS NULL AND ");
            } else {
                obj[a] = row.get(key);
                sqlString.append(key + " = ? " + " AND ");
                a++;
            }
        }
        String sql = sqlString.substring(0, sqlString.length() - 5);
        int i = super.executeUpdate(sql, obj);
        super.closeAll();
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }

    /**
     * DELETE FROM "PLATFORM"."TEST1" WHERE ROWID = 'AAAWCKAAEAAAAU0AAA' AND ORA_ROWSCN = '7398087' and ( "ID" is null or "ID" is not null )
     *
     * @param tablename
     * @param row
     * @return
     * @throws Exception
     */
    @Override
    public CodeObject deleteData(String tablename, Map<String, Object> row) throws Exception {
        StringBuffer stringBuffer = new StringBuffer("DELETE FROM " + tablename + " WHERE ");
        Object[] obj = new Object[row.size()];
        int a = 0;
        for (String key : row.keySet()) {
            if (row.get(key).equals("") || row.get(key) == null) {
//                stringBuffer.append(key + " IS NULL AND ");
            } else {
                obj[a] = row.get(key);
                stringBuffer.append(key + " = ? " + " AND ");
                a++;
            }
        }
        String sql = stringBuffer.substring(0, stringBuffer.length() - 5);
        int i = 0;
        try {
            i = super.executeUpdate(sql, obj);
        } catch (Exception e) {
            throw e;
        } finally {
            super.closeAll();
        }
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }


    /**
     * INSERT INTO "PLATFORM"."TEST1" (ID, USERNAME, PASSWORD, ROLE, LVL) VALUES ('A0000001', 'admin1', 'admin', 'a', '1')
     */

    @Override
    public CodeObject insertData(String tablename, Map<String, Object> row) throws Exception {
        StringBuffer stringBuffer = new StringBuffer("INSERT INTO " + tablename + " ( ");
        Object[] obj = new Object[row.size()];
        int a = 0;
        String fields = "";
        String values = "";
        for (String key : row.keySet()) {
            fields += key + ",";
            values += "?,";
            obj[a] = row.get(key);
            a++;
        }
        fields = fields.substring(0, fields.length() - 1);
        values = values.substring(0, values.length() - 1);
        stringBuffer.append(fields + " ) VALUES (" + values + ")");
        int i = super.executeUpdate(stringBuffer.toString(), obj);
        super.closeAll();
        if (i > 0)
            return ReturnCode.SUCCESS;
        else
            return ReturnCode.FAILED;
    }

    /**
     * SELECT * FROM
     * (
     * SELECT A.*, ROWNUM RN
     * FROM (SELECT * FROM TABLE_NAME) A
     * WHERE ROWNUM <= 40
     * )
     * WHERE RN >= 21
     * currentPage*pageSize+1
     * (currentPage-1)*pageSize
     *
     * @param
     * @param
     * @param tablename
     * @return
     */
//    @Override
//    public PageInfo getData(int currentPage, int pageSize, String tablename) throws Exception {
//        PageHelper.startPage(currentPage, pageSize);
//        int start = currentPage * pageSize + 1;
//        int end = (currentPage - 1) * pageSize;
//        String sqlString = "SELECT * FROM ( SELECT A.*, ROWNUM RN FROM (SELECT * FROM " + tablename + ") A  \n" +
//                "WHERE ROWNUM <= " + start + "  ) WHERE RN >= " + end;
//        ResultSet resultSet = null;
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        try {
//            resultSet = executeQuery(sqlString, null);
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int columnCount = metaData.getColumnCount();
//
//            while (resultSet.next()) {
//                Map map = new HashMap<String, Object>();
//                for (int i = 0; i < columnCount; i++) {
//                    String columnName = metaData.getColumnName(i + 1);
//                    Object value = resultSet.getObject(columnName);
//                    map.put(columnName, value);
//                }
//                list.add(map);
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            closeAll();
//        }
//
//        PageInfo pageInfo = new PageInfo(list);
//        return pageInfo;
//    }
    @Override
    public List<Map<String, Object>> getData(String tablename, String[] fields) throws Exception {
        String f = "";
        if (fields != null || fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                f += fields[i] + ",";
            }
        }
        if (f != "") {
            f = f.substring(0, f.length() - 1);
        } else {
           return null;
        }
        String sqlString = "SELECT " + f + " FROM  " + tablename;
        ResultSet resultSet = null;
        List<Map<String, Object>> list = new ArrayList<>();
        resultSet = super.executeQuery(sqlString, null);
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            Map map = new HashMap<String, Object>();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Object value = resultSet.getObject(columnName);
                map.put(columnName, value);
            }
            list.add(map);
        }
        super.closeAll();
        return list;
    }

    @Override
    public CodeObject insertEt(EvaluationTables evaluationTables) {

        int i = evaluationTablesMapper.insert(evaluationTables);
        if (i > 0) {
            return ReturnCode.SUCCESS;
        } else {
            return ReturnCode.FAILED;
        }
    }

    @Override
    public CodeObject updateEt(EvaluationTables evaluationTables) {
        String time = DateHelper.formatDateTime(new Date());
        evaluationTables.setModifytime(time);
        int i = evaluationTablesMapper.updateByPrimaryKey(evaluationTables);
        if (i > 0) {
            return ReturnCode.SUCCESS;
        } else {
            return ReturnCode.UPDATE_FAILED;
        }
    }

    @Override
    public CodeObject saveOrUpdate(EvaluationTables evaluationTables) {
        if (evaluationTables == null)
            return ReturnCode.UPDATE_FAILED;
        EvaluationTables et = evaluationTablesMapper.selectByTableName(evaluationTables.getTablename());
        if (et != null) {
            evaluationTables.setId(et.getId());
            String time = DateHelper.formatDateTime(new Date());
            evaluationTables.setModifytime(time);
            int i = evaluationTablesMapper.updateByPrimaryKey(evaluationTables);
            if (i > 0) {
                return ReturnCode.SUCCESS;
            } else {
                return ReturnCode.UPDATE_FAILED;
            }
        } else {
            evaluationTables.setId(RandomHelper.uuid());
            String time = DateHelper.formatDateTime(new Date());
            evaluationTables.setCreatetime(time);
            evaluationTables.setModifytime(time);
            int i = evaluationTablesMapper.insert(evaluationTables);
            if (i > 0) {
                return ReturnCode.SUCCESS;
            } else {
                return ReturnCode.INSERT_FAILED;
            }
        }
    }

    @Override
    public CodeObject deleteById(String id) {
        int i = evaluationTablesMapper.deleteByPrimaryKey(id);
        if (i > 0) {
            return ReturnCode.SUCCESS;
        } else {
            return ReturnCode.INSERT_FAILED;
        }
    }

    @Override
    public EvaluationTables searchById(String id) {
        return evaluationTablesMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo getTablesList(int currentPage, int pageSize, String sqlParam) {
        String sql = null;
        if (sqlParam != null || sqlParam != "") {
            sql = "tablename like '%" + sqlParam + "%'or tabledesc like '%" + sqlParam + "%'";
        }
        PageHelper.startPage(currentPage, pageSize);//分页
        List<EvaluationTables> list = evaluationTablesMapper.selectAll(sql);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public EvaluationTables selectByTableName(String tablename) {
        return evaluationTablesMapper.selectByTableName(tablename);
    }

    @Override
    public PageInfo seachByLike(int currentPage, int pageSize, String searchinfo) {
        PageHelper.startPage(currentPage, pageSize);//分页
        List<EvaluationTables> list = evaluationTablesMapper.like(searchinfo);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

}
