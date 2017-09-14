package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemParameter;
import com.zkzy.zyportal.system.api.service.ParameterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Zhangzy on 2017/6/19.
 */
@Validated
@RestController
@RequestMapping("/Param/Parameter")
@Api(tags = "参数设置Controller")
public class ParameterController extends BaseController {
    @Autowired
    ParameterService parameterService;

    /**
     * 查询参数组信息
     * Created by Zhangzy  on 2017/6/19.
     * @return the map
     */
    @PostMapping(value = "/getParam", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询参数信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel getAll(
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize,
            @ApiParam(required = true, value = "分组") @RequestParam("groupType") String groupType
    ){
        String sqlParam="";
if(!"".equals(groupType)){
   sqlParam="AND GROUP_TYPE ='"+groupType+"'";
}
        PageInfo pageInfo=parameterService.selectAll(Integer.valueOf(pageNumber),Integer.valueOf(pageSize),sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }
    /**
     * 查询参数组信息
     * Created by Zhangzy  on 2017/6/19.
     * @return the map
     */
    @PreAuthorize("hasAuthority('getGroup')")
    @PostMapping(value = "/getGroup", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询参数组")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel getGroup(
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize
    ){
        PageInfo pageInfo=parameterService.selectGroup(Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    /**
     * 查询所有行政区域信息
     * Created by Zhangzy  on 2017/6/20.
     * @return the map
     */
    @PreAuthorize("hasAuthority('paramInsert')")
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加参数信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> InsertParam(
            @ApiParam(required = false, value = "参数描述",defaultValue = " ") @RequestParam("description") String description,
            @ApiParam(required = false, value = "编辑",defaultValue = "{'options':{'off':false,'on':true},'type':'checkbox'}") @RequestParam("editor") String editor,
            @ApiParam(required = true, value = "编辑类型") @RequestParam("editorType") String editorType,
            @ApiParam(required = true, value = "分组") @RequestParam("groupType") String groupType,
            @ApiParam(required = true, value = "参数编码") @RequestParam("myid") String myid,
            @ApiParam(required = true, value = "参数名称") @RequestParam("name") String name,
            @ApiParam(required = true, value = "是否启用") @RequestParam("state") String state,
            @ApiParam(required = true, value = "参数值") @RequestParam("value") String value
            ){
        SystemParameter param=parameterService.seleclByMyId(myid);
        if(param==null){
            AuthUser user = WebUtils.getCurrentUser();
            SystemParameter parameter=new SystemParameter();
            parameter.setCreater(Long.parseLong(user.getId()));
            parameter.setModifyer(Long.parseLong(user.getId()));
            parameter.setDescription(description);
            parameter.setEditor(editor);
            parameter.setEditorType(editorType);
            parameter.setGroupType(groupType);
            parameter.setMyid(myid);
            parameter.setName(name);
            parameter.setState(state);
            parameter.setValue(value);
            return makeMessage(parameterService.insertParam(parameter));
        }else{
            return makeMessage(ReturnCode.INSERTBM_FAILED);
        }

    }
    /**
     * 更新参数配置信息
     * Created by Zhangzy  on 2017/6/21.
     * @return the map
     */
    @PreAuthorize("hasAuthority('paramUpdate')")
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改参数信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> updateParam(
            @ApiParam(required = true, value = "是否启用") @RequestParam("state") String state,
            @ApiParam(required = true, value = "参数值") @RequestParam("value") String value,
            @ApiParam(required = false, value = "参数描述",defaultValue = " ") @RequestParam("description") String description,
            @ApiParam(required = true, value = "主键值") @RequestParam("parameterId") String parameterId
    ){
        SystemParameter parameter=new SystemParameter();
parameter.setState(state);
parameter.setValue(value);
parameter.setDescription(description);
parameter.setParameterId(Long.parseLong(parameterId));
return makeMessage(parameterService.updateParamById(parameter));
    }
    /**
     * 删除参数配置信息(逻辑删除)
     * Created by Zhangzy  on 2017/6/21.
     * @return the map
     */
    @PreAuthorize("hasAuthority('paramDelete')")
    @PostMapping(value = "/Delete", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除参数信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> delParam(
            @ApiParam(required = true, value = "参数编码") @RequestParam("myid") String myid,
            @ApiParam(required = true, value = "主键值") @RequestParam("parameterId") String parameterId
    ){
        SystemParameter parameter=new SystemParameter();
        parameter.setParameterId(Long.parseLong(parameterId));
        parameter.setMyid(myid);
        return makeMessage(parameterService.delparamById(parameter));
    }

    /**
     * 查询才参数
     * Created by Zhangzy  on 2017/7/7.
     * @return the map
     */
    @PostMapping(value = "/selectByMyId", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据编码查询参数")
    public Map<String, Object> selectParamByMyId(
            @ApiParam(required = true, value = "参数编码") @RequestParam("myid") String myid
    ){
        SystemParameter param=parameterService.seleclByMyId(myid);
        if(param!=null){
            return makeMessage(ReturnCode.SUCCESS,param);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }

    }

}
