package com.zkzy.zyportal.system.api.service.grain;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.grain.GrainProjectsprogress;


/**
 * Created by Administrator on 2017/9/12 0012.
 */
public interface GrainProjectsprogressService {
    PageInfo infoList(String param, Paging page);
    CodeObject addInfo(GrainProjectsprogress grainProjectsprogress);
    CodeObject editInfo(GrainProjectsprogress grainProjectsprogress);
    CodeObject delInfo(GrainProjectsprogress grainProjectsprogress);
}
