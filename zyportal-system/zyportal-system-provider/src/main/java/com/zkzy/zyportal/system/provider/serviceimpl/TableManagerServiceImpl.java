package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.portal.common.service.dao.JdbcDao;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.portal.common.utils.StringHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.EvaluationTables;
import com.zkzy.zyportal.system.api.entity.SystemTableProperty;
import com.zkzy.zyportal.system.api.service.EvaluationTablesService;
import com.zkzy.zyportal.system.api.service.SystemTablePropertyService;
import com.zkzy.zyportal.system.api.service.TableManagerService;
import com.zkzy.zyportal.system.provider.mapper.TableManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;

/**
 * Created by Administrator on 2017/6/22 0022.
 */
@Service("tableManagerService")
public class TableManagerServiceImpl extends JdbcDao implements TableManagerService {

    @Autowired
    private TableManagerMapper tableManagerMapper;

    @Autowired
    private EvaluationTablesService evaluationTablesService;

    @Autowired
    private SystemTablePropertyService systemTablePropertyService;

    @Override
    public void createTable(EvaluationTables evaluationTables) throws Exception {
        List<String> coc = toColumnSqlString(evaluationTables);
        List<SystemTableProperty> stps = evaluationTables.getTablePropertys();
        StringBuffer sqlString = new StringBuffer("CREATE TABLE " + evaluationTables.getTablename() + " ( ");
        for (int i = 0; i < stps.size(); i++) {
            SystemTableProperty s = stps.get(i);
            sqlString.append(stp2Sql(s));
            if (i < stps.size() - 1) {
                sqlString.append(",");
            }
        }
        String pks = stp2pk(stps);
        if (!pks.equals("")) {
            sqlString.append(",CONSTRAINT ");
            sqlString.append(evaluationTables.getTablename());
            sqlString.append("_PK PRIMARY KEY (");
            sqlString.append(pks);
            sqlString.append(") ");
        }
        sqlString.append(")");
        super.executeSql(sqlString.toString());
        super.closeAll();
        for (String sql : coc) {
            super.executeSql(sql);
            super.closeAll();
        }
    }

    @Override
    public void dropTable(String tablename) throws Exception {
//        tableManagerMapper.dropTable(tablename);
        executeSql("drop table " + tablename);
        super.closeAll();
    }

