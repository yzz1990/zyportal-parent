package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.JobB;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public interface QuartzService {
    public PageInfo list(String param, int currentPage, int pageSize);
    public CodeObject addJob(JobB info);
    public CodeObject edit(JobB info);
    public void delete(JobB info);
    public void pause(JobB info);
    public void resume(JobB info);
    public void removeJob(JobB info);
}
