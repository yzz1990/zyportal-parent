package com.zkzy.zyportal.system.api.service;

import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemUploadnode;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public interface SystemUploadNodeService {
List<SystemUploadnode> selectAll(String param);
    CodeObject insertNode(SystemUploadnode systemUploadnode);
    CodeObject updateNode(SystemUploadnode systemUploadnode);
    SystemUploadnode selectNodebyId(String id);
    CodeObject delNodeById(String id);
}
