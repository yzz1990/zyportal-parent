package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemCode;
import com.zkzy.zyportal.system.api.service.SystemCodeService;
import io.swagger.annotations.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangZY on 2017/6/30.
 */
@Validated
@RestController
@RequestMapping("/Code/SystemCode")
@Api(tags = "数据字典Controller")
public class SystemCodeController extends BaseController   {

    List<SystemCode> childMenu=new ArrayList<SystemCode>();
    @Autowired
    private SystemCodeService systemCodeService;

    @PreAuthorize("hasAuthority('selectgetAll')")
    @PostMapping(value = "selectAll", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询数据字典")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel getOldManInfoList(
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "100")String pageSize,
        /*    @RequestParam(name = "sqlParam",required = false,defaultValue = "")String sqlParam*/
            @ModelAttribute SystemCode code
            ){
        String sqlParam="";
        if(code.getCodeId()==null){
            sqlParam="AND STATUS='A' AND PARENT_ID is NULL ORDER BY CODE_ID DESC";
        }else{
            sqlParam="AND STATUS='A' AND PARENT_ID="+code.getCodeId()+" ORDER BY CODE_ID DESC";
        }
        PageInfo pageInfo=systemCodeService.selectparams(Integer.valueOf(pageNumber),Integer.valueOf(pageSize),sqlParam);
        GridModel grid = new GridModel();
        grid.setTotal(pageInfo.getTotal());
        grid.setRows(pageInfo.getList());
        return grid;
    }


    /**
     * 增加数据字典
     * Created by Zhangzy  on 2017/7/4
     * @return the map
     */
    @PreAuthorize("hasAuthority('selectAll')")
    @PostMapping(value = "selectAllparams", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询数据字典2")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> selectAllParams(){
        String sqlParam="";
        sqlParam="AND STATUS='A'  ORDER BY SORT DESC";
 List<SystemCode> list=systemCodeService.selectAllparams(sqlParam);
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }
    /**
     * 增加数据字典
     * Created by Zhangzy  on 2017/7/4
     * @return the map
     */
    @PreAuthorize("hasAuthority('Insert')")
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加数据字典")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> insertCode(
            @ApiParam(required = true, value = "词典名称") @RequestParam("name") String name,
            @ApiParam(required = false, value = "描述",defaultValue =" ") @RequestParam("description") String description,
            @ApiParam(required = false, value = "父模块Id",defaultValue =" ") @RequestParam("permissionId") String permissionId,
            @ApiParam(required = false, value = "父词典名称",defaultValue = " ") @RequestParam("parentId") String parentId,
            @ApiParam(required = true, value = "词典编码") @RequestParam("codeMyid") String codeMyid,
            @ApiParam(required = false, value = "词典图标",defaultValue = "") @RequestParam("iconCls") String iconCls,
            @ApiParam(required = false, value = "排序编码",defaultValue = "") @RequestParam("sort") String sort
    ){
        String param="AND STATUS='A' AND CODE_MYID='"+codeMyid+"'  ORDER BY SORT DESC";
        List<SystemCode> list=systemCodeService.selectAllparams(param);
        if(list.size()>0){
            return makeMessage(ReturnCode.INSERTBM_FAILED);
        }else{
            AuthUser user = WebUtils.getCurrentUser();
            SystemCode systemCode=new SystemCode();
            systemCode.setName(name);
            systemCode.setDescription(description);
            systemCode.setPermissionid(Long.parseLong(permissionId));
            systemCode.setParentId(Long.parseLong(parentId));
            systemCode.setCodeMyid(codeMyid);
            systemCode.setIconcls(iconCls);
            if("".equals(sort)){
                systemCode.setSort(null);
            }else {
                systemCode.setSort(Long.parseLong(sort));
            }

            systemCode.setCreater(user.getId());
            systemCode.setModifyer(user.getId());
            return  makeMessage(systemCodeService.addSystemCode(systemCode));
        }

    }
    /**
     * 删除数据字典
     * Created by Zhangzy  on 2017/7/4.
     * @return the map
     */
    @PreAuthorize("hasAuthority('del')")
    @PostMapping(value = "/Del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除数据字典")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> delCodeById(
            @ApiParam(required = false, value = "词典编码",defaultValue = " ") @RequestParam("codeMyid") String codeMyid,
            @ApiParam(required = true, value = "codeId") @RequestParam("codeId") String codeId,
            @ApiParam(required = true, value = "parentId") @RequestParam("parentId") String parentId
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemCode systemCode=new SystemCode();
        systemCode.setCodeMyid(codeMyid);
        systemCode.setParentId(Long.parseLong(parentId));
        systemCode.setModifyer(user.getId());
        systemCode.setCodeId(Long.parseLong(codeId));
        return makeMessage(systemCodeService.delSystemCode(systemCode));


    }
    /**
     * 更新数据字典
     * Created by Zhangzy  on 2017/7/4.
     * @return the map
     */
    @PreAuthorize("hasAuthority('update')")
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "更新数据字典内容")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> updateCode(
            @ApiParam(required = true, value = "词典名称") @RequestParam("name") String name,
            @ApiParam(required = false, value = "描述",defaultValue =" ") @RequestParam("description") String description,
            @ApiParam(required = true, value = "词典编码") @RequestParam("codeMyid") String codeMyid,
            @ApiParam(required = false, value = "词典图标",defaultValue = " ") @RequestParam("iconCls") String iconCls,
            @ApiParam(required = false, value = "排序编码",defaultValue = "") @RequestParam("sort") String sort,
            @ApiParam(required = true, value = "codeId") @RequestParam("codeId") String codeId
    ){
        String param="AND STATUS='A' AND CODE_MYID='"+codeMyid+"'  ORDER BY SORT DESC";
        List<SystemCode> list=systemCodeService.selectAllparams(param);
        String mycodeId="";
        if(list.size()>0){
            mycodeId=list.get(0).getCodeId().toString();
        }
       Boolean a= codeId.equals(mycodeId);
        if(list.size()>0&&!a){
return makeMessage(ReturnCode.CHANGEBM_FAILED);
        }
        AuthUser user = WebUtils.getCurrentUser();
        SystemCode systemCode=new SystemCode();
        systemCode.setCodeId(Long.parseLong(codeId));
        systemCode.setName(name);
        systemCode.setDescription(description);
        systemCode.setCodeMyid(codeMyid);
        systemCode.setIconcls(iconCls);
        if("".equals(sort)){
            systemCode.setSort(null);
        }else {
            systemCode.setSort(Long.parseLong(sort));
        }
        systemCode.setModifyer(user.getId());
        return makeMessage(systemCodeService.updateSystemCode(systemCode));
    }
    /**
     * 查询单个数据字典信息
     * Created by Zhangzy  on 2017/7/4.
     * @return the map
     */
    @PostMapping(value = "/SelectCodeById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询单个数据字典信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectCodeById(
            @ApiParam(required = true, value = "codeId" ) @RequestParam("id") String codeId
    ){

        SystemCode systemCode=new SystemCode();
        systemCode.setCodeId(Long.parseLong(codeId));
        SystemCode scode = systemCodeService.selectById(systemCode);
        if(scode!=null){
            return makeMessage(ReturnCode.SUCCESS,scode);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    /**
     * 查询单个数据字典信息
     * Created by Zhangzy  on 2017/7/4.
     * @return the map
     */
    @PostMapping(value = "/SelectCodeByMyid", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据编码查询单个数据字典信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectCodeByMyid(
            @ApiParam(required = true, value = "codeMyid" ) @RequestParam("codeMyid") String codeMyid
    ){

        SystemCode systemCode=new SystemCode();
        systemCode.setCodeMyid(codeMyid);
        SystemCode scode = systemCodeService.selectByMyid(systemCode);
        if(scode!=null){
            return makeMessage(ReturnCode.SUCCESS,scode);
        }else {
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }
/**
 * 查询pemisson
 * Created by Zhangzy  on 2017/7/4.
 * @return the map
 */
@PostMapping(value = "/selectPemisson", produces = "application/json; charset=UTF-8")
@ApiOperation(value = "查询pemisson")
@ApiImplicitParams(
        {
                @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                        dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
)
public Map<String, Object> selectPemisson(
        @ApiParam(required = true, value = "父词典名称") @RequestParam("permissionid") String permissionId
){
    String param="";
    if("".equals(permissionId)){
        param="AND CODE_ID is null";
    }else {
        param="AND CODE_ID ="+permissionId+"";
    }
    List<SystemCode> list=systemCodeService.selectPemisson(param);
    if (list!=null){
        return makeMessage(ReturnCode.SUCCESS,list);
    }else{
        return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
    }
}
/**
 * 查询parentId
 * Created by Zhangzy  on 2017/7/4.
 * @return the map
 */
@PostMapping(value = "/selectParentid", produces = "application/json; charset=UTF-8")
@ApiOperation(value = "查询parentId")
@ApiImplicitParams(
        {
                @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                        dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
)
public Map<String, Object> selectParentid(
        @ApiParam(required = true, value = "父词典名称") @RequestParam("parentId") String parentId
){
    String param="";
    if("".equals(parentId)){
        param="AND CODE_ID is null";
    }else {
        param="AND CODE_ID ="+parentId+"";
    }
    List<SystemCode> list=systemCodeService.selectParentid(param);
    if (list!=null){
        return makeMessage(ReturnCode.SUCCESS,list);
    }else{
        return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
    }
}

    @PostMapping(value = "/selectTask", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "测试接口")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public  List selectcode(){
            List<SystemCode> list=systemCodeService.selectTimeTask();//任务组节点
//            List<SystemCode> main=new ArrayList<>();
//            main.addAll(list);
            List<Map<String,Object>> returnList=new ArrayList();
            for (SystemCode group:list){
                Map<String,Object> newMap=new HashedMap();
                String groupName=group.getName();
                String groupid=group.getCodeId().toString();

                newMap.put("groupid",groupid);
                newMap.put("groupName",groupName);

                List<SystemCode> childCode=systemCodeService.selectBeanAndClass(group.getCodeId().toString(),group.getParentId().toString());
                newMap.put("child",childCode);

                returnList.add(newMap);
            }
            return  returnList;

    }

    /**
     * 查询parentId
     * Created by Zhangzy  on 2017/7/4.
     * @return the map
     */
    @PostMapping(value = "/selectAllparamsbyParentCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询所有子节点")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> selectAllparamsbyParentCode(
            @ApiParam(required = true, value = "父词典编码") @RequestParam("codeMyid") String codeMyid
    ){
        SystemCode systemCode=new SystemCode();
        systemCode.setCodeMyid(codeMyid);
        SystemCode scode = systemCodeService.selectByMyid(systemCode);
        long id=scode.getCodeId();
        childMenu.clear();
     List<SystemCode> list=findChildDepartments(id);
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    private List<SystemCode> findChildDepartments(Long parentId){
        List<SystemCode> list=systemCodeService.selectAllchilds(parentId);
        for (SystemCode group:list){
            String closed=group.getState();
            if("closed".equals(closed)){
                findChildDepartments(group.getCodeId());
                childMenu.add(group);
            }else {
                childMenu.add(group);
            }
        }
       return  childMenu;
    }

}
