package com.jenkins.own;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.jenkins.own.moudle.*.dao")
@SpringBootApplication
@EnableCaching
public class OwnJenkinsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnJenkinsApplication.class, args);
    }

}
