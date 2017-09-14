package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemEditor;
import com.zkzy.zyportal.system.api.service.EditorService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Zhangzy on 2017/6/26.
 */
@Validated
@RestController
@RequestMapping("/Editor/SystemEditor")
@Api(tags = "富文本编辑器Controller")
public class SystemEditorController extends BaseController {
    @Autowired
    private EditorService editorService;

    /**
     * 增加富文本编辑
     * Created by Zhangzy  on 2017/6/26
     * @return the map
     */
    @PreAuthorize("hasAuthority('editInsert')")
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加富文本编辑器内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> InsertEditor(
            @ApiParam(required = true, value = "标题") @RequestParam("name") String name,
            @ApiParam(required = true, value = "简要") @RequestParam("description") String description,
            @ApiParam(required = false, value = "路径",defaultValue =" ") @RequestParam("fileurl") String fileurl,
            @ApiParam(required = false, value = "文件名",defaultValue = " ") @RequestParam("basename") String basename,
            @ApiParam(required = false, value = "编辑器内容",defaultValue = " ") @RequestParam("content") String content
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemEditor systemEditor=new SystemEditor();
        systemEditor.setName(name);
        systemEditor.setDescription(description);
        systemEditor.setFileurl(fileurl);
        systemEditor.setBasename(basename);
        systemEditor.setContent(content);
        systemEditor.setCreater(user.getId());
        systemEditor.setModifyer(user.getId());
        return makeMessage(editorService.insertEditor(systemEditor));
    }

    /**
     * 删除富文本编辑器信息
     * Created by Zhangzy  on 2017/6/26.
     * @return the map
     */
    @PreAuthorize("hasAuthority('editDel')")
    @PostMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除富文本编辑器内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> DelAreaByName(
            @ApiParam(required = true, value = "ID") @RequestParam("id") String id
    ){
        SystemEditor systemEditor=new SystemEditor();
        systemEditor.setId(id);
        return makeMessage(editorService.delByID(systemEditor));
    }
    /**
     * 更新富文本编辑
     * Created by Zhangzy  on 2017/6/26
     * @return the map
     */
    @PreAuthorize("hasAuthority('editUpdate')")
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新富文本编辑器内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> UpdateEditor(
            @ApiParam(required = true, value = "ID") @RequestParam("id") String id,
            @ApiParam(required = true, value = "标题") @RequestParam("name") String name,
            @ApiParam(required = true, value = "简要") @RequestParam("description") String description,
            @ApiParam(required = false, value = "路径",defaultValue =" ") @RequestParam("fileurl") String fileurl,
            @ApiParam(required = false, value = "文件名",defaultValue = " ") @RequestParam("basename") String basename,
            @ApiParam(required = false, value = "编辑器内容",defaultValue = " ") @RequestParam("content") String content
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemEditor systemEditor=new SystemEditor();
        systemEditor.setId(id);
        systemEditor.setName(name);
        systemEditor.setDescription(description);
        systemEditor.setFileurl(fileurl);
        systemEditor.setBasename(basename);
        systemEditor.setContent(content);
        systemEditor.setModifyer(user.getId());
        return makeMessage(editorService.updateById(systemEditor));
    }
    /**
     * 查询所有富文本编辑器信息
     * Created by Zhangzy  on 2017/6/26.
     * @return the map
     */
    @PreAuthorize("hasAuthority('editGetAll')")
    @PostMapping(value = "/getEditors", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有富文本编辑器内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel getAreas(
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize,
            @ModelAttribute SystemEditor systemEditor
            ){
        String sqlParam="";
        if(!"".equals(systemEditor.getName())&&systemEditor.getName()!=null){
   sqlParam+="and name like '%"+systemEditor.getName()+"%'";
        }
        if(!"".equals(systemEditor.getBasename())&&systemEditor.getBasename()!=null){
            sqlParam+="and basename like '%"+systemEditor.getBasename()+"%'";;
        }
        if(!"".equals(systemEditor.getDescription())&&systemEditor.getDescription()!=null){
            sqlParam+="and description like '%"+systemEditor.getDescription()+"%'";
        }
        PageInfo pageInfo=editorService.selectAll(Integer.valueOf(pageNumber),Integer.valueOf(pageSize),sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }

    /**
     * 查询单个富文本编辑器信息
     * Created by Zhangzy  on 2017/6/26.
     * @return the map
     */
    @PostMapping(value = "/SelectEditorById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个富文本编辑器信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> SelectEditorById(
            @ApiParam(required = true, value = "富文本编辑器ID" ) @RequestParam("id") String id
    ){
        SystemEditor systemEditor=editorService.selectById(id);
        if(systemEditor!=null){
            return makeMessage(ReturnCode.SUCCESS,systemEditor);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }
}
