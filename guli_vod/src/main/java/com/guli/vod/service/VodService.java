package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
	String uploadVod(MultipartFile file);

	boolean deleteById(String id);

	boolean deleteBatchByIds(List<String> videoSourceIds);

	String getVideoPlayAuth(String id);
}
