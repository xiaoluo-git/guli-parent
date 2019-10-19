package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.Exception.TeacherException;
import com.guli.common.Result;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.mapper.EduVideoMapper;
import com.guli.teacher.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.feign.VodServiceFeign;
import org.apache.poi.hssf.record.IterationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-24
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

	@Autowired
	private VodServiceFeign vodServiceFeign;

    @Override
    public boolean deleteVideoById(String id) {
        //TODO 删除阿里云上视频
		EduVideo eduVideo = baseMapper.selectById(id);
		if(eduVideo == null){
			return false;
		}
		String videoSourceId = eduVideo.getVideoSourceId();
		if(StringUtils.isEmpty(videoSourceId)){
			return false;
		}

		vodServiceFeign.deleteById(videoSourceId);

		int count = baseMapper.deleteById(id);
        return count == 1;
    }

    @Override
    public boolean saveVideo(EduVideo video) {
        if(video == null){
            return false;
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",video.getCourseId());
        wrapper.eq("chapter_id",video.getChapterId());
        wrapper.eq("title",video.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if(count == 1){
            throw new TeacherException(ResultCode.EXISTS_VIDEO,"此小节存在");
        }
        return baseMapper.insert(video) == 1;
    }

	@Override
	public void deleteBatchVideoByIds(ArrayList<String> videoIds) {
		ArrayList<String> videoSourceIds = new ArrayList<>();
		List<EduVideo> videoList = baseMapper.selectBatchIds(videoIds);
		for (EduVideo eduVideo : videoList) {
			String videoSourceId = eduVideo.getVideoSourceId();
			if(!StringUtils.isEmpty(videoSourceId)){
				videoSourceIds.add(videoSourceId);
			}
		}

		vodServiceFeign.deleteBatchByIds(videoSourceIds);

		baseMapper.deleteBatchIds(videoIds);

	}
}
