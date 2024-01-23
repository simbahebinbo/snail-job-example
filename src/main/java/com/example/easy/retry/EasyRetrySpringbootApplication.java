package com.example.easy.retry;

import com.aizuda.easy.retry.client.starter.EnableEasyRetry;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@EnableEasyRetry(group = "easy_retry_demo_group")
@MapperScan("com.example.easy.retry.dao")
public class EasyRetrySpringbootApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(EasyRetrySpringbootApplication.class, args);
    }

}
