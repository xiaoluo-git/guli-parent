package com.guigu.vod.sdk;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;

import java.util.List;

public class SdkGetPlayInfo {
	//账号AccessKey信息请填写(必选)
	private static String access_key_id = "LTAIhiyF1bmbVDok";
	//账号AccessKey信息请填写(必选)
	private static String access_key_secret = "y2v0iHgOet2NcQVyqpkNQ4J8VYeCht";

	/*获取播放地址函数*/
	public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client) throws Exception {
		GetPlayInfoRequest request = new GetPlayInfoRequest();
		request.setVideoId("4ec4f0e497724a0ba6a7f5800a118c31");
		request.setResultType("Multiple");
		return client.getAcsResponse(request);
	}
	/*以下为调用示例*/
	public static void main(String[] argv) {

		GetPlayInfoResponse response = new GetPlayInfoResponse();
		try {
			DefaultAcsClient client = ClientInit.initVodClient(access_key_id,access_key_secret);
			response = getPlayInfo(client);
			List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
			//播放地址
			for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
				System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
			}
			//Base信息
			System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
		} catch (Exception e) {
			System.out.print("ErrorMessage = " + e.getLocalizedMessage());
		}
		System.out.print("RequestId = " + response.getRequestId() + "\n");
	}
}
