package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.PensionOldmaninfoB;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.OldManInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

@RestController
@RequestMapping("/oldMan/oldManInfo")
@Api(tags = "老人信息Controller")
@ApiModel(value = "pensionOldmaninfoB")
public class OldManInfoController extends BaseController {
    @Autowired
    private OldManInfoService oldManInfoService;

//    @ApiModelProperty(value = "老人姓名@APIModel", required = true)
//    private String name;

//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }

    @PostMapping(value = "getOldManInfoList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询老人信息List")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getOldManInfoList(
                                      @ModelAttribute PensionOldmaninfoB pensionOldmaninfoB,
                                      @RequestParam(name = "currentPage",required = true,defaultValue = "1") String currentPage,
                                      @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        String sParam="";
        if(pensionOldmaninfoB!=null){
            if(pensionOldmaninfoB.getNursinghomeid()!=null && pensionOldmaninfoB.getNursinghomeid().trim().length()>0){
                sParam+=" and  a.NURSINGHOMEID ='"+pensionOldmaninfoB.getNursinghomeid()+"' ";
            }
            if(pensionOldmaninfoB.getName()!=null && pensionOldmaninfoB.getName().trim().length()>0){
                sParam+=" and a.NAME like '%"+pensionOldmaninfoB.getName()+"%' ";
            }
            if(pensionOldmaninfoB.getPcardid()!=null && pensionOldmaninfoB.getPcardid().trim().length()>0){
                sParam+=" and a.PCARDID='"+pensionOldmaninfoB.getPcardid()+"' ";
            }
            if(pensionOldmaninfoB.getRecentrating()!=null && pensionOldmaninfoB.getRecentrating().trim().length()>0){
                sParam+=" and RECENTRATING='"+pensionOldmaninfoB.getRecentrating()+"' ";
            }
            if(pensionOldmaninfoB.getRegisteraddress()!=null && pensionOldmaninfoB.getRegisteraddress().trim().length()>0){
                sParam+=" and REGISTERADDRESS like '%"+pensionOldmaninfoB.getRegisteraddress()+"%' ";
            }
        }
        PageInfo pageInfo=oldManInfoService.getOldManInfoList(sParam,Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        if(pageInfo!=null){
            return makeMessage(ReturnCode.SUCCESS,pageInfo);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }

    @PostMapping(value = "add", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增老人基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addOldManBaseInfo(
            @ModelAttribute PensionOldmaninfoB pensionOldmaninfoB
//            @ApiParam(required = true, value = "姓名") @RequestParam(name = "name",required = true) String name,
//            @ApiParam(required = true,value = "性别") @RequestParam(name = "sex",required = true) String sex,
//            @ApiParam(required = true,value = "有无宗教信仰") @RequestParam(name = "hasreligion",required = true) String hasreligion,
//            @ApiParam(value = "信仰的宗教") @RequestParam(name = "religion") String religion,
//            @ApiParam(required = true,value = "身份证号") @RequestParam(name = "pcardid",required = true) String pcardid,
//            @ApiParam(required = true,value = "文化程度") @RequestParam(name = "culture",required = true) String culture,
//            @ApiParam(required = true,value = "户籍地址") @RequestParam(name = "registeraddress",required = true) String registeraddress,
//            @ApiParam(required = true,value = "常住地址") @RequestParam(name = "residentaddress",required = true) String residentaddress,
//            @ApiParam(required = true,value = "居住状况") @RequestParam(name = "livingstatus",required = true) String livingstatus,
//            @ApiParam(required = true,value = "经济来源") @RequestParam(name = "economicsfrom",required = true) String economicsfrom,
//            @ApiParam(required = true,value = "医疗费用支付方式") @RequestParam(name = "medicalpaytype",required = true) String medicalpaytype,
//            @ApiParam(required = true,value = "婚姻状况") @RequestParam(name = "maritalstatus",required = true) String maritalstatus,
//            @ApiParam(required = true,value = "出生日期") @RequestParam(name = "birth",required = true) String birth,
//            @ApiParam(required = true,value = "社保卡号") @RequestParam(name = "socialcardid",required = true) String socialcardid,
//            @ApiParam(required = true,value = "民族") @RequestParam(name = "famousfamily",required = true) String famousfamily,
//            @ApiParam(required = true,value = "联系人姓名") @RequestParam(name = "contactperson",required = true) String contactperson,
//            @ApiParam(required = true,value = "联系人电话") @RequestParam(name = "contacttel",required = true) String contacttel
    ) throws InvalidCaptchaException {
        //AuthUser user = WebUtils.getCurrentUser();
        return makeMessage(oldManInfoService.save(pensionOldmaninfoB,null));
    }

    @PostMapping(value = "update", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑老人基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateOldManBaseInfo(
            @ModelAttribute PensionOldmaninfoB pensionOldmaninfoB
    ) throws InvalidCaptchaException {
        //AuthUser user = WebUtils.getCurrentUser();
        return makeMessage(oldManInfoService.update(pensionOldmaninfoB,null));
    }

    @PostMapping(value = "del", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除老人基本信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delOldManBaseInfo(@RequestParam(name = "delId",required = true) String delId){
        return makeMessage(oldManInfoService.delete(delId));
    }



}
