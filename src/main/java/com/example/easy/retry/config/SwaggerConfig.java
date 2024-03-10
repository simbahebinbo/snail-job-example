package com.example.easy.retry.config;

import com.aizuda.easy.retry.common.core.util.EasyRetryVersion;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: www.byteblogs.com
 * @date : 2023-07-17 18:19
 * @since 2.1.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Easy Retry Example")
                        .description("<h1>EasyRetry是致力提高分布式业务系统一致性的分布式重试平台</h1> \n" +
                                "<h3>官网地址: https://www.easyretry.com/</h3>" +
                                "<h3>在线体验地址: http://preview.easyretry.com/</h3> " +
                                "<h3>源码地址: https://gitee.com/byteblogs168/easy-retry-demo</h3>" +
                                "<h3>特别提醒: 🌻在您使用测试案例之前请认真的阅读官网.</h3>")
                        .version(EasyRetryVersion.getVersion())
                        .license(new License().name("Apache 2.0").url("https://www.easyretry.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("视频教程:从0到1快速了解分布式重试组件EasyRetry")
                        .url("https://www.ixigua.com/pseries/7272009348824433213/"))
                ;
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                //分组名
                .group("user")
                .pathsToMatch("/**")
                //扫描路径，将路径下有swagger注解的接口解析到文档中
                .packagesToScan("com.example.easy.retry.controller")
                .build();
    }
}






