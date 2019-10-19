package com.guli.oos.controller;

import com.guli.common.Result;
import com.guli.oos.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/oss")
@Api("Oss接口")
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("file/upload")
    @ApiOperation("文件上传")
    public Result upload(
            @ApiParam(name="file",value="上传文件",required = true)
            MultipartFile file){
        String path = ossService.upload(file);
        return Result.ok().data("path",path);

    }
}
