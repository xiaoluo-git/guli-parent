package com.guli.teacher.service.feign;

import com.guli.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@FeignClient("GULI-VOD")
@Service
public interface VodServiceFeign {

	@DeleteMapping("/vod/{id}")
	public Result deleteById(@PathVariable("id") String id);

	@DeleteMapping("/vod/deleteBatchByIds")
	public Result deleteBatchByIds( @RequestParam("videoSourceIds") List<String> videoSourceIds);

}
