package com.mushan.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: mushan
 * @url: www.mushan.com
 * @description: Mybatis Plus 配置文件
 **/
@Configuration
@MapperScan("com.mushan.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}
