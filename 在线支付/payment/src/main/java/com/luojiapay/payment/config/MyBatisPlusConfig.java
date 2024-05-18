package com.luojiapay.payment.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement // 启用事务管理
@MapperScan("com.luojiapay.payment.mapper")
public class MyBatisPlusConfig {
}
