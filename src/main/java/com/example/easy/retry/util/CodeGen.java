package com.example.easy.retry.util;

import org.springframework.beans.factory.annotation.Value;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class CodeGen {

    private static String dataSourceUrl = "jdbc:mysql://localhost:3306/easy_retry_260?useSSL=false&characterEncoding=utf8&useUnicode=true";

    private static String userName = "root";

    private static String password= "root";

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(dataSourceUrl, userName, password).build();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .outputDir("src/main/java")
                .author("xiaowoniu")
                .build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .addInclude("workflow")  // 需要生成的表名
                .build();

        // 包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("com.aizuda.easy.retry.template.datasource.persistence.po")
                .moduleName("easy-retry-springboot")
                .build();

        // 代码生成器
        AutoGenerator generator = new AutoGenerator(dataSourceConfig)
                .global(globalConfig)
                .strategy(strategyConfig)
                .packageInfo(packageConfig);

        // 执行生成代码
        generator.execute();

    }
}
