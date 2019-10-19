package com.guli.statistics.service.feign;

import com.guli.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("GULI-MEMBER")
@Service
public interface MemberServiceFeign {

	@GetMapping("/member/getRegisterNum/{day}")
	public Result getRegisterNum(@PathVariable("day") String day);
}
