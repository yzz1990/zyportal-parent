package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.upload.DiskFileOperator;
import com.zkzy.portal.common.utils.FileUtil;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemNodefile;
import com.zkzy.zyportal.system.api.entity.SystemUploadnode;
import com.zkzy.zyportal.system.api.service.SystemNodefileService;
import com.zkzy.zyportal.system.api.service.SystemUploadNodeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.ConnectException;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */
@Validated
@RestController
@RequestMapping("/fileNode/fileupload")
@Api(tags = "文件系统Controller")
public class SystemUploadnodeController extends BaseController {
    @Autowired
    SystemUploadNodeService systemUploadNodeService;

    @Autowired
    SystemNodefileService systemNodefileService;

    @PreAuthorize("hasAuthority('nodeGetNodes')")
    @PostMapping(value = "/getNodes", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询节点")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectNode(){
        String param="";
        List<SystemUploadnode> list=systemUploadNodeService.selectAll(param);
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }
    @PreAuthorize("hasAuthority('nodeInsert')")
    @PostMapping(value = "/Insert", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "增加节点信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public  Map<String,Object> InsertNode(
            @ApiParam(required = true, value = "parentId") @RequestParam("parentId") String parentId,
            @ApiParam(required = true, value = "name") @RequestParam("name") String name
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemUploadnode systemUploadnode=new SystemUploadnode();
        systemUploadnode.setParentId(Long.parseLong(parentId));
        systemUploadnode.setName(name);
        systemUploadnode.setCreater(user.getId());
        systemUploadnode.setModifyer(user.getId());
        return makeMessage(systemUploadNodeService.insertNode(systemUploadnode));

    }

