package com.guli.vod.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean{

	public static  String access_key_id;

	public static String access_key_secret;

	@Value("${aliyun.vod.file.keyid}")
	private String accessKeyId;

	@Value("${aliyun.vod.file.keysecret}")
	private String accessKeySecret;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.access_key_id = accessKeyId;
		this.access_key_secret = accessKeySecret;
	}
}
