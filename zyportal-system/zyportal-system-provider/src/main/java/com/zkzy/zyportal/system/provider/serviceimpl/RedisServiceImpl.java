package com.zkzy.zyportal.system.provider.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.zyportal.system.api.entity.SystemCode;
import com.zkzy.zyportal.system.api.entity.SystemOrganization;
import com.zkzy.zyportal.system.api.entity.SystemParameter;
import com.zkzy.zyportal.system.api.service.RedisService;
import com.zkzy.zyportal.system.api.service.SystemCodeService;
import com.zkzy.zyportal.system.api.service.SystemOrganizationService;
import com.zkzy.zyportal.system.api.service.SystemParameterService;
import com.zkzy.zyportal.system.api.viewModel.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */


//@Component
@Service("redisService")
@Transactional(readOnly = true)
public class RedisServiceImpl implements RedisService,CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class);

    public static final String BASE_NAME="base";

    @Autowired
    private RedisRepository redisRepository;


//    @Autowired
//    private RedisUtil redisUtil
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private SystemCodeService systemCodeService;
    @Autowired
    private SystemParameterService systemParameterService;
    @Autowired
    private SystemOrganizationService systemOrganizationService;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>服务启动执行，执行加载Redis数据操作开始<<<<<<<<<<<<<");
        //操作数据字典
        try {
            LOGGER.info("操作数据字典,数据缓存进redis开始");
            String param="";
            List<SystemCode> systemCodeLists=systemCodeService.selectAll(param);
            List<SystemCode> childList = null;
            for(SystemCode systemCode : systemCodeLists){
                String codeMyid = systemCode.getCodeMyid();
                param=" and PARENT_ID in (select code_id from system_code "
                        + " where CODE_MYID='"+codeMyid+"' and status='A' )  and status='A' order by SORT asc ";
                childList=systemCodeService.selectAll(param);
                /**存入自己的值到redis*/
                redisTemplate.opsForValue().set(BASE_NAME+codeMyid, systemCode.getName());
                /**如果存在子元素则存入缓存*/
                if(childList != null && childList.size() > 0){
                    Json json=new Json();
                    json.setStatus(true);
                    json.setSystemCodeList(childList);
                    String jsonStr = JSONObject.toJSONString(json);
                    redisTemplate.opsForValue().set(codeMyid, systemCode.getName());
                }
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally{
            LOGGER.info("操作数据字典,数据缓存进redis结束");
        }
        //操作系统参数
        try {
            LOGGER.info("操作系统参数,数据缓存进redis开始");
            String param=" and myid is not null and STATE='Y' ";
            List<SystemParameter> list =systemParameterService.selectAll(param);
            for(SystemParameter parameter : list){
                String codeMyid = parameter.getMyid();
                Json json=new Json();
                json.setStatus(true);
                json.setParameter(parameter);
                String jsonStr = JSONObject.toJSONString(json);
                /**存入自己的值到redis*/
                redisTemplate.opsForValue().set(BASE_NAME+codeMyid,jsonStr);
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info("操作系统参数,数据缓存进redis结束");
        }
        //操作组织表
        try {
            LOGGER.info("操作组织表,数据缓存进redis开始");
            String param=" and status='A' and myid is not null ";
            List<SystemOrganization> list =systemOrganizationService.selectAll(param);
            List<SystemOrganization> childList = null;
            for(SystemOrganization o : list){
                String codeMyid = o.getMyid();
//                param=" and status='A' and myid = '"+codeMyid+"'";
                childList = systemOrganizationService.queryOrganizationchildListByMyId(codeMyid);
                /**存入自己的值到redis*/
                redisTemplate.opsForValue().set(BASE_NAME+codeMyid,o.getFullName());
                /**如果存在子元素则存入缓存*/
                if(childList != null && childList.size() > 0){
                    Json json=new Json();
                    json.setStatus(true);
                    json.setOrganizationList(childList);
                    String jsonStr = JSONObject.toJSONString(json);
                    redisTemplate.opsForValue().set(codeMyid,jsonStr);
                }
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            LOGGER.info("操作组织表,数据缓存进redis结束");
        }
        LOGGER.info(">>>>>>>>>>>>>>>服务启动执行，执行加载Redis数据操作结束<<<<<<<<<<<<<");

//        machineData();

    }

    /**
     * 根据code取出对应的系统参数值
     * @param code
     * @return
     */
    @Override
    public String getParameterValueByCode(String code) {
        /**从redis中取出数据*/
        String redisStr = "";
       try {
           String jsonStr=redisTemplate.opsForValue().get(this.BASE_NAME + code);
           Json json=JSONObject.parseObject(jsonStr, Json.class);
           if(json != null){
               SystemParameter pp = json.getParameter();
               if(pp != null){
                   redisStr = pp.getValue();
               }
           }
       }catch (Exception e){
//            e.printStackTrace();
           LOGGER.error(e.getMessage());
       }finally {
            return redisStr;
       }
    }

    /**
     * 根据code取出对应的子元素数据字典集合
     * @param code
     * @return
     */
    @Override
    public String getSystemCodeListByCode(String code) {
        StringBuilder sb = new StringBuilder();
        /**从redis中取出数据*/
        try {
            String jsonStr = redisTemplate.opsForValue().get(code);
            Json json=JSONObject.parseObject(jsonStr, Json.class);
            if(json != null){
                List<SystemCode> list = json.getSystemCodeList();
                if(list != null && list.size()>0){
                    for(SystemCode systemCode : list){
                        sb.append("<option value=\""+systemCode.getCodeMyid()+"\">"+systemCode.getName()+"</option>");
                    }
                }
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            return sb.toString();
        }
    }

    /**
     * 根据code取出对应的数据字典名称
     * @param code
     * @return
     */
    @Override
    public String getSystemCodeNameByCode(String code) {
        String jsonStr="";
        try {
            /**从redis中取出数据*/
            jsonStr=redisTemplate.opsForValue().get(this.BASE_NAME + code);
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            return jsonStr;
        }
    }

    /**
     * 根据code取出对应的子元素组织机构集合
     * @param code
     * @return
     */
    @Override
    public String getOrganizationListByCode(String code) {
        StringBuilder sb = new StringBuilder();
        /**从redis中取出数据*/
        try {
            String jsonStr = redisTemplate.opsForValue().get(code);
            Json json=JSONObject.parseObject(jsonStr, Json.class);
            if(json != null){
                List<SystemOrganization> list = json.getOrganizationList();
                if(list != null && list.size()>0){
                    for(SystemOrganization o : list){
                        sb.append("<option value=\""+o.getMyid()+"\">"+o.getFullName()+"</option>");
                    }
                }
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            return sb.toString();
        }
    }

    /**
     * 根据code取出对应的子元素组织机构集合LIST
     * @param code
     * @return
     */
    @Override
    public List<SystemOrganization> getOrganizationListBeanByCode(String code) {
        List<SystemOrganization> list=null;
        try {
            String jsonStr = redisTemplate.opsForValue().get(code);
            Json json=JSONObject.parseObject(jsonStr, Json.class);
            if(json != null){
                list = json.getOrganizationList();
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            return list;
        }

    }

    /**
     * 根据code取出对应的组织机构名称
     * @param code
     * @return
     */
    @Override
    public String getOrganizationNameByCode(String code) {
        String redisStr="";
        /**从redis中取出数据*/
        try {
            String jsonStr=redisTemplate.opsForValue().get(this.BASE_NAME + code);
            if(jsonStr != null){
                redisStr = jsonStr;
            }
        }catch (Exception e){
//            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }finally {
            return redisStr;
        }
    }


    /**
     * 获取在线的用户信息List
     * @param param
     * @return
     */
    @Override
    public String getOnlineUserList(String param) {
        //List<UserDetails> usrDetailsList=new ArrayList<UserDetails>();
//        String key = AbstractTokenUtil.REDIS_PREFIX_USER+param;
//        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
//
//        byte[] keys = serializer.serialize(key);
//
//        byte[] values = connection.get(keys);
//        Object o =serializer.deserialize(values);
//
////        byte[] str=redisRepository.get(keys);
////        String jsonStr=redisTemplate.opsForValue().get(key);

//        String key = AbstractTokenUtil.REDIS_PREFIX_USER+param;
//        String str =redisRepository.get("user-details:root");

//        redisTemplate.opsForList().leftPop(param);

//        String str =redisTemplate.opsForList().leftPop("user-details*");

        return null;
    }

    /**
     * 模拟在线设备的信息
     */
//    public void machineData(){
//        String machineNo="no001";
//        Map<String,String> map=new HashedMap();
//        map.put("machineNo",machineNo);
//        map.put("lastlinkedtime","2017-07-13 12:00:00");
//        map.put("lastdistime","2017-07-13 12:10:00");
//        map.put("getdatatime","2017-07-13 12:05:00");
//        map.put("data","...数据");
//
//        String jsonStr = JSONObject.toJSONString(map);
//        redisTemplate.opsForValue().set("machineOnline:"+machineNo, jsonStr);
//        redisTemplate.opsForValue().set("machineOnline:"+"no002", jsonStr);
//        redisTemplate.opsForValue().set("machineOnline:"+"no003", jsonStr);
//        redisTemplate.opsForValue().set("machineOnline:"+"no004", jsonStr);
//        redisTemplate.opsForValue().set("machineOnline:"+"no005", jsonStr);
//    }







}
