package com.guli.teacher.service;

import com.guli.teacher.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author guli
 * @since 2019-08-24
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean deleteVideoById(String id);

    boolean saveVideo(EduVideo video);

	void deleteBatchVideoByIds(ArrayList<String> videoIds);
}
