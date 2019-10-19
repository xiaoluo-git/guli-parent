package com.guigu.vod.sdk;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

public class Main {
	//账号AccessKey信息请填写(必选)
	private static String access_key_id = "LTAIhiyF1bmbVDok";
	//账号AccessKey信息请填写(必选)
	private static String access_key_secret = "y2v0iHgOet2NcQVyqpkNQ4J8VYeCht";

	/*获取播放凭证函数*/
	public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client) throws Exception {
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setVideoId("4ec4f0e497724a0ba6a7f5800a118c31");
		return client.getAcsResponse(request);
	}
	/*以下为调用示例*/
	public static void main(String[] argv)  {

		GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

		try {
			DefaultAcsClient client = ClientInit.initVodClient(access_key_id, access_key_secret);

			response = getVideoPlayAuth(client);
			//播放凭证
			System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
			//VideoMeta信息
			System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}
		System.out.print("RequestId = " + response.getRequestId() + "\n");
	}
}
