package com.example;

import com.aizuda.snailjob.client.starter.EnableSnailJob;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
@EnableSnailJob
@MapperScan("com.example.dao")
public class ExampleApplication {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(ExampleApplication.class, args);
    }
}
