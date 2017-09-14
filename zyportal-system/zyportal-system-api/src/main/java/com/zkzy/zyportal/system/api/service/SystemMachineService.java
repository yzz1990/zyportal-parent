package com.zkzy.zyportal.system.api.service;

import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.api.Paging;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.entity.SystemMachine;
import com.zkzy.zyportal.system.api.viewModel.EquipmentModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by Administrator on 2017/7/7 0007.
 */
public interface SystemMachineService {
    PageInfo machineList(String param, Paging page);
    CodeObject addMachine(SystemMachine systemMachine);
    CodeObject updateMachine(SystemMachine systemMachine);
    CodeObject delMachine(SystemMachine systemMachine);
    List<EquipmentModel> machineHis(SystemMachine systemMachine, String startTime, String endTime) throws ParseException, IOException;
}
