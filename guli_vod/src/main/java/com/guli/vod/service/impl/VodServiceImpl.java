package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.common.Exception.TeacherException;
import com.guli.vod.service.VodService;
import com.guli.vod.util.AliyunVODSDKUtils;
import com.guli.vod.util.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
public class VodServiceImpl implements VodService {
	@Override
	public String uploadVod(MultipartFile file) {


		try {
			InputStream inputStream = file.getInputStream();
			String originalFilename = file.getOriginalFilename();
			String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

			UploadStreamRequest request = new UploadStreamRequest(
					ConstantPropertiesUtil.access_key_id,
					ConstantPropertiesUtil.access_key_secret,
					title, originalFilename, inputStream);

			UploadVideoImpl uploader = new UploadVideoImpl();
			UploadStreamResponse response = uploader.uploadStream(request);

			//如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
			// 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
			String videoId = response.getVideoId();
			if (!response.isSuccess()) {
				String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
				log.warn(errorMessage);
				if(StringUtils.isEmpty(videoId)){
					throw new TeacherException(20001, errorMessage);
				}
			}

			return videoId;
		} catch (IOException e) {
			throw new TeacherException(20001, "guli vod 服务上传失败");
		}
	}

	@Override
	public boolean deleteById(String id) {

		try {
			DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(ConstantPropertiesUtil.access_key_id,
					ConstantPropertiesUtil.access_key_secret);
			DeleteVideoRequest request = new DeleteVideoRequest();
			//支持传入多个视频ID，多个用逗号分隔
			request.setVideoIds(id);

			DeleteVideoResponse response  = client.getAcsResponse(request);
			System.out.print("RequestId = " + response.getRequestId() + "\n");
			return true;
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
			throw new TeacherException(20001,e.getLocalizedMessage());
		}

	}

	@Override
	public boolean deleteBatchByIds(List<String> videoSourceIds) {
		try {
			DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(ConstantPropertiesUtil.access_key_id, ConstantPropertiesUtil.access_key_secret);
			DeleteVideoRequest request = new DeleteVideoRequest();
			request.setVideoIds(org.apache.commons.lang.StringUtils.join(videoSourceIds,","));
			DeleteVideoResponse response = client.getAcsResponse(request);
			System.out.print("RequestId = " + response.getRequestId() + "\n");
			return true;
		} catch (ClientException e) {
			throw new TeacherException(20001,e.getLocalizedMessage());
		}
	}

	@Override
	public String getVideoPlayAuth(String id) {
		try {
			DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(ConstantPropertiesUtil.access_key_id, ConstantPropertiesUtil.access_key_secret);
			GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
			request.setVideoId(id);
			GetVideoPlayAuthResponse response = client.getAcsResponse(request);
			return response.getPlayAuth();
		} catch (ClientException e) {
			e.printStackTrace();
			return null;
		}

	}

}
