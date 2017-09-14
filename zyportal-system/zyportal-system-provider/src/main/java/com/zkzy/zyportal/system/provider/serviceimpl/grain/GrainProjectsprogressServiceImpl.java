package com.zkzy.zyportal.system.provider.serviceimpl.grain;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.grain.GrainProjectsprogress;
import com.zkzy.zyportal.system.api.service.grain.GrainProjectsprogressService;
import com.zkzy.zyportal.system.provider.mapper.grain.GrainProjectsprogressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/9/12 0012.
 */
@Service("grainProjectsprogressServiceImpl")
public class GrainProjectsprogressServiceImpl implements GrainProjectsprogressService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrainProjectsprogressServiceImpl.class);
    @Autowired
    private GrainProjectsprogressMapper grainProjectsprogressMapper;

    @Override
    public PageInfo infoList(String param, Paging page) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<GrainProjectsprogress> list = grainProjectsprogressMapper.selectAll(param);
            return new PageInfo<>(list);
        }catch (Exception e){
//            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CodeObject addInfo(GrainProjectsprogress grainProjectsprogress) {
        try {
            if (grainProjectsprogress.getId()==null || "".equals(grainProjectsprogress.getId().trim())){
                grainProjectsprogress.setId(RandomHelper.uuid());
                grainProjectsprogressMapper.insert(grainProjectsprogress);
                return ReturnCode.AREA_SUCCESS;//新增成功
            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }

    }

    @Override
    public CodeObject editInfo(GrainProjectsprogress grainProjectsprogress) {
        try {
            if (grainProjectsprogress.getId()!=null && !"".equals(grainProjectsprogress.getId().trim())){
                grainProjectsprogressMapper.updateByPrimaryKey(grainProjectsprogress);
                return ReturnCode.UPDATE_SUCCESS;//更新成功
            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }

    }

    @Override
    public CodeObject delInfo(GrainProjectsprogress grainProjectsprogress) {
        try {
            if (grainProjectsprogress.getId()!=null){
                grainProjectsprogressMapper.deleteByPrimaryKey(grainProjectsprogress.getId());
                return ReturnCode.DEL_SUCCESS;//删除成功
            }else{
                return ReturnCode.DEL_FAILED;//删除失败
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
    }


}
