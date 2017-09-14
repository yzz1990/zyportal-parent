package com.zkzy.portal.client;

import com.zkzy.zyportal.system.api.util.CcpRestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.MultipartConfigElement;

/**
 * The type Client mobile application.
 *
 * @author admin
 */
@RestController
@SpringBootApplication
@ImportResource("classpath:dubbo-consumer.xml")
public class ClientMobileApplication {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientMobileApplication.class);

    /**
     * Hello string.
     *
     * @return the string
     */
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    /**
     * 文件上传临时路径
     */
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setLocation("e:/data");
//        return factory.createMultipartConfig();
//    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ClientMobileApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        LOGGER.info("客户端启动成功！");
    }
}
