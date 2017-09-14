package com.zkzy.zyportal.system.provider.serviceimpl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.WeChatAccountDevelopInfo;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.provider.mapper.WeChatAccountDevelopInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by Jisy on 2017/7/12.
 */
@Service("weChatAccountDevelopInfoService")
public class WeChatAccountDevelopInfoServiceImpl implements WeChatAccountDevelopInfoService {

    @Autowired
    private WeChatAccountDevelopInfoMapper weChatAccountDevelopInfoMapper;

    @Override
    public CodeObject add(WeChatAccountDevelopInfo info) {
        if(weChatAccountDevelopInfoMapper.selectByAppid(info.getAppid())==null&&info!=null){
            info.setId(uuid());
            weChatAccountDevelopInfoMapper.insert(info);
            return ReturnCode.ADD_ACCOUNT_SUCCESS;
        }else{
        return ReturnCode.ADD_ACCOUNT_FAILED;
        }

    }

    @Override
    public CodeObject DeleteById(String id) {
        if(id!=null&&id!=""){
            weChatAccountDevelopInfoMapper.deleteByPrimaryKey(id);
            return ReturnCode.DELETE_ACCOUNT_SUCCESS;
        }else{
            return ReturnCode.DELETE_ACCOUNT_FAILED;
        }
    }

    @Override
    public CodeObject UpdateById(WeChatAccountDevelopInfo info) {
        if(info!=null){
            weChatAccountDevelopInfoMapper.updateByPrimaryKey(info);
            return ReturnCode.UPDATE_ACCOUNT_SUCCESS;
        }else{
            return ReturnCode.UPDATE_ACCOUNT_FAILED;
        }
    }

    @Override
    public WeChatAccountDevelopInfo selectById(String id){
        if(id!=null&&id!=""){
            return weChatAccountDevelopInfoMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public PageInfo selectByTypecode(int currentPage, int pageSize, String typecode){
        PageHelper.startPage(currentPage,pageSize);
        List<WeChatAccountDevelopInfo> list = weChatAccountDevelopInfoMapper.selectByTypecode(typecode);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public PageInfo selectAll(int currentPage, int pageSize, String param) {
        PageHelper.startPage(currentPage,pageSize);
        List<WeChatAccountDevelopInfo> list = weChatAccountDevelopInfoMapper.selectAll(param);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
}
