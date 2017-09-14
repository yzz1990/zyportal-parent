package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.WeChatKFMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatKFMessageMapper {

    List<WeChatKFMessage> selectAll(String param);

    int deleteByPrimaryKey(String id);

    int insert(WeChatKFMessage record);

    int insertSelective(WeChatKFMessage record);

    WeChatKFMessage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WeChatKFMessage record);

    int updateByPrimaryKey(WeChatKFMessage record);
}