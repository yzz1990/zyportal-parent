package com.zkzy.portal.client.controller;

import com.zkzy.portal.common.upload.DiskFileOperator;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/18 0018.
 * 文件上传
 * 注意点:
 *         1.类上添加@RestController或在方法上添加@ResponseBody注解
 *         2.返回的数据在前台需要显示为json格式
 */


@Controller
@RequestMapping("/file/fileUpload")
//@ConfigurationProperties(prefix="upload")
public class FileController {

    @PostMapping(value = "imageUpload", produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "图片上传")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,String> uploadImage(
           HttpServletRequest request,HttpServletResponse response) throws InvalidCaptchaException, IOException {
        Map<String,String> resultMap=new HashMap<String,String>();
        FileOutputStream fos=null;
        String uploadFilePath=null;//保存的绝对路径
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileNames = file.getOriginalFilename();
                int split = fileNames.lastIndexOf(".");
                //存储文件
                String fileName = fileNames.substring(0,split);//文件名
                String fileType= fileNames.substring(split+1,fileNames.length());//文件后缀
                byte[] fileBytes= file.getBytes();//文件内容

                String newFileNmae=this.getNewFileName(fileName,fileType);//新名称
                String relativePath="/"+newFileNmae;//相对地址
                uploadFilePath= DiskFileOperator.workFolderName+relativePath;

                File file1 = new File(uploadFilePath);//保存路径
                String fileParentPath=file1.getParent();//上级父路径
                File fParent=new File(fileParentPath);
                if(!fParent.exists()){//上级父路径不存在则创建
                    fParent.mkdirs();
                }

                fos = new FileOutputStream(file1);
                fos.write(fileBytes);
                resultMap.put("status","success");
                resultMap.put("fileUrl",relativePath);//返回保存的相对路径
                resultMap.put("uploadFilePath",uploadFilePath);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
        }finally {
            if(fos!=null){
                fos.close();
            }
            return resultMap;
        }
    }


    @PostMapping(value = "fileUpload", produces = "application/json; charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "文件上传")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String,String> uploadFile(
            HttpServletRequest request,HttpServletResponse response) throws InvalidCaptchaException, IOException {
        Map<String,String> resultMap=new HashMap<String,String>();
        FileOutputStream fos=null;
        String uploadFilePath=null;//保存的绝对路径
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile file = req.getFile(iterator.next());
                String fileNames = file.getOriginalFilename();
                int split = fileNames.lastIndexOf(".");
                //存储文件
                String fileName = fileNames.substring(0,split);//文件名
                String fileType= fileNames.substring(split+1,fileNames.length());//文件后缀
                byte[] fileBytes= file.getBytes();//文件内容

                String newFileNmae=this.getNewFileName(fileName,fileType);//新名称
                String relativePath="/"+newFileNmae;//相对地址
                uploadFilePath= DiskFileOperator.workFolderName+relativePath;

                File file1 = new File(uploadFilePath);//保存路径
                String fileParentPath=file1.getParent();//上级父路径
                File fParent=new File(fileParentPath);
                if(!fParent.exists()){//上级父路径不存在则创建
                    fParent.mkdirs(); 
                }

                fos = new FileOutputStream(file1);
                fos.write(fileBytes);
                resultMap.put("status","success");
                resultMap.put("fileBaseName",fileNames);
                resultMap.put("fileUrl",relativePath);//返回保存的相对路径
                resultMap.put("uploadFilePath",uploadFilePath);
            }
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("status","fail");
        }finally {
            if(fos!=null){
                fos.close();
            }
            return resultMap;
        }
    }
    /**
     * 删除
     * @param delFile
     * @return
     */
    public Map<String,String> delImage(String delFile){

        return null;
    }

    /**
     * 获取新名称
     * 规则:uuid+图片原来的名称
     * @param name
     * @param suffix
     * @return
     */
    public String getNewFileName(String name,String suffix) {
        String uuid= RandomHelper.uuid();
        return uuid  +"."+ suffix;
    }




}