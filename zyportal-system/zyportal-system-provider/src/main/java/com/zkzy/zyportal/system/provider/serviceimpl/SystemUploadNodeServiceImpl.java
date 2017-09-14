package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemNodefile;
import com.zkzy.zyportal.system.api.entity.SystemUploadnode;
import com.zkzy.zyportal.system.api.service.SystemUploadNodeService;
import com.zkzy.zyportal.system.provider.mapper.SystemNodefileMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemParameterMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemUploadnodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.DateHelper.getEst8Date;

/**
 * Created by admin on 2017/7/18.
 */
@Service("systemUploadNodeService")
public class SystemUploadNodeServiceImpl implements SystemUploadNodeService {
    @Autowired
    private SystemUploadnodeMapper systemUploadnodeMapper;

    @Autowired
    private SystemParameterMapper parameterMapper;

    @Autowired
    private SystemNodefileMapper systemNodefileMapper;

    @Override
    public List<SystemUploadnode> selectAll(String param) {

        return systemUploadnodeMapper.selectAll(param);
    }

    @Override
    public CodeObject insertNode(SystemUploadnode systemUploadnode) {
        if(systemUploadnode!=null){
      List<SystemUploadnode> list=systemUploadnodeMapper.selectNodeByName(systemUploadnode.getName(),systemUploadnode.getParentId().toString());
      if(list.size()>0){
          return ReturnCode.add_FAILED;
      }else {
          systemUploadnode.setId(Long.parseLong(parameterMapper.selectsequence()));
          Date time=getEst8Date();
          systemUploadnode.setCreatedate(time);
          systemUploadnode.setUpdatedate(time);
          systemUploadnode.setType("N");
          systemUploadnodeMapper.insert(systemUploadnode);
          systemUploadnodeMapper.updatePnodeType(systemUploadnode);
          return ReturnCode.AREA_SUCCESS;
      }
        }else{
            return  ReturnCode.AREA_FAILED;
        }
    }

    @Override
    public CodeObject updateNode(SystemUploadnode systemUploadnode) {
        if(systemUploadnode!=null){
            List<SystemUploadnode> list=systemUploadnodeMapper.selectNodeByName(systemUploadnode.getName(),systemUploadnode.getParentId().toString());
            if(list.size()>0&&!(systemUploadnode.getId()).equals(list.get(0).getId())){
                return ReturnCode.edit_FAILED;
            }else {
                Date time=getEst8Date();
                systemUploadnode.setUpdatedate(time);
                systemUploadnodeMapper.updateNodeName(systemUploadnode);
                return  ReturnCode.UPDATE_SUCCESS;
            }
        }else{
            return  ReturnCode.UPDATE_FAILED;
        }
    }

    @Override
    public SystemUploadnode selectNodebyId(String id) {
if(id!=null){
    SystemUploadnode systemUploadnode=systemUploadnodeMapper.selectNodeById(id);
    return systemUploadnode;
}else{
    return null;
}

    }

    @Override
    public CodeObject delNodeById(String id) {
        if(id!=null){
            List<SystemNodefile> list=systemNodefileMapper.selectByPrimaryKey(Long.parseLong(id));
            List<SystemUploadnode> nodes=systemUploadnodeMapper.selectNodeByPid(id);
            if(list.size()>0||nodes.size()>0){
               return  ReturnCode.DELNode_FAILED;
            }else {
                systemUploadnodeMapper.deleteNodeById(id);
                return ReturnCode.DEL_SUCCESS;
            }
        }else {
            return  ReturnCode.DEL_FAILED;
        }

    }

}
