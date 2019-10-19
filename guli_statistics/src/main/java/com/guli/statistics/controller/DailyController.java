package com.guli.statistics.controller;


import com.guli.common.Result;
import com.guli.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author xiaoluo
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/statistics")
@CrossOrigin
public class DailyController {
	@Autowired
	private DailyService dailyService;

	@GetMapping("createData/{day}")
	public Result createData(@PathVariable String day){
		boolean flag = dailyService.createData(day);
		if(!flag){
			return Result.error();
		}
		return Result.ok();
	}

	@PostMapping("showData/{begin}/{end}/{type}")
	public Result showData(@PathVariable String begin,
						   @PathVariable String end,
						   @PathVariable String type){
		Map<String,Object> map = dailyService.showData(begin,end,type);
		return Result.ok().data(map);

	}

}

