package com.example.day12.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.day12.entity.Examinee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamineeDao extends BaseMapper<Examinee> {
}
