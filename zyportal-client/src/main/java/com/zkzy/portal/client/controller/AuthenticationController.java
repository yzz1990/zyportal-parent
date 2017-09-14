package com.zkzy.portal.client.controller;

import com.zkzy.portal.client.common.controller.BaseController;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.constant.ReturnCode;
import com.zkzy.portal.client.security.utils.TokenUtil;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.FileUtil;
import com.zkzy.portal.common.utils.UserAgent;
import com.zkzy.portal.common.utils.UserAgentUtil;
import com.zkzy.portal.common.web.logFilter.LogDao;
import com.zkzy.portal.common.web.logFilter.LogFilter;
import com.zkzy.portal.common.web.security.AbstractTokenUtil;
import com.zkzy.portal.common.web.security.AuthenticationTokenFilter;
import com.zkzy.portal.common.web.util.IpUtils;
import com.zkzy.portal.common.web.util.WebUtils;
import com.zkzy.zyportal.system.api.entity.SystemIp;
import com.zkzy.zyportal.system.api.entity.SystemLog;
import com.zkzy.zyportal.system.api.service.ISystemUserService;
import com.zkzy.zyportal.system.api.service.SystemMenuService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Authentication controller.
 *
 * @author admin
 */
@RestController
@RequestMapping("/Permission/auth")
@Api(tags = "权限管理")
public class AuthenticationController extends BaseController {

    @Autowired
    private ISystemUserService systemUserService;
    /**
     * 权限管理
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 用户信息服务
     */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * Token工具类
     */
    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private RedisRepository redisRepository;
    /**
     * Create authentication token map.
     *
     * @param username the username
     * @param password the password
     * @return the map
     */
    @PostMapping(value = "/token", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "获取token")
    public Map<String, Object> createAuthenticationToken(
        @ApiParam(required = true, value = "用户名") @RequestParam(value="username",defaultValue = "root") String username,
        @ApiParam(required = true, value = "密码") @RequestParam(value="password",defaultValue = "root") String password,
        HttpServletRequest request,HttpServletResponse response
    ) throws ParseException {

        //完成授权
        //1、用户名、密码组合生成一个Authentication对象（也就是UsernamePasswordAuthenticationToken对象）。
        //2、生成的这个token对象会传递给一个AuthenticationManager对象用于验证。
        //3、当成功认证后，AuthenticationManager返回一个Authentication对象，认证后的身份。
        //4、接下来，就可以调用
        //SecurityContextHodler.getContext().setAuthentication(…)。
        Authentication authentication=null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        }catch(Exception e){
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SystemLog systemLog=new SystemLog();
            //获取客户端请求信息
            //ip
            String ip= IpUtils.getIpAddress(request);
            systemLog.setIp(ip);
            //代理
            systemLog.setUseragent(request.getHeader("User-Agent"));
            //用户登录名
            systemLog.setUsername(WebUtils.getCurrentUser().getUsername());
            //访问路径
            String URI=request.getRequestURI();
            systemLog.setUri(URI);
            //菜单权限名
            String permission=LogFilter.menus.get(URI);
            systemLog.setPermission(permission);
            //时间
            systemLog.setTime(sdf.format(new Date()));
            if(permission!=null){
                LogDao logDao=new LogDao(systemMenuService,systemLog);
                Thread t=new Thread(logDao);
                t.start();
            }
            Map<String, Object> message = new HashMap<>();
            message.put(Message.RETURN_FIELD_CODE, ReturnCode.BAD_REQUEST);
            message.put(Message.RETURN_FIELD_CODE_DESC, "账号或密码错误!");
            return message;
        }

        //判断是否在允许时间段内
        SystemIp systemIp=new SystemIp();
        String ip= IpUtils.getIpAddress(request);
        systemIp.setIp(ip);
        SystemIp sip= systemMenuService.queryIpAll(systemIp);
        if(sip!=null&&sip.getStarttime()!=null&&sip.getEndtime()!=null){

            SimpleDateFormat simsdf=new SimpleDateFormat("yyyy-MM-dd");
            Date now=new Date();
            String st=sip.getStarttime();
            String et=sip.getEndtime();
            Date sdt= simsdf.parse(st);
            Date edt= simsdf.parse(et);
            if(!(now.getTime()>=sdt.getTime()&&now.getTime()<=edt.getTime())){
                //不在范围就踢下去
                redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_AUTH+username);
                redisRepository.del(AbstractTokenUtil.REDIS_PREFIX_USER+username);
                Map<String, Object> message = new HashMap<>();
                message.put(Message.RETURN_FIELD_CODE, ReturnCode.BAD_REQUEST);
                message.put(Message.RETURN_FIELD_CODE_DESC, "此账户不在允许登陆时段!");
                return message;
            }
        }
        if(sip.getStatus().equals("0")){
            Map<String, Object> message = new HashMap<>();
            message.put(Message.RETURN_FIELD_CODE, ReturnCode.BAD_REQUEST);
            message.put(Message.RETURN_FIELD_CODE_DESC, "此IP被限制登陆！");
            return message;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //生成自定义token
        final String token = jwtTokenUtil.generateToken(userDetails);

        String agent=request.getHeader("User-Agent");
        UserAgent UserAgent=UserAgentUtil.getUserAgent(agent);
        systemUserService.updateOSAndBroswer(UserAgent.getPlatformType()+UserAgent.getPlatformSeries(),UserAgent.getBrowserType()+","+UserAgent.getBrowserVersion(),userDetails.getUsername());

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", token);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, tokenMap);
        return message;
    }

    /**
     * Refresh and get authentication token map.
     *
     * @param request the request
     * @return the map
     */
    @GetMapping(value = "/refresh", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "刷新token")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> refreshAndGetAuthenticationToken(
        HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        //重新生成Token
        String username = jwtTokenUtil.getUsernameFromToken(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String refreshedToken = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", refreshedToken);
        tokenMap.put("expires_in", jwtTokenUtil.getExpiration());
        tokenMap.put("token_type", TokenUtil.TOKEN_TYPE_BEARER);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);
        message.put(Message.RETURN_FIELD_DATA, tokenMap);

        return message;
    }

    /**
     * Delete authentication token map.
     *
     * @param request the request
     * @return the map
     */

    @DeleteMapping(value = "/token", produces = "application/json; charset=UTF-8")
    @ApiOperation(value = "清空token")
    @ApiImplicitParams(
        {
            @ApiImplicitParam(name = "Authorization", required = true, paramType = "header",
                dataType = "string", value = "authorization header", defaultValue = "Bearer ")
        }
    )
    public Map<String, Object> deleteAuthenticationToken(

        HttpServletRequest request) {

        String tokenHeader = request.getHeader(AuthenticationTokenFilter.TOKEN_HEADER);
        String token = tokenHeader.split(" ")[1];

        //移除token
        jwtTokenUtil.removeToken(token);

        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, ReturnCode.SUCCESS);

        return message;
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBusinessException(BadCredentialsException ex) {
        //用户名或密码错误
        return makeErrorMessage(ReturnCode.INVALID_GRANT, "用户名密码错误", ex.getMessage());
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleBusinessException(DisabledException ex) {
        //用户被停用
        return makeErrorMessage(ReturnCode.DISABLED_USER, "用户被锁定", ex.getMessage());
    }

}
