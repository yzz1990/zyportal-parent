package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SystemCode;
import com.zkzy.zyportal.system.api.service.SystemCodeService;
import com.zkzy.zyportal.system.provider.mapper.SystemCodeMapper;
import com.zkzy.zyportal.system.provider.mapper.SystemParameterMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.zkzy.portal.common.utils.DateHelper.getEst8Date;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

@Service("systemCodeService")
public class SystemCodeServiceImpl  implements SystemCodeService{
    public static final String BASE_NAME="base";

    @Autowired
    private SystemCodeMapper systemCodeMapper;

    @Autowired
    private SystemParameterMapper parameterMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public List<SystemCode> selectAll(String param) {
        return systemCodeMapper.selectAll(param);
    }

    @Override
    public PageInfo selectparams(int currentPage, int pageSize, String sqlParam) {
        PageHelper.startPage(currentPage,pageSize);
        List<SystemCode> list=systemCodeMapper.selectAll(sqlParam);
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public CodeObject addSystemCode(SystemCode systemCode) {
if(systemCode!=null){
    String systemCodeId=parameterMapper.selectsequence();
    Date time=getEst8Date();
    systemCode.setCreated(time);
    systemCode.setLastmod(time);
    systemCode.setStatus(ReturnCode.PERSISTENCE_STATUS);
    systemCode.setCodeId(Long.parseLong(systemCodeId));
    systemCode.setType("D");
    if(systemCode.getParentId()==null||systemCode.getParentId().equals("")){
systemCode.setState(ReturnCode.TREE_STATUS_OPEN);
    }else{
        String codeparam="AND CODE_ID ="+systemCode.getParentId()+"";
        List<SystemCode> code=systemCodeMapper.selectAll(codeparam);
       String state=code.get(0).getState();
       if(!ReturnCode.TREE_STATUS_CLOSED.equals(state)){
      SystemCode sysCode= code.get(0);
      sysCode.setState(ReturnCode.TREE_STATUS_CLOSED);
           systemCodeMapper.updateStateById(sysCode);
       }
       systemCode.setState(ReturnCode.TREE_STATUS_OPEN);
    }
    systemCodeMapper.insert(systemCode);
    redisTemplate.opsForValue().set(BASE_NAME+systemCode.getCodeMyid(), systemCode.getName());
    return  ReturnCode.AREA_SUCCESS;
}else {
 return  ReturnCode.AREA_FAILED;
}
    }

    @Override
    public CodeObject delSystemCode(SystemCode systemCode) {
        if (systemCode!=null){
            systemCode.setLastmod(getEst8Date());
            systemCode.setStatus(ReturnCode.PERSISTENCE_DELETE_STATUS);
            systemCodeMapper.delParamById(systemCode);
            redisTemplate.delete(BASE_NAME+systemCode.getCodeMyid());
        List<SystemCode> list=systemCodeMapper.selectTaskById(systemCode);
        if(list.size()<=0){
systemCode.setCodeId(systemCode.getParentId());
systemCode.setState(ReturnCode.TREE_STATUS_OPEN);
systemCodeMapper.updateStateById(systemCode);
        }
            return ReturnCode.DEL_SUCCESS;
        }else {
            return ReturnCode.DEL_FAILED;
        }

    }

    @Override
    public CodeObject updateSystemCode(SystemCode systemCode) {
        if(systemCode!=null){

            systemCode.setLastmod(getEst8Date());
            systemCodeMapper.updateParamById(systemCode);
            SystemCode codeParam=systemCodeMapper.selectCodeById(systemCode);
            redisTemplate.delete(BASE_NAME+systemCode.getCodeMyid());
            redisTemplate.opsForValue().set(BASE_NAME+codeParam.getCodeMyid(),codeParam.getName());
            return ReturnCode.UPDATE_SUCCESS;
        }else{
            return  ReturnCode.UPDATE_FAILED;
        }

    }

    @Override
    public SystemCode selectById(SystemCode systemCode) {
        SystemCode code=null;
        if(systemCode!=null){
         code=systemCodeMapper.selectCodeById(systemCode);
        }
        return code;
    }

    @Override
    public SystemCode selectByMyid(SystemCode systemCode) {
        SystemCode code=null;
        if(systemCode!=null){
            code=systemCodeMapper.selectCodeByMyId(systemCode);
        }
        return code;
    }

    @Override
    public  List<SystemCode> selectPemisson(String param) {
        List<SystemCode> code=null;
        code=systemCodeMapper.selectPemisson(param);
        return code;
    }

    @Override
    public  List<SystemCode> selectParentid(String param) {
        List<SystemCode> code=null;
        if(param!=null){
            code=systemCodeMapper.selectParentid(param);
        }
        return code;
    }

    @Override
    public List<SystemCode> selectAllparams(String param) {
        return  systemCodeMapper.selectAll(param);
    }

    @Override
    public List<SystemCode> selectTimeTask() {
        SystemCode systemCode=new SystemCode();
        String id="1";
        systemCode.setParentId(Long.parseLong(id));
        return systemCodeMapper.selectTaskById(systemCode);
    }

    @Override
    public List<SystemCode> selectAllchilds(long parentid) {
        SystemCode systemCode=new SystemCode();
        systemCode.setParentId(parentid);
        return systemCodeMapper.selectTaskById(systemCode);
    }
    @Override
    public List selectBeanAndClass(String parentId, String id) {
        if(!"".equals(parentId)){
            SystemCode systemCode=new SystemCode();
            systemCode.setParentId(Long.parseLong(parentId));
            List<SystemCode> list=systemCodeMapper.selectTaskById(systemCode);
            List<Map<String,Object>> code=new ArrayList<>();
             for (SystemCode type:list){
                 Map<String,Object> map=new HashedMap();
                 Long pid=type.getCodeId();
                 SystemCode childcode=new SystemCode();
                 childcode.setParentId(pid);
                 List<SystemCode> child=systemCodeMapper.selectTaskById(childcode);
//                 for (int a=0;a<child.size();a++){
//                     child.get(a).setParentId(Long.parseLong(parentId));
//                 }

                 map.put("type",type.getName());
                 map.put("typedata",child);
                 code.add(map);
//                 code.addAll(child);
             }
             return code;
        }else {
            return null;
        }


    }


}
