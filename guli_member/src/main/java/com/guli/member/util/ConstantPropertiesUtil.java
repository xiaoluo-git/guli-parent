package com.guli.member.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean{
	@Value("${wx.open.app_id}")
	private String appId;

	@Value("${wx.open.app_secret}")
	private String appSecret;

	@Value("${wx.open.redirect_url}")
	private  String redirectUrl;

	public static  String APP_ID;

	public static  String APP_SECRET;

	public static  String REDIRECT_URL;
	@Override
	public void afterPropertiesSet() throws Exception {
		APP_ID = appId;
		APP_SECRET = appSecret;
		REDIRECT_URL = redirectUrl;
	}
}
