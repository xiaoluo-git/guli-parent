package com.guli.teacher.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("二级课程列表封装对象")
@Data
public class TwoSubject implements Serializable{
    @ApiModelProperty("二级课程列表Id")
    private String id;
    @ApiModelProperty("二级课程列表标题")
    private String title;
}
