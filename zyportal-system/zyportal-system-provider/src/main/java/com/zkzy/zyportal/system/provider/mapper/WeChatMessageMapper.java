package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.WeChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeChatMessage record);

    int insertSelective(WeChatMessage record);

    WeChatMessage selectByPrimaryKey(String id);

    List<WeChatMessage> selectAll(String param);

    int updateByPrimaryKeySelective(WeChatMessage record);

    int updateByPrimaryKey(WeChatMessage record);
}