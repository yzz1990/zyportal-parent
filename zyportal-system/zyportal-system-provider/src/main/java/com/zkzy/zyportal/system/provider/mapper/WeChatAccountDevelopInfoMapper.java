package com.zkzy.zyportal.system.provider.mapper;


import com.zkzy.zyportal.system.api.entity.WeChatAccountDevelopInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface WeChatAccountDevelopInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeChatAccountDevelopInfo record);

    int insertSelective(WeChatAccountDevelopInfo record);

    WeChatAccountDevelopInfo selectByPrimaryKey(String id);

    WeChatAccountDevelopInfo selectByAppid(String appid);

    List<WeChatAccountDevelopInfo> selectByTypecode(String typecode);

    List<WeChatAccountDevelopInfo> selectAll(String param);

    int updateByPrimaryKeySelective(WeChatAccountDevelopInfo record);

    int updateByPrimaryKey(WeChatAccountDevelopInfo record);
}