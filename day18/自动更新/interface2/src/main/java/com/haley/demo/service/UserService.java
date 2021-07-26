package com.haley.demo.service;

import com.haley.demo.entity.ApkEntity;
import com.haley.demo.entity.UserEntity;

/**
 * @program: demo
 * @description: 用户Service
 * @author: haley
 * @create: 2021-07-09 19:09
 **/
public interface UserService {
    /**
     * @param id
     * @return 用户信息
     * @description: 根据id查找用户信息
     */
    UserEntity queryById(int id);

    /**
     * @description: 保存用户信息
     * @param userEntity
     */
    void save(UserEntity userEntity);

    /**
     * 根据phone查找用户信息
     * @param phone
     * @return
     */
    UserEntity queryByPhone(String phone);

    void saveAPK(ApkEntity maps);

    ApkEntity queryAPK();
}
