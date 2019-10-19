package com.guli.teacher.controller;


import com.guli.common.Exception.TeacherException;
import com.guli.common.Result;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
@RestController
@RequestMapping("/chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @GetMapping("list/{courseId}")
    public Result getChapterList(@PathVariable("courseId") String courseId){
        List<OneChapter> list = chapterService.getChapterList(courseId);
        if(list == null){
            throw new TeacherException(ResultCode.CHAPTER_NOT_EXISTS,"还没有章节数据");
        }
        return Result.ok().data("chapterList",list);
    }

    @PostMapping("save/{courseId}")
    public Result saveChapter(@RequestBody EduChapter eduChapter, @PathVariable("courseId") String courseId){
        boolean flag = chapterService.saveChapter(eduChapter,courseId);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }

    @GetMapping("{id}")
    public Result getChapterById(@PathVariable("id") String id){
        EduChapter chapter = chapterService.getById(id);
        if(chapter == null ){
            return Result.error();
        }
        return Result.ok().data("chapter",chapter);
    }

    @PutMapping("update")
    public Result updateChapter(@RequestBody EduChapter chapter){
        boolean flag = chapterService.updateChapter(chapter);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") String id){
        boolean flag = chapterService.romoveChapterById(id);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }


}

