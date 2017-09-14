package com.zkzy.zyportal.system.api.service;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/8 0008.
 */
public interface ChartService {
    Map<String,Object> userOperationChart(String type, String who);
}
