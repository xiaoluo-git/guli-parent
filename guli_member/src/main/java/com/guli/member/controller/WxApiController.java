package com.guli.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.guli.common.Result;
import com.guli.member.entity.UcenterMember;
import com.guli.member.service.UcenterMemberService;
import com.guli.member.util.ConstantPropertiesUtil;
import com.guli.member.util.HttpClientUtils;
import com.guli.member.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginContext;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@CrossOrigin
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
	@Autowired
	private UcenterMemberService memberService;

	@GetMapping("getCode")
	public String getCode(){
		try {
			String url = "https://open.weixin.qq.com/connect/qrconnect?" +
					"appid=%s" +
					"&redirect_uri=%s" +
					"&response_type=code" +
					"&scope=snsapi_login" +
					"&state=%s" +
					"#wechat_redirect";

			String appId = ConstantPropertiesUtil.APP_ID;
			String redirectUrl = ConstantPropertiesUtil.REDIRECT_URL;
			redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
			String state = "xl";

			url = String.format(url,appId,redirectUrl,state);
			return "redirect:" + url;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("发生错误");
		}
	}

	@GetMapping("callback")
	public String callback(String code){

		String url = "https://api.weixin.qq.com/sns/oauth2/access_token" +
				"?appid=%s" +
				"&secret=%s" +
				"&code=%s" +
				"&grant_type=authorization_code";

		String appId = ConstantPropertiesUtil.APP_ID;
		String appSecret = ConstantPropertiesUtil.APP_SECRET;

		url = String.format(url,appId,appSecret,code);
		String s = "";
		try {
			s = HttpClientUtils.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		HashMap<String, Object> map = gson.fromJson(s, HashMap.class);
		Object openid = map.get("openid");
		Object access_token = map.get("access_token");

		QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
		wrapper.eq("openid",openid);
		UcenterMember member = memberService.getOne(wrapper);

		if(member == null){
			url = "https://api.weixin.qq.com/sns/userinfo" +
					"?openid=%s" +
					"&access_token=%s";

			url = String.format(url,openid,access_token);
			String userInfo = "";
			try {
				userInfo = HttpClientUtils.get(url);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(userInfo);
			HashMap<String,Object> hashMap = gson.fromJson(userInfo, HashMap.class);

			//{"openid":"o1R-t5irnmvDVHyfKvGVPE6d8BMs","nickname":"小落","sex":1,"language":"zh_CN","city":"Datong","province":"Shanxi","country":"CN","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTKsebzbb4ibuVr8D21csIAuJLpvicicgoY55IXN0JYQGHcVib9F98WH7JeMEtRJpCTCloQXUiaoxMxUmBQ\/132","privilege":[],"unionid":"oWgGz1AphkYW-chFQOWPalRMhf98"}
			String openId = (String) hashMap.get("openid");
			String nickname = (String) hashMap.get("nickname");
			String headimgurl = (String) hashMap.get("headimgurl");

			member = new UcenterMember();
			member.setOpenid(openId);
			member.setNickname(nickname);
			member.setAvatar(headimgurl);

			memberService.save(member);
		}

		String token = JwtUtils.geneJsonWebToken(member);


		return "redirect:http://localhost:3000?token=" + token;
	}

	@PostMapping("parseToken/{token}")
	@ResponseBody
	public Result parseToken(@PathVariable String token){
		Claims claims = JwtUtils.checkJWT(token);
		UcenterMember member = new UcenterMember();
		member.setOpenid((String) claims.get("id"));
		member.setNickname((String) claims.get("nickname"));
		member.setAvatar((String) claims.get("avatar"));
		return Result.ok().data("member",member);
	}
}
