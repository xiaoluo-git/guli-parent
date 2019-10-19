package com.guli.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Exception.TeacherException;
import com.guli.common.Result;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.service.EduTeacherService;
import com.guli.teacher.vo.InsertTeacherVo;
import com.guli.teacher.vo.TeacherVo;
import com.guli.teacher.vo.UpdateTeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author guli
 * @since 2019-08-14
 */
@Api(tags="123",value="讲师接口",description = "讲师管理")
@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("list")
    @ApiOperation("获取所有讲师列表")
    public Result listTeacher(){
//        try {
//            int a = 1/0;
//        } catch (Exception e) {
//            throw new TeacherException(20001,"你不知道0不能做除数啊？憨熊！");
//        }
        List<EduTeacher> list = teacherService.list(null);
        return Result.ok().data("list",list);
    }

    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("{id}")
    public Result deleteTeacherById(
            @ApiParam(name="id",value="讲师ID",required = true)
            @PathVariable String id
    ){
        boolean b = teacherService.removeById(id);
        if(b){
            return Result.ok();
        }

        return Result.error();

    }

    @ApiOperation("教师列表的分页查询")
    @GetMapping("/{pageNo}/{pageSize}")
    public Result listTeacherByPage(
            @ApiParam(name="pageNo",value="当前页",required = true)
            @PathVariable String pageNo,
            @ApiParam(name="pageSize",value = "每页显示的条数",required = true)
            @PathVariable String pageSize
    ){
        Page<EduTeacher> page = new Page<EduTeacher>(Long.parseLong(pageNo),Long.parseLong(pageSize));
        teacherService.page(page,null);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok().data("total",total).data("rows",records);
    }

    @ApiOperation("根据条件分页查询教师列表")
    @PostMapping("/{pageNo}/{pageSize}")
    public Result listTeacherByPageAndWrapper(
            @ApiParam(name="pageNo",value="当前页",required = true)
            @PathVariable long pageNo,
            @ApiParam(name="pageSize",value = "每页显示的条数",required = true)
            @PathVariable long pageSize,
            @ApiParam(name="condition",value = "查询条件")
            @RequestBody TeacherVo condition
    ){
        Page<EduTeacher> page = new Page<>(pageNo,pageSize);
        teacherService.pageCondition(page,condition);

        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return Result.ok().data("total",total).data("rows",records);

    }

    @ApiOperation("新增讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(
            @ApiParam(name="insertTeacherVo",value="新增的讲师",required = true)
            @RequestBody InsertTeacherVo insertTeacherVo
    ){
        EduTeacher eduTeacher = new EduTeacher();
        BeanUtils.copyProperties(insertTeacherVo,eduTeacher);
        boolean b = teacherService.save(eduTeacher);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

    @ApiOperation("根据Id查询讲师")
    @GetMapping("{id}")
    public Result getTeacherById(
            @ApiParam(name="id", value="讲师Id", required = true)
            @PathVariable String id
    ){
        EduTeacher teacher = teacherService.getById(id);
        if(teacher == null){
            new TeacherException(ResultCode.TEACHERID_NOT_EXISTS,"用户Id不存在");
        }
        return Result.ok().data("teacher",teacher);

    }

    @ApiOperation("根据id更新用户信息")
    @PutMapping("updateTeacherbyId")
    public Result updateTeacherById(
            @ApiParam(name="updateTeacherVo",value="更新讲师的Vo",required = true)
            @RequestBody UpdateTeacherVo updateTeacherVo

    ){
       EduTeacher teacher = new EduTeacher();
        BeanUtils.copyProperties(updateTeacherVo,teacher);
        boolean b = teacherService.updateById(teacher);
        if(b){
            return Result.ok();
        }
        return Result.error();
    }

}

