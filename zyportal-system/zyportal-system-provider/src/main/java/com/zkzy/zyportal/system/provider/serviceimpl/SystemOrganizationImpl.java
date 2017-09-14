package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.service.SystemOrganizationService;
import com.zkzy.zyportal.system.api.viewModel.TreeModel;
import com.zkzy.zyportal.system.provider.mapper.SystemOrganizationMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

@Service("systemOrganizationService")
public class SystemOrganizationImpl implements SystemOrganizationService{
    @Autowired
    private SystemOrganizationMapper systemOrganizationMapper;
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Override
    public List<SystemOrganization> selectAll(String param) {
        return systemOrganizationMapper.selectAll(param);
    }
    @Override
    public List<TreeModel> selectAllTree(String param) {
        List<SystemOrganization>tempList=systemOrganizationMapper.selectAll(param);
        List<TreeModel> list=new ArrayList<TreeModel>();
        for (SystemOrganization o : tempList)
        {
            TreeModel treeModel=new TreeModel();
            treeModel.setId(o.getOrganizationId());
            treeModel.setPid(o.getPid()==null?null:o.getPid().toString());
            treeModel.setName(o.getFullName());
            treeModel.setState("open");
            treeModel.setIconCls("");
            list.add(treeModel);
        }
        return list;
    }


    @Override
    public CodeObject delbyId(String id) {
        try {
            List<SystemUser> list=systemUserMapper.findList(new SystemUser());
            for(SystemUser user:list){
                if(user.getOrganizeId()!=null){
                    String [] ids=user.getOrganizeId().split(",");
                    for(String a:ids){
                        if(a.equals(id)){
                            return new CodeObject("-1","组织正在使用在中，请取消后重试！");
                        }
                    }
                }
            }
            systemOrganizationMapper.del(id);
            return ReturnCode.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UNKNOWN_ERROR;
        }
    }

    @Override
    public CodeObject saveOrganiza(SystemOrganization systemOrganization,String oldPermission){
        Date date=new Date();
        systemOrganization.setLastmod(date);
        systemOrganization.setStatus("A");
        systemOrganization.setState("closed");
        try {
            if(systemOrganization.getOrganizationId()==null){
                SystemOrganization old= systemOrganizationMapper.selectByPermission(systemOrganization.getMyid());
                if(old!=null){
                    return new CodeObject("-1","组织已存在");
                }
                systemOrganization.setCreated(date);
                systemOrganizationMapper.insert(systemOrganization);
            }else{
                if(!oldPermission.equals(systemOrganization.getMyid())){
                    SystemOrganization old= systemOrganizationMapper.selectByPermission(oldPermission);
                    if(old!=null){
                        return new CodeObject("-1","组织已存在");
                    }
                }
                systemOrganizationMapper.update(systemOrganization);
            }
        }catch(Exception e){
            return ReturnCode.UNKNOWN_ERROR;
        }
        return ReturnCode.SUCCESS;
    }


    @Override
    public SystemOrganization getOrganizaByUserId(String id){
        return null;
    }

    @Override
    public List<SystemOrganization> queryOrganizationchildListByMyId(String codeMyid) {
        SystemOrganization o = this.getOrganizationByMyId(codeMyid);
        List<SystemOrganization> list = new ArrayList<SystemOrganization>();;
        if(o!=null){
            list = this.queryOrganizationchildListByPid(o.getOrganizationId());
        }
        return list;
    }

    private List<SystemOrganization> queryOrganizationchildListByPid(String pid) {
        String param=" and status='A' and pid = '"+pid+"' ";
        return systemOrganizationMapper.selectAll(param);
    }

    public SystemOrganization getOrganizationByMyId(String codeMyid){
        String param=" and status='A' and myid = '"+codeMyid+"'";
        List<SystemOrganization> list=systemOrganizationMapper.selectAll(param);
        SystemOrganization o = null;
        if(list != null && list.size() > 0){
            o = list.get(0);
        }
        return o;
    }



}





