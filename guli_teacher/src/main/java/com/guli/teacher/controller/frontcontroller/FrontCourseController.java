package com.guli.teacher.controller.frontcontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Result;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/frontCourse")
@CrossOrigin
public class FrontCourseController {
	@Autowired
	private EduCourseService courseService;

	@GetMapping("listCourseByPage/{page}/{limit}")
	public Result listCourseByPage(@PathVariable Integer page,@PathVariable Integer limit){
		Page<EduCourse> coursePage = new Page<>(page, limit);
		Map<String,Object> map = courseService.listCourseByPage(coursePage);
		return Result.ok().data(map);

	}

	@GetMapping("/getCourseInfo/{id}")
	public Result getCourseInfo(@PathVariable String id){
		Result result = courseService.getCourseInfo(id);
		return result;
	}
}
