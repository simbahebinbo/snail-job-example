package com.example.config;

import com.aizuda.snailjob.common.core.util.SnailJobVersion;
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
                        .title("Snail Job Example")
                        .description("<h1>SnailJob是一个灵活，可靠和快速的分布式任务重试和分布式任务调度平台</h1> \n" +
                                "<h3>官网地址: https://snailjob.opensnail.com/</h3>" +
                                "<h3>在线体验地址: https://preview.snailjob.opensnail.com/</h3> " +
                                "<h3>源码地址: https://gitee.com/opensnail/snail-job-demo</h3>" +
                                "<h3>特别提醒: 🌻在您使用测试案例之前请认真的阅读官网.</h3>")
                        .version(SnailJobVersion.getVersion())
                        .license(new License().name("Apache 2.0").url("https://snailjob.opensnail.com/")))
                .externalDocs(new ExternalDocumentation()
                        .description("视频教程:以小白视角的SnailJob入门级视频教程")
                        .url("https://www.bilibili.com/video/BV1pvtBerEmV/?vd_source=ec323e2347232ea82321f54aba036b63"))
                ;
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                //分组名
                .group("user")
                .pathsToMatch("/**")
                //扫描路径，将路径下有swagger注解的接口解析到文档中
                .packagesToScan("com.example.controller")
                .build();
    }
}






