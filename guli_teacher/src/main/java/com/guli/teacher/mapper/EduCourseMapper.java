package com.guli.teacher.mapper;

import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.teacher.entity.vo.PublishVo;
import com.guli.teacher.vo.CourseInfoVo;

import java.util.Map;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    PublishVo getPublishVoByCourseId(String courseId);

	Map<String,Object> getPublishVo(String courseId);

	CourseInfoVo getCourseInfo(String id);

}
