package com.zkzy.zyportal.system.provider.mapper;

import com.zkzy.zyportal.system.api.entity.WeChatUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeChatUserMapper {

    int deleteByPrimaryKey(WeChatUser record);

    int deleteByWeChatId(String wechatid);

    int insert(WeChatUser record);

    int insertSelective(WeChatUser record);

    WeChatUser selectByPrimaryKey(String id);

    WeChatUser selectByOpenid(String openid);

    int updateByPrimaryKeySelective(WeChatUser record);

    int updateByPrimaryKey(WeChatUser record);

    int updateRemarkByOpenid(WeChatUser record);

    int updateSubscribe(int subscribe);

    List<WeChatUser> selectAll(String param);

    List<WeChatUser> selectUsersSubscribed(String param);

    int updateByOpenid(WeChatUser record);
}