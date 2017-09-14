package com.zkzy.zyportal.system.api.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
public interface PgdaService {
    Map<String,Object> getPgdaInfoList(String oldManid, String param,int currentPage,int pageSize);
}
