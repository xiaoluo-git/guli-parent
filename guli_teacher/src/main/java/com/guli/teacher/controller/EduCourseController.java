package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.vo.CourseConditionVo;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.PublishVo;
import com.guli.teacher.service.EduCourseService;
import com.sun.deploy.net.HttpResponse;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
@RestController
@RequestMapping("/course")
@CrossOrigin
@Api(tags="课程管理")
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    @PostMapping("/{page}/{limit}")
    public Result getPageByCondition(
            @PathVariable("page") Integer page,
            @PathVariable("limit") Integer limit,
            @RequestBody CourseConditionVo courseConditionVo){
        Page<EduCourse> pageInfo = new Page<>(page,limit);
        courseService.getPageByCondition(pageInfo,courseConditionVo);
        return Result.ok().data("courseList",pageInfo.getRecords()).data("total",pageInfo.getTotal());
    }


    @PostMapping("save")
    @ApiOperation("保存基本信息")
    public Result save(
            @ApiParam(name="courseDesc",value="课程基本信息封装",required = true)
          @RequestBody CourseDesc courseDesc){
        String courseId = courseService.saveCourse(courseDesc);
        if(courseId == null){
            return Result.error();
        }
        return Result.ok().data("courseId",courseId);
    }

    @GetMapping("{id}")
    @ApiOperation("根据ID查询课程")
    public Result getCourse(@PathVariable String id, HttpServletResponse response){
                response.setHeader("access-Control-Allow-Origin","*");

        CourseDesc courseDesc = courseService.getCourseDescById(id);
        if(courseDesc == null){
            return Result.error();
        }
        return Result.ok().data("courseInfo",courseDesc);
    }

    @PutMapping("update")
    public Result update(@RequestBody CourseDesc courseDesc){
       boolean flag =  courseService.updateCourse(courseDesc);
       if(!flag){
           return Result.error();
       }
       return Result.ok();
    }

    @DeleteMapping("{id}")
    public Result removeById(@PathVariable("id") String id){
        boolean flag = courseService.removeCourseById(id);
        if(!flag){
            return Result.error();
        }
        return Result.ok();
    }

    @GetMapping("publish/{courseId}")
    public Result getPublishVo(@PathVariable("courseId") String courseId){
//        PublishVo publishVo = courseService.getPublishVoByCourseId(courseId);
//		if(publishVo == null){
//			return Result.error();
//		}
//
//		return Result.ok().data("publishVo",publishVo);
        Map<String,Object> map = courseService.getPublishVo(courseId);

        if(map == null){
            return Result.error();
        }

        return Result.ok().data(map);
    }

    @PutMapping("updatesStatus/{id}")
	public Result updateStatus(@PathVariable String id){
		EduCourse eduCourse = new EduCourse();
		eduCourse.setId(id);
		eduCourse.setStatus("Normal");
		boolean flag = courseService.updateById(eduCourse);
		if(!flag){
			return Result.error();
		}
		return Result.ok();
	}



}