    @PreAuthorize("hasAuthority('nodeUpdate')")
    @PostMapping(value = "/Update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改节点信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )

    public  Map<String,Object> UpadateNode(
            @ApiParam(required = true, value = "parentId") @RequestParam("parentId") String parentId,
            @ApiParam(required = true, value = "id") @RequestParam("id") String id,
            @ApiParam(required = true, value = "name") @RequestParam("name") String name
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemUploadnode systemUploadnode=new SystemUploadnode();
        systemUploadnode.setParentId(Long.parseLong(parentId));
        systemUploadnode.setName(name);
        systemUploadnode.setId(Long.parseLong(id));
        systemUploadnode.setModifyer(user.getId());
        return makeMessage(systemUploadNodeService.updateNode(systemUploadnode));
    }

    @PostMapping(value = "/getNodeById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据ID查询节点")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectNodeById(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id
    ){
 SystemUploadnode systemUploadnode=systemUploadNodeService.selectNodebyId(id);
        if (systemUploadnode!=null){
            return makeMessage(ReturnCode.SUCCESS,systemUploadnode);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    @PreAuthorize("hasAuthority('nodeGetFile')")
    @PostMapping(value = "/getFileByPid", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据PID查询文件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectFileByPid(
            @ApiParam(required = true, value = "parentId") @RequestParam("parentId") String parentId,
            @ApiParam(required = true, value = "filetype") @RequestParam("filetype") String filetype
    ){
                String param="";
if(parentId!=null){
    param=param+" AND PARENT_ID ="+parentId+"";
}
if(!filetype.equals("all")){
    if(filetype.equals("office")){
        param=param+" AND(FILETYPE='docOffice' or FILETYPE='xlsOffice')";
    }else{
        param=param+" AND FILETYPE='"+filetype+"'";
    }

}
List<SystemNodefile> list=systemNodefileService.selectAllbyPid(param);
        if (list!=null){
            return makeMessage(ReturnCode.SUCCESS,list);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    @PreAuthorize("hasAuthority('saveFileinfo')")
    @PostMapping(value = "/saveFileinfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "保存文件信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> saveFile(
            @ApiParam(required = true, value = "parentId") @RequestParam("parentId") String parentId,
            @ApiParam(required = true, value = "baseName") @RequestParam("baseName") String baseName,
            @ApiParam(required = true, value = "fileUrl") @RequestParam("fileUrl") String fileUrl,
            @ApiParam(required = true, value = "size") @RequestParam("size") String size,
            @ApiParam(required = true, value = "filetype") @RequestParam("filetype") String filetype,
            @ApiParam(required = false, value = "pdfPath",defaultValue =" ") @RequestParam("pdfPath") String pdfPath
    ){
        AuthUser user = WebUtils.getCurrentUser();
        SystemNodefile systemNodefile=new SystemNodefile();
        systemNodefile.setParentId(Long.parseLong(parentId));
        systemNodefile.setBasename(baseName);
        systemNodefile.setFileurl(fileUrl);
        systemNodefile.setSize(size);
        if(!"".equals(pdfPath)){
            systemNodefile.setPdfpath(pdfPath);
            systemNodefile.setStatus("false");
        }
        systemNodefile.setFiletype(filetype);
        systemNodefile.setCreater(user.getId());
        systemNodefile.setModifyer(user.getId());
        return makeMessage(systemNodefileService.saveUploadFile(systemNodefile));
    }

    @PreAuthorize("hasAuthority('delFile')")
    @PostMapping(value = "/delFile", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除保存文件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> delFile(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id
    ){
                return  makeMessage(systemNodefileService.delUploadFile(id));
    }

    @PreAuthorize("hasAuthority('delNode')")
    @PostMapping(value = "/delNode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除节点")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> delNode(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id
    ){
                return  makeMessage(systemUploadNodeService.delNodeById(id));
    }



    @PreAuthorize("hasAuthority('officeToPdf')")
    @PostMapping(value = "/officeToPdf", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "手动转换pdf")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> officeToPdf (
            @ApiParam(required = true, value = "pdfPath") @RequestParam("pdfPath") String pdfPath,
            @ApiParam(required = true, value = "fileUrl") @RequestParam("fileUrl") String fileUrl,
            @ApiParam(required = true, value = "id") @RequestParam("id") String id

    ){
        String param="";
        if(id!=null){
            param=param+" AND ID='"+id+"'";
        }
        List<SystemNodefile> list=systemNodefileService.selectAllbyPid(param);
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        systemNodefileService.updateFile(list.get(0),"checktrue");
                        FileUtil pdf=new FileUtil();
                            int flag=pdf.office2PDF(DiskFileOperator.workFolderName+fileUrl,DiskFileOperator.workFolderName+pdfPath);
                            if(flag==0){
                                systemNodefileService.updateFile(list.get(0),"true");
                            }else if (flag==-1){
                                systemNodefileService.updateFile(list.get(0),"Non-Existent");
                            }else if(flag==-2){
                                systemNodefileService.updateFile(list.get(0),"disConect");
                            }else if(flag==1){
                                systemNodefileService.updateFile(list.get(0),"failed");
                            }else if(flag==-5){
                                systemNodefileService.updateFile(list.get(0),"bgToPdf");
                            }else{
                                systemNodefileService.updateFile(list.get(0),"failed");
                            }
                    }
                });

        if(!list.get(0).getStatus().equals("true")){
            try{
                thread.start();
                    return makeMessage(systemNodefileService.updateFile(list.get(0),"checktrue"));
            }catch (Exception e){
                return makeMessage(ReturnCode.TOPDF_FAILED);
            }
        }else{
            return makeMessage(ReturnCode.TOPDF_SUCESS);
        }
    }


    @PostMapping(value = "/getStatusById", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据ID查询转换状态")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,Object> selectFileByPid(
            @ApiParam(required = true, value = "id") @RequestParam("id") String id
    ){
        String param="";
        if(id!=null){
            param=param+" AND ID='"+id+"'";
        }
        List<SystemNodefile> list=systemNodefileService.selectAllbyPid(param);
        String nowStatus=list.get(0).getStatus();
        if("failed".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_FAILED);
        }else if("true".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_SUCESS);
        }else if("checktrue".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_LOADING);
        }else if("disConect".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_DISCONECT);
        } else if("Non-Existent".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_NONEFILE);
        } else if("bgToPdf".equals(nowStatus)){
            return makeMessage(ReturnCode.TOPDF_BACKGROUNDTOPDF);
        } else{
            systemNodefileService.updateFile(list.get(0),"false");
            return makeMessage(ReturnCode.TOPDF_FAILED);
        }
    }
}
