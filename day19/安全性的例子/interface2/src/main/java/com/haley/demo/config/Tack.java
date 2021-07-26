package com.haley.demo.config;

import com.haley.demo.utils.Token;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class Tack {

    @Scheduled(cron = "0/60 * * * * ?")
    public void test(){
        Token.token += 1;
        System.out.println(Token.token);
    }
}
