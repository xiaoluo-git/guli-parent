package com.guli.vod.controller;

import com.guli.common.Result;
import com.guli.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/vod")
public class VodController {
	@Autowired
	private VodService vodService;

	@PostMapping("upload")
	public Result uploadVod(MultipartFile file){
		String vodId = vodService.uploadVod(file);
		return Result.ok().data("vodId",vodId);
	}

	@DeleteMapping("{id}")
	public Result deleteById(@PathVariable String id){
		boolean flag = vodService.deleteById(id);
		if(!flag){
			return Result.error();
		}
		return  Result.ok();
	}

	@DeleteMapping("/deleteBatchByIds")
	public Result deleteBatchByIds( @RequestParam("videoSourceIds") List<String> videoSourceIds){
		boolean flag = vodService.deleteBatchByIds(videoSourceIds);
		if(!flag){
			return Result.error();
		}
		return  Result.ok();
	}

	@GetMapping("getVideoPlayAuth/{id}")
	public Result getVideoPlayAuth(@PathVariable String id){
		String playAuth = vodService.getVideoPlayAuth(id);
		return Result.ok().data("playAuth",playAuth);
	}
}
