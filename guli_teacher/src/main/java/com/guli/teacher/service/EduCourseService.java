package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Result;
import com.guli.teacher.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.CourseConditionVo;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.PublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
public interface EduCourseService extends IService<EduCourse> {

    String  saveCourse(CourseDesc courseDesc);

    CourseDesc getCourseDescById(String id);

    boolean updateCourse(CourseDesc courseDesc);

    void getPageByCondition(Page<EduCourse> pageInfo, CourseConditionVo courseConditionVo);

    boolean removeCourseById(String id);

    PublishVo getPublishVoByCourseId(String courseId);

    Map<String,Object> getPublishVo(String courseId);

	Map<String,Object> listCourseByPage(Page<EduCourse> coursePage);

	Result getCourseInfo(String id);
}
