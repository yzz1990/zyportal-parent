package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemRole;
import com.zkzy.zyportal.system.api.service.SystemRoleService;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import com.zkzy.zyportal.system.provider.mapper.SystemRoleMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemRoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

@Service("systemRoleServiceImpl")
public class SystemRoleServiceImpl implements SystemRoleService {
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemRoleMenuMapper systemRoleMenuMapper;


    @Override
    public PageInfo<SystemRole> roleList(String param, Paging page) {
        try {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<SystemRole> list = systemRoleMapper.selectAll(param);
            return new PageInfo<>(list);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CodeObject addRole(SystemRole systemRole) {
        try {
            if (systemRole.getId()==null){
                //判断角色是否存在
                SystemRole oldRole=systemRoleMapper.getRoleByName(systemRole.getName());
                if(oldRole!=null){//角色已存在
                    return ReturnCode.ROLE_EXIST;
                }

                systemRole.setId(RandomHelper.uuid());

                Date date=new Date();
                systemRole.setCreatedate(date);
                systemRole.setUpdatedate(date);

                systemRoleMapper.insert(systemRole);
            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    @Override
    public CodeObject delRole(SystemRole systemRole) {
        try {
            if (systemRole.getId()!=null){
                //角色表
                systemRoleMapper.delRoleByid(systemRole.getId().toString());

                //角色与之关联的权限
                systemRoleMenuMapper.deleteByRoleId(systemRole.getId());

            }else{
                return ReturnCode.DEL_FAILED;//删除失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除成功
    }

    @Override
    public CodeObject updateRole(SystemRole systemRole) {
        try {
            if (systemRole.getId()!=null){
                //判断角色是否存在
                SystemRole oldRole=systemRoleMapper.getRoleByName(systemRole.getName());
                if(oldRole!=null){//角色已存在
                    if(!systemRole.getId().equals(oldRole.getId())){//存在的角色与正在编辑的不是同一个
                        return ReturnCode.ROLE_EXIST;
                    }
                }

                Date date=new Date();
                systemRole.setUpdatedate(date);

                systemRoleMapper.update(systemRole);

            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    @Override
    public List<ZtreeSimpleView> getRoleZtreeSimpleData(String sqlParam) {
        if(sqlParam==null){
            sqlParam="";
        }

        List<ZtreeSimpleView> list=new ArrayList<ZtreeSimpleView>();
        List<SystemRole> roleList = systemRoleMapper.selectAll(" and status='1' ");
        for(SystemRole oneRole:roleList){
            ZtreeSimpleView ztreeSimpleView=new ZtreeSimpleView();
            ztreeSimpleView.setId(oneRole.getId().toString());
            ztreeSimpleView.setName(oneRole.getName());
            ztreeSimpleView.setpId("0");

            list.add(ztreeSimpleView);
        }
        return list;
    }
}
