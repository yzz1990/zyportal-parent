package com.zkzy.portal.client.controller;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.security.model.AuthUser;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SmsBookB;
import com.zkzy.zyportal.system.api.exception.InvalidCaptchaException;
import com.zkzy.zyportal.system.api.service.ISmsBookService;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/sms/book")
@Api(tags = "短信通讯录管理")
@ApiModel(value = "smsBook")
public class SmsBookController extends BaseController {

    /**
     * 通讯录服务
     */
    @Autowired
    private ISmsBookService smsBookServiceImpl;
    @Autowired
    private RedisRepository redisRepository;

    @PreAuthorize("hasAuthority('bookTree')")
    @PostMapping(value = "/bookTree", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取通讯录树")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> getBookZTree() {
        AuthUser user = WebUtils.getCurrentUser();
        Map<String,Object> message=null;
        try {
            List<ZtreeSimpleView> zTreeList=smsBookServiceImpl.getBookZTree();
            message=makeMessage(ReturnCode.SUCCESS);
            message.put(Message.RETURN_FIELD_DATA,zTreeList);
        }catch (Exception e){
            e.printStackTrace();
            message=makeMessage(ReturnCode.FAILED);
            return message;
        }
        return message;
    }

    /**
     * 获取通讯录列表
     */
    @PreAuthorize("hasAuthority('bookList')")
    @PostMapping(value = "bookList", produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "获取通讯录列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public GridModel bookList(
                          @ModelAttribute SmsBookB smsBookB,
                          @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
                          @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();
        try {
            String param="";
            if(smsBookB != null){
                if(smsBookB.getName()!=null && smsBookB.getName().trim().length()>0){
                    param+=" and name like '%"+smsBookB.getName()+"%' ";
                }
            }
            PageInfo pageInfo = smsBookServiceImpl.bookList(param,Integer.valueOf(pageNumber),Integer.valueOf(pageSize));
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

    @PreAuthorize("hasAuthority('addBook')")
    @PostMapping(value = "addBook", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新建通讯录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addBook(@ModelAttribute SmsBookB book) throws InvalidCaptchaException {
        return makeMessage(smsBookServiceImpl.addBook(book));
    }

    @PreAuthorize("hasAuthority('updateBook')")
    @PostMapping(value = "updateBook", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "修改通讯录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> updateBook(@ModelAttribute SmsBookB book) throws InvalidCaptchaException {
        return makeMessage(smsBookServiceImpl.updateBook(book));
    }

    @PreAuthorize("hasAuthority('deleteBook')")
    @PostMapping(value = "deleteBook", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除通讯录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> deleteBook(@ModelAttribute SmsBookB book){
        return makeMessage(smsBookServiceImpl.deleteBook(book));
    }

}
