package com.bjpowernode;

import com.bjpowernode.commons.BaseApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.bjpowernode.dao")
@SpringBootApplication
public class DlykApplication extends BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DlykApplication.class, args);
    }
}