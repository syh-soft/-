package com.haley.demo.dao;

import com.haley.demo.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Test
    void save() {
        UserEntity user = new UserEntity();
        user.setName("赵龙昊1");
        user.setPhone("111");
        user.setPassword("111");
        user.setSex("男");
        int a = userDao.save(user);
        assertEquals(1,a);
    }

    @Test
    void getOne(){
        String phone = "10086";
        UserEntity a = userDao.queryByPhone(phone);
        System.out.println(a);
        assertNotNull(a);
    }
}