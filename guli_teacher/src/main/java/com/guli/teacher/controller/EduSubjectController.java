package com.guli.teacher.controller;


import com.guli.common.Result;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author guli
 * @since 2019-08-20
 */
@RestController
@RequestMapping("/subject")
@CrossOrigin
@Api(tags="课程分类管理")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    @PostMapping("import")
    @ApiOperation("批量课程导入")
    public Result importFile(
            @ApiParam(name="file",value="导入文件",required = true)
            MultipartFile file
    ){
        List<String> list = subjectService.importFile(file);
        if(list == null || list.size() == 0){
            return Result.ok();
        }
        return Result.error().data("list",list);
    }


    @GetMapping("tree")
    @ApiOperation("获取树形列表")
    public Result getTree(){
        List<OneSubject> list = subjectService.getTree();
        if(list.size() == 0){
            return Result.error().message("请添加数据");
        }
        return Result.ok().data("list",list);
    }

    @DeleteMapping("{id}")
    @ApiOperation("根据id删除节点")
    public Result deleteSubjectById(@PathVariable("id") String id){
        boolean b = subjectService.removeSubject(id);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("save")
    @ApiOperation("保存课程列表")
    public Result save(@RequestBody EduSubject subject){
        boolean flag = subjectService.saveSubject(subject);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }

    @PutMapping("update")
    @ApiOperation("修改课程列表分类信息")
    public Result update(@RequestBody EduSubject subject){
        boolean flag = subjectService.updateSubject(subject);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }
}