    /**
     * ALTER TABLE TEST1
     * DROP COLUMN AGE;
     *
     * @param evaluationTables
     */
    @Override
    public void updateTable(EvaluationTables evaluationTables) throws Exception {
        //1.drop不用的column
        List<SystemTableProperty> drops = systemTablePropertyService.selectDisabled(evaluationTables.getId());
        for (SystemTableProperty s : drops) {
            String sqlString = "ALTER TABLE " + evaluationTables.getTablename() + " DROP COLUMN " + s.getFieldname();
            super.executeSql(sqlString);
            super.closeAll();
        }
        //2.更新字段信息
        List<SystemTableProperty> stps = evaluationTables.getTablePropertys();

        for (SystemTableProperty s : stps) {
            //执行新增字段
            if (s.getDisabled() == 2) {
                String sqlString = "ALTER TABLE " + evaluationTables.getTablename() + " ADD (" + stp2Sql(s) + ")";
                super.executeSql(sqlString);
                super.closeAll();
                s.setDisabled(0);
                systemTablePropertyService.update(s);
            } else if (s.getDisabled() == 3) {
                //执行修改字段
                if (s.getOldfieldname() != null) {
                    String sqlString = "ALTER TABLE " + evaluationTables.getTablename() + " RENAME COLUMN " + s.getOldfieldname() + " TO " + s.getFieldname();
                    super.executeSql(sqlString);
                    super.closeAll();
                }
                StringBuffer sqlString = new StringBuffer("ALTER TABLE " + evaluationTables.getTablename() + " MODIFY (" + s.getFieldname() + " ");
                sqlString.append(s.getDatatype());
                if (s.getDatatype().equals("FLOAT")) {
                    sqlString.append("(" + s.getPointlength() + ")");
                } else if (s.getDatatype().equals("NUMBER")) {
                    sqlString.append("(" + s.getDatalength() + ", " + s.getPointlength() + ")");
                } else {
                    sqlString.append("(" + s.getDatalength() + ")");
                }
                if (s.getDefaultvalue() != null) {
                    sqlString.append(" DEFAULT ");
                    sqlString.append(s.getDefaultvalue());
                }
                String n = s.getIsnull();
                String o = s.getOldisnull();
                if (("Y").equals(o) && ("N").equals(n)) {
                    sqlString.append(" NOT NULL");
                }
                if (("N").equals(o) && ("Y").equals(n)) {
                    sqlString.append(" NULL");
                }
                sqlString.append(")");
                super.executeSql(sqlString.toString());
                super.closeAll();
                s.setDisabled(0);
                systemTablePropertyService.update(s);
            }
        }
        try {
            //废除原主键
            String delpk = "ALTER TABLE " + evaluationTables.getTablename() + " DROP CONSTRAINT " + evaluationTables.getTablename() + "_PK";
            super.executeSql(delpk);
            super.closeAll();
        } catch (Exception e) {

        }
        String pks = stp2pk(stps);
        if (!pks.equals("")) {
            //成立新主键
            String newpk = "ALTER TABLE " + evaluationTables.getTablename() + " ADD CONSTRAINT " + evaluationTables.getTablename() + "_PK PRIMARY KEY ( " + pks + ") ENABLE";
            super.executeSql(newpk);
            super.closeAll();
        }
        //3.修改字段描述
        List<String> coc = toColumnSqlString(evaluationTables);
        for (String sql : coc) {
            super.executeSql(sql);
            super.closeAll();
        }

    }

    @Override
    public EvaluationTables findTableBytablenam(String tablename) {
        return tableManagerMapper.findTableBytablenam(tablename);
    }

    /**
     * <result column="COLUMN_NAME" property="fieldname" jdbcType="VARCHAR"/>
     <result column="COMMENTS" property="fieldesc" jdbcType="VARCHAR"/>
     <result column="DATA_TYPE" property="datatype" jdbcType="VARCHAR"/>
     <result column="DATA_LENGTH" property="datalength" jdbcType="DECIMAL"/>
     <result column="NULLABLE" property="isnull" jdbcType="VARCHAR"/>
     <result column="DATA_DEFAULT" property="defaultvalue" jdbcType="VARCHAR"/>
     <result column="DATA_PRECISION" property="pointlength" jdbcType="DECIMAL"/>
     <result column="DATA_SCALE" property="dataScale" jdbcType="DECIMAL"/>
     <result column="TABLE_NAME" property="tablename" jdbcType="VARCHAR"/>
     <result column="COLUMN_ID" property="columnid" jdbcType="DECIMAL"/>
     <result column="PKEY" property="pkey" jdbcType="DECIMAL"/>
     * @param tablename
     * @return
     */
    public List<SystemTableProperty> getPropreties(String tablename) {
        List<SystemTableProperty> list = new ArrayList<>();
        Object[] obj = {tablename};
        String sqlString = " select a.COLUMN_NAME, b.COMMENTS, a.DATA_TYPE, a.DATA_LENGTH, " +
                "a.NULLABLE, a.DATA_DEFAULT, a.DATA_PRECISION, a.DATA_SCALE, a.TABLE_NAME, a.COLUMN_ID " +
                "from all_tab_cols a, USER_COL_COMMENTS b where a.TABLE_NAME=b.TABLE_NAME " +
                "and a.COLUMN_NAME = b.COLUMN_NAME and  a.TABLE_NAME= ?";
        try {
            ResultSet resultSet = super.executeQuery(sqlString, obj);
            while(resultSet.next()){
                SystemTableProperty systemTableProperty = new SystemTableProperty();
                systemTableProperty.setFieldname(resultSet.getString("COLUMN_NAME"));
                systemTableProperty.setFieldesc(resultSet.getString("COMMENTS"));
                systemTableProperty.setDatatype(resultSet.getString("DATA_TYPE"));
                systemTableProperty.setDatalength(resultSet.getString("DATA_LENGTH")==null?0:Integer.valueOf(resultSet.getString("DATA_LENGTH")));
                systemTableProperty.setIsnull(resultSet.getString("NULLABLE"));
                systemTableProperty.setDefaultvalue(""+resultSet.getString("DATA_DEFAULT"));
                if(resultSet.getString("DATA_PRECISION")!=null){
                    systemTableProperty.setPointlength(Integer.valueOf(resultSet.getString("DATA_PRECISION")));
                }
                if(resultSet.getString("DATA_SCALE")!=null){
                    systemTableProperty.setDataScale(Integer.valueOf(resultSet.getString("DATA_SCALE")));
                }
                systemTableProperty.setTablename(resultSet.getString("TABLE_NAME"));
                systemTableProperty.setColumnid(Integer.valueOf(resultSet.getString("COLUMN_ID")));
                list.add(systemTableProperty);

            }
        } catch (Exception e) {

        }
        super.closeAll();
        return list;
    }


