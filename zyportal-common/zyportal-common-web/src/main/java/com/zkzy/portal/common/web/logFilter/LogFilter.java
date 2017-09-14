package com.zkzy.portal.common.web.logFilter;

import com.zkzy.portal.common.web.util.IpUtils;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.entity.SystemMenu;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LogFilter implements HandlerInterceptor{

    private SystemMenuService systemMenuService;

    public static Map<String,String> menus=new HashedMap();
    public static void setMenus(List<SystemMenu> list){
        menus.clear();
        for(SystemMenu sm:list){
            if(sm.getUrl()!=null){
                menus.put(sm.getUrl(),sm.getPermission());
            }
        }
    }

    public LogFilter(SystemMenuService s){
        systemMenuService=s;
    }
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) {
        String method=request.getMethod();
        LogDao logDao=null;

        if("OPTIONS".equalsIgnoreCase(method)){
            return;
        }
        //ip
        String ip= IpUtils.getIpAddress(request);
        if(ip.split("\\.").length!=4){
            return;
        }
        String username=null;
        try {
            username=WebUtils.getCurrentUser().getUsername();
        }catch(Exception e){
            username="";
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SystemLog systemLog=new SystemLog();
        //获取客户端请求信息

        systemLog.setIp(ip);
        //代理
        systemLog.setUseragent(request.getHeader("User-Agent"));
        //用户登录名
        systemLog.setUsername(username);
        //访问路径
        String URI=request.getRequestURI();
        systemLog.setUri(URI);
        //菜单权限名
        String permission=menus.get(URI);
        if(permission==null){
            return;
        }
        systemLog.setPermission(permission);
        //时间
        systemLog.setTime(sdf.format(new Date()));
        logDao=new LogDao(systemMenuService,systemLog);
        Thread t=new Thread(logDao);
        t.start();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
