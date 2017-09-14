package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.entity.SmsManB;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISmsManService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping("/sms/man")
@Api(tags = "短信通讯人员管理")
@ApiModel(value = "smsMan")
public class SmsManController extends BaseController {

    /**
     * 通讯人员服务
     */
    @Autowired
    private ISmsManService smsManServiceImpl;
    @Autowired
    private RedisRepository redisRepository;

    /**
     * 获取通讯人员列表
     */
    @PreAuthorize("hasAuthority('manList')")
    @PostMapping(value = "manList", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取通讯人员列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel manList(
                          @ModelAttribute SmsManB smsManB,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();
        try {
            String param="";
            if(smsManB != null){
                if(smsManB.getName()!=null && smsManB.getName().trim().length()>0){
                    param+=" and name like '%"+smsManB.getName()+"%' ";
                }
                if(smsManB.getBookid()!=null && smsManB.getBookid().trim().length()>0){
                    String[] bookidArr=smsManB.getBookid().split(",");
                    StringBuffer sqlBuf=new StringBuffer();
                    for(String s:bookidArr){
                        if(sqlBuf.length()==0){
                            sqlBuf.append("SELECT DISTINCT(ID) as ID from SMS_MAN_B t where t.BOOKID like '%"+s+"%'");
                        }else{
                            sqlBuf.append(" or t.BOOKID like '%"+s+"%'");
                        }
                    }
                    param+=" and id in ("+sqlBuf+")";
                }
            }
            PageInfo pageInfo = smsManServiceImpl.manList(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
                grid.setRet(true);
            }else{
                grid.setRet(false);
            }
        }catch (Exception e){
            e.printStackTrace();
            grid.setRet(false);
        }
        return grid;
    }

    @PreAuthorize("hasAuthority('addMan')")
    @PostMapping(value = "addMan", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新建通讯人员")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addMan(@ModelAttribute SmsManB Man) throws InvalidCaptchaException {
        return makeMessage(smsManServiceImpl.addMan(Man));
    }

    @PreAuthorize("hasAuthority('updateMan')")
    @PostMapping(value = "updateMan", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改通讯人员")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateMan(@ModelAttribute SmsManB Man) throws InvalidCaptchaException {
        return makeMessage(smsManServiceImpl.updateMan(Man));
    }

    @PreAuthorize("hasAuthority('deleteMan')")
    @PostMapping(value = "deleteMan", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除通讯人员")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteMan(@ModelAttribute SmsManB Man){
        return makeMessage(smsManServiceImpl.deleteMan(Man));
    }

}
