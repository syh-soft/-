package com.example.day12.dto;

import com.example.day12.entity.Examinee;

public interface ExamineeService {
    //添加一个考生
    void add(Examinee examinee);

    //删除一个考生
    void deleteByid(String id);

    //更新一个考生
    void updateExamine(Examinee examinee);

    //根据id查询一个考生
    Examinee query(String id);

}
