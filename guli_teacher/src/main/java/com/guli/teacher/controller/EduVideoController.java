package com.guli.teacher.controller;


import com.guli.common.Result;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author guli
 * @since 2019-08-24
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @PostMapping("save")
    public Result save(@RequestBody EduVideo video){
        boolean flag = videoService.saveVideo(video);
        if(!flag){
            return Result.error();
        }

        return Result.ok();
    }

    @GetMapping("{id}")
    public Result getVideoById(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        if(video == null){
            return Result.error();
        }

        return Result.ok().data("video",video);
    }

    @PutMapping("update")
    public Result update(@RequestBody EduVideo video){
        boolean flag = videoService.updateById(video);
        if(!flag){
            return Result.error();
        }

        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result deleteVideoById(@PathVariable String id){
        boolean flag = videoService.deleteVideoById(id);
        if(!flag){
            return Result.error();
        }

        return Result.ok();
    }



}

