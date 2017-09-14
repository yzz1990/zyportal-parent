package com.zkzy.portal.client.security;

import com.zkzy.portal.client.security.model.AuthUserFactory;
import com.zkzy.portal.common.web.util.IpUtils;
import com.zkzy.portal.common.web.util.IpUtils_baidu;
import com.zkzy.zyportal.system.api.entity.SystemIp;
import com.zkzy.zyportal.system.api.entity.SystemUser;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Security User Detail Service
 *
 * @author admin
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * 系统服务
     */
    @Autowired
    private ISystemUserService systemUserService;
    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private HttpServletRequest request;

    private static String status="1";

    @Override
    public UserDetails loadUserByUsername(String userName) {
        SystemUser user = systemUserService.getUserByLoginName(userName);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", userName));
        } else {
            //更新user的数据
            user.setUpdateDate(new Date());
            try {
                String ip= IpUtils.getIpAddress(request);
                user.setIp(ip);
                if(ip.equals("127.0.0.1")){
                    user.setArea("IANA");
                    user.setLng("");
                    user.setLat("");
                }else{
                    Map<String,String> map=IpUtils_baidu.getIpInfo(ip);
                    if (map!=null){
                        user.setArea(map.get("city"));
                        user.setLng(map.get("lng"));
                        user.setLat(map.get("lat"));
                    }
                }

                //更新纯真ip表
                SystemIp systemIp=new SystemIp();
                systemIp.setLocation(user.getArea());
                systemIp.setIp(user.getIp());
                systemIp.setLng(user.getLng());
                systemIp.setLat(user.getLat());
                systemIp.setOperatingTime(sdf.format(new Date()));
                systemIp.setRecentOperator(user.getUsername());
                systemIp.setStatus("1");
                systemMenuService.saveIp(systemIp);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                systemUserService.updateInfo(user);
                //session.setAttribute("onlineUser"+user.getId(),user);

//                return AuthUserFactory.create(user);
                return AuthUserFactory.create_AuthSystemUsers(user);
            }
        }
    }




}
