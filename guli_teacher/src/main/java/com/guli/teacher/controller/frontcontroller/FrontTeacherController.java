package com.guli.teacher.controller.frontcontroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Result;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/frontTeacher")
@CrossOrigin
public class FrontTeacherController {

	@Autowired
	private EduTeacherService teacherService;

	@GetMapping("listAllTeachersByPage/{page}/{limit}")
	public Result listAllTeachersByPage(@PathVariable Long page,@PathVariable Long limit){
		Page<EduTeacher> teacherPage = new Page<>(page, limit);
		Map<String,Object> map = teacherService.listAllTeachersByPage(teacherPage);

		return Result.ok().data(map);

	}

	@GetMapping("getTeacherInfoById/{id}")
	public Result getTeacherInfoById(@PathVariable String id){
		Map<String,Object> map = teacherService.getTeacherInfoById(id);
		return Result.ok().data(map);
	}
}
