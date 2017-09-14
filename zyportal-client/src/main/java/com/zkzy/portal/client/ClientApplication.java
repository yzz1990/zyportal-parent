package com.zkzy.portal.client;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by yupc on 2017/5/12.
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
public class ClientApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(ClientMobileApplication.class);
	}
}