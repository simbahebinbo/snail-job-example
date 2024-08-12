package com.example.snailjob;

import com.aizuda.snailjob.client.starter.EnableSnailJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@EnableSnailJob
@MapperScan("com.example.snailjob.dao")
public class SnailJobSpringbootApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(SnailJobSpringbootApplication.class, args);
    }

}
