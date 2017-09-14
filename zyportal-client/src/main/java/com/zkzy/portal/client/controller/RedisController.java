package com.zkzy.portal.client.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.security.utils.TokenUtil;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/17 0017.
 * 获取redis中的数据
 */

@Validated
@RestController
@RequestMapping("/sys/sysRedis")
@Api(tags = "Redis存取缓存值")
public class RedisController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ISystemUserService systemUserService;

    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private TokenUtil jwtTokenUtil;


    @GetMapping(value = "getParameterValueByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的系统参数值")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
//    @PreAuthorize("hasAuthority('sys:menu:view')")
    public String getParameterValueByCode(@RequestParam("code") String code){
        return redisService.getParameterValueByCode(code);
    }

    @GetMapping(value = "getSystemCodeListByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的子元素数据字典集合")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public String getSystemCodeListByCode(@RequestParam("code") String code){
        return redisService.getSystemCodeListByCode(code);
    }

    @GetMapping(value = "getSystemCodeNameByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的数据字典名称")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public String getSystemCodeNameByCode(@RequestParam("code") String code){
        return redisService.getSystemCodeNameByCode(code);
    }

    @GetMapping(value = "getOrganizationListByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的子元素组织机构集合")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public String getOrganizationListByCode(@RequestParam("code") String code){
        return redisService.getOrganizationListByCode(code);
    }

    @GetMapping(value = "getOrganizationListBeanByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的子元素组织机构集合")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public List<SystemOrganization> getOrganizationListBeanByCode(@RequestParam("code") String code){
        return redisService.getOrganizationListBeanByCode(code);
    }

    @GetMapping(value = "getOrganizationNameByCode", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "根据code取出对应的组织机构名称")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public String getOrganizationNameByCode(@RequestParam("code") String code){
        return redisService.getOrganizationNameByCode(code);
    }


    @PreAuthorize("hasAuthority('onlineUser_list')")
    @PostMapping(value = "getOnlineUserList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取在线的用户信息List")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getOnlineUserList(
                                       //@RequestParam(name = "sqlParam",required = false) String sqlParam,
                                       @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                                       @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();
        List<SystemUser> systemUserList=new ArrayList<SystemUser>();
        try {
            List<String> onlineUserKeyList=jwtTokenUtil.onlineUserList();
            int totalSize=onlineUserKeyList.size();
            //是否分页
            if(pageNumber!=null&& pageSize!=null){
                int page=Integer.valueOf(pageNumber);
                int size=Integer.valueOf(pageSize);
                int start = (page - 1) * size;
                int end=((start+size)>totalSize)?totalSize:(start+size);
                onlineUserKeyList = onlineUserKeyList.subList(start, end);
            }
            if(onlineUserKeyList!=null){
                for(String userkey:onlineUserKeyList){
                    String userDetailStr=redisRepository.get(userkey);
                    //System.out.println(userDetailStr);
                    JSONObject jsonObject = JSON.parseObject(userDetailStr);
                    String userid=jsonObject.get("id").toString();
                    SystemUser systemUser=systemUserService.getUserById(userid);
                    if(systemUser!=null){
                        systemUserList.add(systemUser);
                    }
                }
                grid.setTotal((long) systemUserList.size());
                grid.setRows(systemUserList);
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        }catch (Exception e){
            e.printStackTrace();
            return makeMessage(ReturnCode.FAILED,grid);
        }
//        return grid;
    }


}
