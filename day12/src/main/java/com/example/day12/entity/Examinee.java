package com.example.day12.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@ApiModel
@TableName("examinee")
@Accessors(chain = true)
public class Examinee {
    @ApiModelProperty(notes = "考生id")
    private String id;

    @ApiModelProperty(notes = "考生学号")
    private String studentNumber;

    @ApiModelProperty(notes = "考生姓名")
    private String name;

    @ApiModelProperty(notes = "考生性别")
    private int sex;


}