    @Override
    public int synchronousTables() {
        int flag = 0;
        List<EvaluationTables> list = tableManagerMapper.selectTables();
        for (EvaluationTables temp : list) {
            EvaluationTables e = evaluationTablesService.selectByTableName(temp.getTablename());
            if (e == null) {
                e = new EvaluationTables();
                e.setId(RandomHelper.uuid());
                e.setCreatetime(temp.getCreatetime());
                e.setTablename(temp.getTablename());
                e.setModifytime(temp.getModifytime());
                e.setSynchronous((short) 1);
                evaluationTablesService.insertEt(e);
            } else {
                e.setModifytime(temp.getModifytime() == null ? DateHelper.formatDate(new Date()) : temp.getModifytime());
                e.setSynchronous((short) 1);
                evaluationTablesService.updateEt(e);
            }

            List<SystemTableProperty> temps = tableManagerMapper.selectPkey(temp.getTablename());
            //要考虑字段的变化，多了或者少了
            //有一个省事的方法，直接删除原来的记录，新增同步这些记录
            systemTablePropertyService.deleteByTableid(e.getId());
            //获得这张表下的所有字段属性
//            List<SystemTableProperty> stps = tableManagerMapper.getproperties(StringHelper.upperCase(temp.getTablename()));
            List<SystemTableProperty> stps = getPropreties(StringHelper.upperCase(temp.getTablename()));
            for (SystemTableProperty s : stps) {
                if (temps != null || !temps.isEmpty()) {
                    for (SystemTableProperty ss : temps) {
                        s.setPkey(s.getFieldname().equals(ss.getFieldname()) ? 1 : 0);
                    }
                }
                if (s.getDatatype().equals("NUMBER")) {
                    s.setDatalength(s.getPointlength());
                    s.setPointlength(s.getDataScale());
                }
                CodeObject code = systemTablePropertyService.insert(s, e.getTablename(), e.getId());
                if (!code.getCode().equals("0"))
                    flag++;
            }
        }
        return flag;
    }

    @Override
    public List<SystemTableProperty> selectPkey(String tablename) {
        return null;
    }

    public List<String> toColumnSqlString(EvaluationTables evaluationTables) {
        List<SystemTableProperty> stps = evaluationTables.getTablePropertys();
        List<String> coc = new ArrayList<>();
        for (SystemTableProperty s : stps) {
            if (s.getFieldesc() != null) {
                StringBuffer desc = new StringBuffer();
                desc.append("COMMENT ON COLUMN ");
                desc.append(evaluationTables.getTablename());
                desc.append(".");
                desc.append(s.getFieldname());
                desc.append(" IS ");
                desc.append("'" + s.getFieldesc() + "'");
                coc.add(desc.toString());
            }
        }
        return coc;
    }

