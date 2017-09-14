package com.zkzy.portal.client.common.config;

import com.zkzy.portal.common.web.config.AbstractWebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.cors.CorsUtils;

/**
 * spring-security配置
 *
 * @author admin
 */
@Configuration
public class WebSecurityConfig extends AbstractWebSecurityConfig {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略权限校验的访问路径
        web
                .ignoring()
                .antMatchers(
                        "/hello",
                        "/favicon.ico",
                        "/swagger**/**",
                        "/*/api-docs",
                        "/webjars/**",
                        "/*/sms/captcha",
                        "/*/currency/**",
                        "/zyportal5/**",
                        "/Param/Parameter/selectByMyId"
                )
        //.antMatchers(HttpMethod.POST, "/system/*/*")
        ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                // 例如以下代码指定了/和/auth/token不需要任何认证就可以访问，其他的路径都必须通过身份验证。
                .antMatchers("/*/auth/token").permitAll()
                //开启跨域验证
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        //关闭跨域保护
//        security.csrf().disable();
        super.configure(security);
    }

    //官方自带默认用户名密码
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//    }
}
