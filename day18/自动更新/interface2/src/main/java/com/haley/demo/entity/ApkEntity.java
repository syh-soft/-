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
@TableName("apk_Info")
public class ApkEntity {

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户姓名
     */
    private String filename;
    /**
     * 用户密码
     */
    private String pkgname;
    /**
     * 用户性别
     */
    private String versioncode;

    private String versionname;
}
