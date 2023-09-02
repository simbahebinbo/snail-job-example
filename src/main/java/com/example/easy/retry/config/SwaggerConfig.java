package com.example.easy.retry.config;

import com.aizuda.easy.retry.common.core.util.EasyRetryVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: www.byteblogs.com
 * @date : 2023-07-17 18:19
 * @since 2.1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.easy.retry.controller")) // 替换为你的项目包名
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Easy Retry Example")
            .description(
                    "<h1>EasyRetry是基于BASE思想实现的分布式服务重试组件</h1> \n" +
                    "<h3>官网地址: https://www.easyretry.com/</h3>" +
                    "<h3>在线体验地址: http://preview.easyretry.com/</h3> "+
                    "<h3>源码地址: https://gitee.com/byteblogs168/easy-retry-demo</h3>" +
                    "<h3>特别提醒: 🌻在您使用测试案例之前请认真的阅读官网.</h3>"
            )
            .version(EasyRetryVersion.getVersion())
            .build();
    }
}






