package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.service.PgdaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 * 评估档案
 */

@RestController
@RequestMapping("/pgda/pgdaInfo")
@Api(tags = "评估档案Controller")
public class PgdaController extends BaseController {

    @Autowired
    private PgdaService pgdaService;

    @PostMapping(value = "pgdaInfoList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "查询评估档案List")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getPgdaInfoList(@RequestParam(name = "param",required = false) String param,
                                               @RequestParam(name = "currentPage",required = true,defaultValue = "1") String currentPage,
                                               @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize,
                                               @RequestParam(name = "oldManid",required = true) String oldManid){
        Map<String,Object> info=pgdaService.getPgdaInfoList(oldManid,param,Integer.valueOf(currentPage),Integer.valueOf(pageSize));
        if(info!=null){
            return makeMessage(ReturnCode.SUCCESS,info);
        }else{
            return makeMessage(ReturnCode.UNKNOWN_ERROR,null);
        }
    }


}
