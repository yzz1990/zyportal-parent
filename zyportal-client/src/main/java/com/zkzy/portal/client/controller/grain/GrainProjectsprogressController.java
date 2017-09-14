package com.zkzy.portal.client.controller.grain;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.GridModel;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.grain.GrainProjectsprogress;
import com.zkzy.zyportal.system.api.service.grain.GrainProjectsprogressService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2017/9/12 0012.
 * 粮食仓储和物流设施重大工程进展情况
 */

@RestController
@RequestMapping("/grain/grainProjectsprogress")
@Api(tags = "粮食仓储和物流设施重大工程进展情况Controller")
@ApiModel("grainProjectsprogress")
public class GrainProjectsprogressController extends BaseController {
    @Autowired
    private GrainProjectsprogressService grainProjectsprogressServiceImpl;

    /**
     * 获取粮安工程统计列表
     */
    @PreAuthorize("hasAuthority('grainProjectsprogress_list')")
    @PostMapping(value = "infoList", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取粮食仓储和物流设施重大工程进展情况统计列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> infoList(
            @ModelAttribute GrainProjectsprogress grainProjectsprogress,
            @RequestParam(name = "pageNumber",required = true,defaultValue = "1") String pageNumber,
            @RequestParam(name = "pageSize",required = true,defaultValue = "10")String pageSize){
        GridModel grid = new GridModel();

        try {
            String param="";
            if(grainProjectsprogress!=null){
                if(grainProjectsprogress.getReportingunit()!=null && grainProjectsprogress.getReportingunit().trim().length()>0){
                    param+=" and reportingunit like '%"+grainProjectsprogress.getReportingunit()+"%' ";
                }
                if(grainProjectsprogress.getReportingtime()!=null && grainProjectsprogress.getReportingtime().trim().length()>0){
                    param+=" and reportingtime='"+grainProjectsprogress.getReportingtime()+"' ";
                }
            }
            Paging page=new Paging();
            page.setPageNum(Integer.valueOf(pageNumber));
            page.setPageSize(Integer.valueOf(pageSize));
            PageInfo pageInfo=grainProjectsprogressServiceImpl.infoList(param,page);
            if(pageInfo!=null){
                grid.setTotal(pageInfo.getTotal());
                grid.setRows(pageInfo.getList());
            }
            return makeMessage(ReturnCode.SUCCESS,grid);
        }catch (Exception e){
            return makeMessage(ReturnCode.FAILED,grid);
        }

    }

    /**
     * 新增
     * @param grainProjectsprogress
     * @return
     */
    @PreAuthorize("hasAuthority('grainProjectsprogress_add')")
    @PostMapping(value = "addInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "新增信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> addInfo(@ModelAttribute GrainProjectsprogress grainProjectsprogress) {
        return makeMessage(grainProjectsprogressServiceImpl.addInfo(grainProjectsprogress));
    }

    /**
     * 编辑
     * @param grainProjectsprogress
     * @return
     */
    @PreAuthorize("hasAuthority('grainProjectsprogress_edit')")
    @PostMapping(value = "editInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "编辑信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> editInfo(@ModelAttribute GrainProjectsprogress grainProjectsprogress) {
        return makeMessage(grainProjectsprogressServiceImpl.editInfo(grainProjectsprogress));
    }

    /**
     * 删除
     * @param grainProjectsprogress
     * @return
     */
    @PreAuthorize("hasAuthority('grainProjectsprogress_del')")
    @PostMapping(value = "delInfo", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "删除信息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                            dataType = "string", value = "authorization header", defaultValue = "Bearer ")
            }
    )
    public Map<String, Object> delMachine( @ModelAttribute GrainProjectsprogress grainProjectsprogress){
        return makeMessage(grainProjectsprogressServiceImpl.delInfo(grainProjectsprogress));
    }


}
