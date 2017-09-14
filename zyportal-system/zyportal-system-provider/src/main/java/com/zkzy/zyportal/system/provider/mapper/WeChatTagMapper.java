package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.WeChatTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatTagMapper {
    int deleteByTag(WeChatTag record);

    int deleteByWeChatId(String weChatId);

    int insert(WeChatTag record);

    int insertSelective(WeChatTag record);

    WeChatTag selectByPrimaryKey(String id);

    WeChatTag selectByTagid(int tagid);

    List<WeChatTag> selectAll(String param);

    int updateByTag(WeChatTag record);

    int updateByPrimaryKey(WeChatTag record);
}