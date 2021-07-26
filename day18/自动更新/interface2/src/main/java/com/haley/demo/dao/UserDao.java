package com.haley.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.haley.demo.entity.ApkEntity;
import com.haley.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: demo
 * @description: 用户dao
 * @author: haley
 * @create: 2021-07-09 19:07
 **/
@Mapper
public interface UserDao {
    /**
     * 保存新客户信息
     * @param userEntity
     * @return
     */
    int save(UserEntity userEntity);

    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    UserEntity queryById(int id);

    /**
     * 根据phone查找用户信息
     * @param phone
     * @return
     */
    UserEntity queryByPhone(String phone);

    void saveAPK(ApkEntity maps);

    ApkEntity queryAPK();
}
