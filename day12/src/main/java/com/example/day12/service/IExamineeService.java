package com.example.day12.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.day12.dao.ExamineeDao;
import com.example.day12.dto.ExamineeService;
import com.example.day12.entity.Examinee;
import org.springframework.stereotype.Service;

@Service
public class IExamineeService extends ServiceImpl<ExamineeDao, Examinee> implements ExamineeService {
    @Override
    public void add(Examinee examinee) {
        this.save(examinee);
    }

    @Override
    public void deleteByid(String id) {
        this.removeById(id);
    }

    @Override
    public void updateExamine(Examinee examinee) {
        this.updateById(examinee);
    }

    @Override
    public Examinee query(String id) {
        return this.getById(id);
    }
}
