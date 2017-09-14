package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.PensionOldmanhomeB;
import com.zkzy.zyportal.system.api.service.PensionOldmanhomeBService;
import com.zkzy.zyportal.system.provider.mapper.PensionOldmanhomeBMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.zkzy.portal.common.utils.DateHelper.formatDateTime;
import static com.zkzy.portal.common.utils.DateHelper.getDate;
import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by admin on 2017/5/10.
 */
@Service("pensionOldmanhomeBService")
public class PensionOldmanhomeBServiceImpl implements PensionOldmanhomeBService{
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private PensionOldmanhomeBMapper pensionOldmanhomeBMapper;

    @Override
    public CodeObject InsertOldmanHome(PensionOldmanhomeB home) {
        if(home!=null){
         List<PensionOldmanhomeB> list=pensionOldmanhomeBMapper.selectbyName(home);
         if(list.size()>0){
          return  ReturnCode.INSERT_FAILED;
         }
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            home.setId(uuid());
            home.setCreatetime(localtime);
            home.setModifytime(localtime);
            pensionOldmanhomeBMapper.insert(home);
        return  ReturnCode.AREA_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }

    }

    @Override
    public CodeObject DeleteById(String ID) {
        if(ID!=null){
           pensionOldmanhomeBMapper.deleteByPrimaryKey(ID);
            return ReturnCode.DEL_SUCCESS;
        }else{
            return ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject UpdateById(PensionOldmanhomeB home) {
        if(home!=null){
            String localtime=getDate(DEFAULT_DATETIME_FORMAT);
            home.setModifytime(localtime);
            pensionOldmanhomeBMapper.updateByPrimaryKey(home);
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return ReturnCode.UPDATE_FAILED;
        }

    }

    @Override
    public PensionOldmanhomeB SelectById(String ID) {
        PensionOldmanhomeB home=null;
        if(ID!=null){
          home=pensionOldmanhomeBMapper.selectByPrimaryKey(ID);
        }
        return home;
    }

    @Override
    public PageInfo SelectAll(int currentPage, int pageSize,String param) {
        PageHelper.startPage(currentPage,pageSize);//分页
        List<PensionOldmanhomeB> Oldmanhomes= pensionOldmanhomeBMapper.selectAll(param);
        PageInfo pageInfo=new PageInfo(Oldmanhomes);
        return pageInfo;
    }

    @Override
    public List<PensionOldmanhomeB> SelectName() {
        return pensionOldmanhomeBMapper.selectName();
    }

}