    public String stp2Sql(SystemTableProperty s) {
        StringBuffer sb = new StringBuffer("");
        sb.append(s.getFieldname() + " " + s.getDatatype());
        if (s.getDatatype().equals("FLOAT")) {
            if (s.getPointlength() != 0) {
                sb.append("(" + s.getPointlength() + ")");
            }
        } else if (s.getDatatype().equals("NUMBER")) {
            if (s.getDatalength() != 0) {
                sb.append("(");
                sb.append(s.getDatalength());
                sb.append(", ");
                sb.append(s.getPointlength());
                sb.append(")");
            }
        } else {
            if (s.getDatalength() != 0) {
                sb.append("(");
                sb.append(s.getDatalength());
                sb.append(")");
            }
        }
        if (s.getDefaultvalue() != null) {
            sb.append(" DEFAULT ");
            sb.append(s.getDefaultvalue());
        }
        if (s.getIsnull().equals("N")) {
            sb.append(" NOT NULL ");
        }
        return sb.toString();
    }

    public String stp2pk(List<SystemTableProperty> stps) {
        List<String> pkeys = new ArrayList<>();
        for (SystemTableProperty s : stps) {
            if (s.getPkey() == 1) {
                pkeys.add(s.getFieldname());
            }
        }
        StringBuffer p = new StringBuffer("");
        if (pkeys.size() > 0) {
            for (int i = 0; i < pkeys.size(); i++) {
                p.append(pkeys.get(i));
                if (i < pkeys.size() - 1) {
                    p.append(",");
                }
            }
        }
        return p.toString();
    }

//    public String toSqlString(EvaluationTables evaluationTables) {
//        StringBuffer sb = new StringBuffer("");
//        List<SystemTableProperty> stps = evaluationTables.getTablePropertys();
//        List<String> pkeys = new ArrayList<>();
//        for (int i = 0; i < stps.size(); i++) {
//            SystemTableProperty s = stps.get(i);
//            sb.append(s.getFieldname() + " " + s.getDatatype());
//            if (s.getDatatype().equals("FLOAT")) {
//                if (s.getPointlength() != 0) {
//                    sb.append("(" + s.getPointlength() + ")");
//                }
//            } else if (s.getDatatype().equals("NUMBER")) {
//                if (s.getDatalength() != 0) {
//                    sb.append("(");
//                    sb.append(s.getDatalength());
//                    sb.append(", ");
//                    sb.append(s.getPointlength());
//                    sb.append(")");
//                }
//            } else {
//                if (s.getDatalength() != 0) {
//                    sb.append("(");
//                    sb.append(s.getDatalength());
//                    sb.append(")");
//                }
//            }
//            if (s.getDefaultvalue() != null) {
//                sb.append(" DEFAULT ");
//                sb.append(s.getDefaultvalue());
//            }
//            if (s.getIsnull().equals("N")) {
//                sb.append(" NOT NULL ");
//            }
//            if (i < stps.size() - 1) {
//                sb.append(",");
//            }
//            if (s.getPkey() == 1) {
//                pkeys.add(s.getFieldname());
//            }
//        }
//        if (pkeys.size() > 0) {
//            StringBuffer p = new StringBuffer();
//            for (int i = 0; i < pkeys.size(); i++) {
//                p.append(pkeys.get(i));
//                if (i < pkeys.size() - 1) {
//                    p.append(",");
//                }
//            }
//            sb.append(",CONSTRAINT ");
//            sb.append(evaluationTables.getTablename());
//            sb.append("_PK PRIMARY KEY (");
//            sb.append(p.toString());
//            sb.append(") ");
//        }
//        return sb.toString();
//    }


}
