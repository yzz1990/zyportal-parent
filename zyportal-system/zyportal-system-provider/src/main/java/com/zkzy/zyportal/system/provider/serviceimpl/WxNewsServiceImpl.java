package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.zyportal.system.api.service.WxNewsService;
import com.zkzy.zyportal.system.provider.mapper.WxNewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/8/22 0022.
 */
@Service("wxNewsService")
public class WxNewsServiceImpl implements WxNewsService {

    @Autowired
    private WxNewsMapper WxNewsMapper;

    @Override
    public int saveOrUpdateNews() {
        return 0;
    }
}
