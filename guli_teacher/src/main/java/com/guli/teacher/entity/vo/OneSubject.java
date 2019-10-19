package com.guli.teacher.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel("一级课程列表封装类")
@Data
public class OneSubject {

    @ApiModelProperty("一级列表ID")
    private String id;

    @ApiModelProperty("一级列表标题")
    private String title;

    @ApiModelProperty("一级列表子节点的集合")
    private List<TwoSubject> children = new ArrayList<>();
}
