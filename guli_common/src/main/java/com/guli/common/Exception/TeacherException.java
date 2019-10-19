package com.guli.common.Exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor

@ToString
@ApiModel("全局异常")
public class TeacherException extends RuntimeException {
    @ApiModelProperty("异常代码")
    private int code;
    @ApiModelProperty("异常信息")
    private String message;
}
