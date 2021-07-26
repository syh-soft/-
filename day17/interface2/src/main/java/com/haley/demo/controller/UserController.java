package com.haley.demo.controller;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.haley.demo.entity.UserEntity;
import com.haley.demo.service.UserService;
import com.haley.demo.utils.Result;
import com.haley.demo.utils.SendUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @program: demo
 * @description: 用户控制层
 * @author: haley
 * @create: 2021-07-09 19:14
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param id
     * @return 用户信息
     */
    @GetMapping("/queryById")
    public UserEntity queryById(int id){
        //PushResult result = SendUtils.testSendPush();
        return userService.queryById(id);
    }

    @GetMapping("/queryByPhone")
    public UserEntity queryByPhone(String phone){
        return userService.queryByPhone(phone);
    }

    @PostMapping("/saveOne")
    public Result saveone(@RequestBody UserEntity userEntity){
        userService.save(userEntity);
        return Result.ok("保存成功");
    }


    @PostMapping("/sendMessage")
    public Result sendMessage(@RequestBody UserEntity userEntity){
        PushResult result = SendUtils.testSendPush(userEntity.getName(),userEntity.getPassword(), userEntity.getPhone());
        return Result.ok("保存成功");
    }

}
