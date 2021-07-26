package com.haley.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: demo
 * @description: 用户实体类
 * @author: haley
 * @create: 2021-07-09 19:07
 **/
@Data
@Accessors(chain = true)
@TableName("user")
public class UserEntity {

    /**
     * 用户id
     */
    private int id;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户性别
     */
    private String sex;
}
