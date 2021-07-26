package com.example.day12;

import io.swagger.annotations.SwaggerDefinition;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan(value = {"com.example.day12.dao"})
public class Day12Application {

    public static void main(String[] args) {
        SpringApplication.run(Day12Application.class, args);
    }

}
