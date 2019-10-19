package com.guli.teacher.entity.vo;

import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduCourseDescription;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("封装课程基本信息的类")
public class CourseDesc implements Serializable {

    @ApiModelProperty("课程信息")
    private EduCourse course;

    @ApiModelProperty("课程描述")
    private EduCourseDescription courseDescription;
}
