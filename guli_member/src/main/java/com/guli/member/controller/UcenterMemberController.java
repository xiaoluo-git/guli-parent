package com.guli.member.controller;


import com.guli.common.Result;
import com.guli.member.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/member")
@CrossOrigin
public class UcenterMemberController {

	@Autowired
	private UcenterMemberService memberService;

	@GetMapping("getRegisterNum/{day}")
	public Result getRegisterNum(@PathVariable String day){

		Integer count = memberService.countRegisterNum(day);
		return Result.ok().data("count",count);
	}

}

