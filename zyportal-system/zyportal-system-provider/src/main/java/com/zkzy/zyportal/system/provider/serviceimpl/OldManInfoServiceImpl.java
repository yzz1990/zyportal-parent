package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.PensionOldmaninfoB;
import com.zkzy.zyportal.system.api.service.OldManInfoService;
import com.zkzy.zyportal.system.provider.mapper.PensionOldmaninfoBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8 0008.
 */

@Service("oldManInfoService")
public class OldManInfoServiceImpl implements OldManInfoService{
    @Autowired
    private PensionOldmaninfoBMapper pensionOldmaninfoBMapper;

    /**
     * 按param条件查询老人基础信息表分页
     * @param param
     * @return
     */
    @Override
    public PageInfo getOldManInfoList(String param,int currentPage,int pageSize) {
        if(param==null){
            param="";
        }
        try {
            PageHelper.startPage(currentPage,pageSize);//分页
            List<PensionOldmaninfoB> list=pensionOldmaninfoBMapper.selectOldManInfoList(param);
            PageInfo pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {

        }
    }

    /**
     * 新增老人信息
     * @param pensionOldmaninfoB
     * @return
     */
    @Override
    public CodeObject save(PensionOldmaninfoB pensionOldmaninfoB,String userId) {
        try {
            if (pensionOldmaninfoB!=null){

                pensionOldmaninfoB.setId(RandomHelper.uuid());
                pensionOldmaninfoB.setRecentrating("-1");//无评估等级
                pensionOldmaninfoB.setEvaluationcount("0");//评估次数0
                pensionOldmaninfoB.setCreatetime(DateHelper.getDateTime());
                pensionOldmaninfoB.setModifytime(DateHelper.getDateTime());

                pensionOldmaninfoBMapper.insert(pensionOldmaninfoB);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    /**
     * 编辑老人信息
     * @param pensionOldmaninfoB
     * @return
     */
    @Override
    public CodeObject update(PensionOldmaninfoB pensionOldmaninfoB,String userId) {
        try {
            if (pensionOldmaninfoB!=null){
                pensionOldmaninfoB.setModifytime(DateHelper.getDateTime());

                pensionOldmaninfoBMapper.updateByPrimaryKey(pensionOldmaninfoB);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    /**
     * 删除老人信息
     * @param  delId
     * @return
     */
    @Override
    public CodeObject delete(String delId) {
        try {
            pensionOldmaninfoBMapper.deleteByPrimaryKey(delId);
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除成功
    }


}
