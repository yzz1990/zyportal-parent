package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemNodefile;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 */
public interface SystemNodefileService {
   List<SystemNodefile> selectAllbyPid(String param);
   CodeObject saveUploadFile(SystemNodefile systemNodefile);
   CodeObject delUploadFile(String id);
   CodeObject updateFile(SystemNodefile systemNodefile,String status);
}
