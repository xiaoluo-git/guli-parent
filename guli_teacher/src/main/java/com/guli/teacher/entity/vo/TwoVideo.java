package com.guli.teacher.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class TwoVideo {

    private String id;

    private String title;

    private String videoSourceId;

    private String videoOriginalName;

    @TableField(value = "is_free")
    private boolean free;
}
