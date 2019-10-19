package com.guli.statistics.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.guli.statistics.mapper")
@EnableTransactionManagement
@EnableEurekaClient
public class statisticsConfig {
}
