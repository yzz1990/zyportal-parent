package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.WeChatMassMessage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WeChatMassMessageMapper {
    int deleteByPrimaryKey(String id);

    int insert(WeChatMassMessage record);

    int insertSelective(WeChatMassMessage record);

    WeChatMassMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeChatMassMessage record);

    int updateByPrimaryKey(WeChatMassMessage record);
}