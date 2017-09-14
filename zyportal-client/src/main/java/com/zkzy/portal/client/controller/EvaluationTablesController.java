package com.zkzy.portal.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.portal.common.utils.StringHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.EvaluationTables;
import com.zkzy.zyportal.system.api.entity.SystemTableProperty;
import com.zkzy.zyportal.system.api.service.EvaluationTablesService;
import com.zkzy.zyportal.system.api.service.SystemTablePropertyService;
import com.zkzy.zyportal.system.api.service.TableManagerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Validated
@RestController
@RequestMapping(value = "/tables/administration")
@Api(tags = "数据库表管理controller")
public class EvaluationTablesController extends BaseController {

    @Autowired
    private EvaluationTablesService evaluationTablesService;

    @Autowired
    private SystemTablePropertyService systemTablePropertyService;

    @Autowired
    private TableManagerService tableManagerService;

    /**
     * 获得所有表
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @PreAuthorize("hasAuthority('getTables')")
    @GetMapping(value = "/getTables", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取所有表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public GridModel getTables(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @RequestParam(name = "sqlParam", required = false, defaultValue = "") String sqlParam
    ) {
        PageInfo pageInfo = evaluationTablesService.getTablesList(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    @PostMapping(value = "/getTableProperty", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取该表字段")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> getTableProperty(
            @RequestParam(name = "id", required = true, defaultValue = "") String id,
            @RequestParam(name = "toshow", required = false, defaultValue = "false") String toshow
    ) {
        EvaluationTables evaluationTables = evaluationTablesService.searchById(id);
        List<SystemTableProperty> stps = null;
        if (evaluationTables != null) {
            if (!Boolean.valueOf(toshow)) {
                stps = systemTablePropertyService.selectByTableid(evaluationTables.getId());
            } else {
                stps = systemTablePropertyService.selectToShow(evaluationTables.getId());
            }
            evaluationTables.setTablePropertys(stps);
        }
        Map<String, Object> message = makeMessage(ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, evaluationTables);
        return message;
    }

    /**
     * SystemTableProperty中的disabled代表4种状态
     * 0.正常、1.删除、2.新增、3.修改
     * 1状态不会显示在列表中,其他状态用来标记该条属性的现实状态
     *
     * @param tableid
     * @param tablename
     * @param tabledesc
     * @param tablePropertys
     * @param delist
     * @return
     */
    @PreAuthorize("hasAuthority('saveOrUpdateTable')")
    @PostMapping(value = "/saveOrUpdateTable", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增或更新表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> saveOrUpdate(@RequestParam(name = "tableid", required = false, defaultValue = "") String tableid,
                                            @RequestParam(name = "tablename", required = true) String tablename,
                                            @RequestParam(name = "tabledesc", required = true) String tabledesc,
                                            @RequestParam(name = "tablePropertys", required = true, defaultValue = "") String tablePropertys,
                                            @RequestParam(name = "delist", required = false, defaultValue = "") String delist) {
        EvaluationTables evaluationTables = evaluationTablesService.searchById(tableid);
        //页面回传的字段
        List<SystemTableProperty> stps = JSONArray.parseArray(tablePropertys, SystemTableProperty.class);
        String time = DateHelper.formatDateTime(new Date());
        Map<String, Object> message = null;
        int flag = 0;
        if (evaluationTables != null) {
            evaluationTables.setModifytime(time);
            evaluationTables.setTabledesc(tabledesc);
            if (!delist.equals("")) {
                String delids[] = delist.split(",");
                for (String id : delids) {
                    systemTablePropertyService.disableById(id);
                }
                if (evaluationTables.getSynchronous() != 2) {
                    evaluationTables.setSynchronous((short) 0);
                }
            }
            for (SystemTableProperty s : stps) {
                SystemTableProperty systemTableProperty = systemTablePropertyService.selectByPrimaryKey(s.getId());
                //s是页面传回的新数据，systemTableProperty是老数据------和数据库现有的字段做比较
                if (systemTableProperty != null) {
                    //不是空的就修改这条数据
                    s.setFieldname(StringHelper.upperCase(s.getFieldname()));
                    s.setTableid(evaluationTables.getId());
                    s.setTablename(evaluationTables.getTablename());
                    //新数据中的old字段是老数据中的正常字段
                    //新旧数据校对，false放到old字段中
                    int doup = 0;
                    if (!s.getFieldname().equals(systemTableProperty.getFieldname())) {
                        s.setOldfieldname(systemTableProperty.getFieldname());
                        doup++;
                    }
                    if (!s.getDatatype().equals(systemTableProperty.getDatatype())) {
                        s.setOldatatype(systemTableProperty.getOldatatype());
                        doup++;
                    }
                    if (s.getDatalength() != systemTableProperty.getDatalength()) {
                        s.setOldatalength(systemTableProperty.getDatalength());
                        doup++;
                    }
                    if (!s.getIsnull().equals(systemTableProperty.getIsnull())) {
                        s.setOldisnull(systemTableProperty.getIsnull());
                        doup++;
                    }
                    if (systemTableProperty.getDefaultvalue() == null) {
                        systemTableProperty.setDefaultvalue("");
                    }
                    if (!s.getDefaultvalue().equals(systemTableProperty.getDefaultvalue())) {
                        s.setOldefaultvalue(systemTableProperty.getDefaultvalue());
                        doup++;
                    }
                    if (s.getPkey() != systemTableProperty.getPkey()) {
                        s.setOldpkey(systemTableProperty.getPkey());
                        doup++;
                    }
                    if (s.getPointlength() != systemTableProperty.getPointlength()) {
                        s.setOldpointlength(systemTableProperty.getPointlength());
                        doup++;
                    }
                    if (doup > 0 && s.getDisabled() != 2) {
                        s.setDisabled(3);
                    }
                    if (doup > 0 && evaluationTables.getSynchronous() != 2) {
                        evaluationTables.setSynchronous((short) 0);
                    }
                    s.setToShow(systemTableProperty.getToShow());
                    //更新
                    CodeObject code = systemTablePropertyService.update(s);
                    if (!code.getCode().equals("0"))
                        flag++;
                } else {
                    //2是新增
                    s.setDisabled(2);
                    CodeObject c2 = systemTablePropertyService.insert(s, evaluationTables.getTablename(), evaluationTables.getId());
                    if (!c2.getCode().equals("0"))
                        flag++;
                    if (evaluationTables.getSynchronous() != 2) {
                        evaluationTables.setSynchronous((short) 0);
                    }
                }
            }
            evaluationTablesService.updateEt(evaluationTables);

        } else {
            //新增table
            evaluationTables = new EvaluationTables();
            evaluationTables.setId(RandomHelper.uuid());
            evaluationTables.setTablename(StringHelper.upperCase(tablename));
            evaluationTables.setTabledesc(tabledesc);
            evaluationTables.setCreatetime(time);
            evaluationTables.setModifytime(time);
            evaluationTables.setSynchronous((short) 2);
            CodeObject code1 = evaluationTablesService.insertEt(evaluationTables);
            if (!code1.getCode().equals("0"))
                return makeMessage(ReturnCode.AREA_FAILED);
            for (SystemTableProperty s : stps) {
                CodeObject c1 = systemTablePropertyService.insert(s, tablename, evaluationTables.getId());
                if (!c1.getCode().equals("0"))
                    flag++;
            }
        }
        if (flag > 0) {
            message = makeMessage(ReturnCode.AREA_FAILED);
            message.put("num", flag);
        } else {
            message = makeMessage(ReturnCode.AREA_SUCCESS);
            message.put("num", flag);
        }
        return message;
    }

    @GetMapping(value = "/checkTableName", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "检查表名是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> checkTableName(@RequestParam(name = "tablename") String tablename) {
        EvaluationTables evaluationTables = evaluationTablesService.selectByTableName(StringHelper.upperCase(tablename));
        Map<String, Object> message = null;
        if (evaluationTables == null)
            message = makeMessage(ReturnCode.SUCCESS);
        else
            message = makeMessage(ReturnCode.FAILED);
        return message;
    }


    @GetMapping(value = "/likeTable", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "模糊查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public GridModel likeTable(@RequestParam(name = "serachinfo") String serachinfo,
                               @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
                               @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize) {
        PageInfo pageInfo = evaluationTablesService.seachByLike(Integer.valueOf(pageNumber), Integer.valueOf(pageSize), serachinfo);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    @PreAuthorize("hasAuthority('delTable')")
    @DeleteMapping(value = "/delTable", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> delTable(
            @RequestParam(name = "id", required = true, defaultValue = "") String id,
            @RequestParam(name = "type", required = true, defaultValue = "F") String type) {
        EvaluationTables evaluationTables = evaluationTablesService.searchById(id);
        Map<String, Object> message = null;
        systemTablePropertyService.deleteByTableid(id);
        evaluationTablesService.deleteById(id);
        message = makeMessage(ReturnCode.DEL_SUCCESS);
        message.put("message", "");
        /**
         * 此处执行数据库内真删除表
         */
        if (type.equals("T")) {
            try {
                tableManagerService.dropTable(evaluationTables.getTablename());
            } catch (Exception e) {
                message = makeMessage(ReturnCode.DEL_FAILED);
                message.put("message", e.getMessage());
            }
        }
        return message;
    }

    @PreAuthorize("hasAuthority('synchronousTables')")
    @PostMapping(value = "/synchronousTables", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "同步数据库")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> synchronousTables(
            @RequestParam(name = "command", required = true, defaultValue = "false") String command) {
        Map<String, Object> message = null;
        if (Boolean.valueOf(command)) {
            int i = tableManagerService.synchronousTables();
            message = makeMessage(ReturnCode.SUCCESS);
            message.put("num", i);
        }
        return message;
    }
    @PreAuthorize("hasAuthority('createTable')")
    @PostMapping(value = "/createTable", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "数据库创建或更新表")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> createTable(
            @RequestParam(name = "tableid", required = true, defaultValue = "") String tableid) {

        Map<String, Object> message = null;
        EvaluationTables evaluationTables = evaluationTablesService.searchById(tableid);
        if (evaluationTables.getSynchronous() == 1) {
            message = makeMessage(ReturnCode.SUCCESS);
            return message;
        }
        List<SystemTableProperty> stps = systemTablePropertyService.selectByTableid(tableid);
        evaluationTables.setTablePropertys(stps);
        //查询数据库表是否存在
        EvaluationTables et = tableManagerService.findTableBytablenam(evaluationTables.getTablename());
        if (et == null) {
            try {
                tableManagerService.createTable(evaluationTables);
                evaluationTables.setSynchronous((short) 1);
                evaluationTablesService.updateEt(evaluationTables);
                message = makeMessage(ReturnCode.SUCCESS);
            } catch (Exception e) {
                message = makeMessage(ReturnCode.AREA_FAILED);
                message.put("error", e.getMessage());
            }
        } else {
            try {
                tableManagerService.updateTable(evaluationTables);
                evaluationTables.setSynchronous((short) 1);
                evaluationTablesService.updateEt(evaluationTables);
                message = makeMessage(ReturnCode.SUCCESS);
            } catch (Exception e) {
                message = makeMessage(ReturnCode.AREA_FAILED);
                message.put("error", e.getMessage());
            }

        }

        return message;
    }

    @PreAuthorize("hasAuthority('getData')")
    @GetMapping(value = "/getData", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取表内数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    public Map<String, Object> getData(
            @RequestParam(name = "pageNumber", required = true, defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize", required = true, defaultValue = "10") String pageSize,
            @RequestParam(name = "tablename", required = true, defaultValue = "") String tablename,
            @RequestParam(name = "tableId", required = true, defaultValue = "") String tableId
    ) {
        Map<String, Object> message = null;
        List<SystemTableProperty> stps = systemTablePropertyService.selectToShow(tableId);
        if(stps == null){
            message = makeMessage(ReturnCode.SUCCESS);
            message.put("data", null);
            return message;
        }
        String[] strings = new String[stps.size()];
        int index = 0;
        for (SystemTableProperty s : stps) {
            strings[index] = s.getFieldname();
            index++;
        }
        try {
            List<Map<String, Object>> list = evaluationTablesService.getData(tablename, strings);
            message = makeMessage(ReturnCode.SUCCESS);
            message.put("data", list);
        } catch (Exception e) {
            message = makeMessage(ReturnCode.FAILED);
            message.put("data", "");
        }
        return message;
    }


    /**
     * ,'pkeyField':pkeyField,'pkeyValue':pkeyValue
     *
     * @param
     * @param
     * @param
     * @return
     */
    @PreAuthorize("hasAuthority('UpdateData')")
    @PostMapping(value = "/UpdateData", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> UpdateData(
            @RequestParam(name = "tablename", required = true, defaultValue = "") String tablename,
            @RequestParam(name = "fieldname", required = true, defaultValue = "") String fieldname,
            @RequestParam(name = "value", required = true, defaultValue = "") String value,
            @RequestParam(name = "row", required = true, defaultValue = "") String row
    ) {
        Map<String, Object> message = null;
        Map<String, Object> params = JSONObject.parseObject(row, new TypeReference<Map<String, Object>>() {
        });
        params.remove("state");
        try {
            evaluationTablesService.changDataValue(tablename, fieldname, value, params);
            message = makeMessage(ReturnCode.SUCCESS);
        } catch (Exception e) {
            message = makeMessage(ReturnCode.FAILED);
            message.put("error", e.getMessage());
        }
        return message;
    }
    @PreAuthorize("hasAuthority('saveData')")
    @PostMapping(value = "/saveData", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> saveData(
            @RequestParam(name = "tablename", required = true, defaultValue = "") String tablename,
            @RequestParam(name = "row", required = true, defaultValue = "") String row
    ) {
        Map<String, Object> message = null;
        Map<String, Object> params = JSONObject.parseObject(row, new TypeReference<Map<String, Object>>() {
        });
        params.remove("state");
        try {
            evaluationTablesService.insertData(tablename, params);
            message = makeMessage(ReturnCode.SUCCESS);
        } catch (Exception e) {
            message = makeMessage(ReturnCode.FAILED);
            message.put("error", e.getMessage());
        }
        return message;
    }

    @PreAuthorize("hasAuthority('deleteData')")
    @DeleteMapping(value = "/deleteData", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> deleteData(@RequestParam(name = "tablename", required = true, defaultValue = "") String tablename,
                                          @RequestParam(name = "row", required = true, defaultValue = "") String row) {
        Map<String, Object> message = null;
        Map<String, Object> params = JSONObject.parseObject(row, new TypeReference<Map<String, Object>>() {
        });
        params.remove("state");
        try {
            evaluationTablesService.deleteData(tablename, params);
            message = makeMessage(ReturnCode.SUCCESS);
        } catch (Exception e) {
            message = makeMessage(ReturnCode.FAILED);
            message.put("error", e.getMessage());
        }
        return message;
    }

    @PostMapping(value = "/showInDataPage", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "是否在数据页显示")
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
            dataType = "string", value = "authorization header", defaultValue = "Bearer ")})
    @ResponseBody
    public Map<String, Object> showInDataPage(@RequestParam(name = "propertyId", required = true, defaultValue = "") String propertyId) {
        Map<String, Object> message = null;
        SystemTableProperty s = systemTablePropertyService.selectByPrimaryKey(propertyId);
        s.setToShow(s.getToShow() == 0 ? 1 : 0);
        CodeObject code = systemTablePropertyService.update(s);
        message = makeMessage(code);
        message.put("toShow", s.getToShow());
        return message;
    }


}
