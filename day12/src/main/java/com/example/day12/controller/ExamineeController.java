package com.example.day12.controller;

import com.example.day12.dto.ExamineeService;
import com.example.day12.entity.Examinee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/examinee")
@Api(tags = "考生管理")
public class ExamineeController {
    @Autowired
    private ExamineeService examineeService;

    @GetMapping("/add")
    public void addExaminee(Examinee examinee){
        examineeService.add(examinee);
    }

    @GetMapping("/delete")
    public void deleteExaminee(String id){
        examineeService.deleteByid(id);
    }

    @GetMapping("/update")
    public void updateExaminee(Examinee examinee){
        examineeService.updateExamine(examinee);
    }

    @GetMapping("/query")
    public Examinee queryById(String id){
        return examineeService.query(id);
    }





}
