package com.guli.teacher.service.impl;



import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.Exception.TeacherException;
import com.guli.common.Result;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.*;
import com.guli.teacher.entity.vo.CourseConditionVo;
import com.guli.teacher.entity.vo.CourseDesc;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.entity.vo.PublishVo;
import com.guli.teacher.mapper.EduCourseMapper;
import com.guli.teacher.service.EduChapterService;
import com.guli.teacher.service.EduCourseDescriptionService;
import com.guli.teacher.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import com.guli.teacher.vo.CourseInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
@Service
@Transactional
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
	@Autowired
	private EduVideoService videoService;
	@Autowired
	private EduChapterService chapterService;

    private String id;

    @Override
    public String saveCourse(CourseDesc courseDesc) {
//        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        wrapper.eq("title",courseDesc.getCourse().getTitle());
//        EduCourse eduCourse = baseMapper.selectOne(wrapper);
//        if(eduCourse != null){
//            throw new TeacherException(ResultCode.COURSE_EXISTS,"课程已存在");
//        }

        int count = baseMapper.insert(courseDesc.getCourse());
        if(count != 1){
            throw new TeacherException(ResultCode.COURSE_INSERT_ERR,"课程插入失败");
        }
        id = courseDesc.getCourse().getId();
        courseDesc.getCourseDescription().setId(id);
        boolean flag = courseDescriptionService.save(courseDesc.getCourseDescription());
        if(!flag){
            throw new TeacherException(ResultCode.COURSEDESC_INSERT_ERR,"课程描述插入失败");
        }
        return id;
    }

    @Override
    public CourseDesc getCourseDescById(String id) {
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new TeacherException(ResultCode.ID_NOT_EXISTS,"ID不存在");
        }
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        if(courseDescription == null){
        	throw new TeacherException(20001,"描述不存在,不能回显");
		}
        CourseDesc courseDesc = new CourseDesc();
        courseDesc.setCourse(eduCourse);
        courseDesc.setCourseDescription(courseDescription);
        return courseDesc;
    }

    @Override
    public boolean updateCourse(CourseDesc courseDesc) {
        int count = baseMapper.updateById(courseDesc.getCourse());
        if(count != 1){
            return false;
        }
        courseDesc.getCourseDescription().setId(courseDesc.getCourse().getId());
        return courseDescriptionService.updateById(courseDesc.getCourseDescription());
    }

    @Override
    public void getPageByCondition(Page<EduCourse> pageInfo, CourseConditionVo courseConditionVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseConditionVo.getTitle();
        String subjectId = courseConditionVo.getSubjectId();
        String subjectParentId = courseConditionVo.getSubjectParentId();
        String teacherId = courseConditionVo.getTeacherId();

        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        baseMapper.selectPage(pageInfo,wrapper);
    }



    @Override
    public boolean removeCourseById(String id) {
        //TODO 删除小节
		ArrayList<String> videoIds = new ArrayList<>();
		QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id",id);
		List<EduVideo> videoList = videoService.list(wrapper);
		if(videoList != null && videoList.size() > 0){
			for (EduVideo eduVideo : videoList) {
				videoIds.add(eduVideo.getId());
			}
		}
		videoService.deleteBatchVideoByIds(videoIds);

		//TODO 删除章节
		ArrayList<String> chapterIds = new ArrayList<>();
		QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
		chapterWrapper.eq("course_id",id);
		List<EduChapter> chapterList = chapterService.list(chapterWrapper);
		for (EduChapter eduChapter : chapterList) {
			chapterIds.add(eduChapter.getId());
		}
		chapterService.removeByIds(chapterIds);


		int count = baseMapper.deleteById(id);
        if(count != 1){
            return false;
        }
        return courseDescriptionService.removeById(id);
    }

    @Override
    public PublishVo getPublishVoByCourseId(String courseId) {
        PublishVo publishVo = baseMapper.getPublishVoByCourseId(courseId);

        return publishVo;
    }

	@Override
	public Map<String, Object> getPublishVo(String courseId) {
		Map<String,Object> map = baseMapper.getPublishVo(courseId);

		return map;
	}

	@Override
	public Map<String, Object> listCourseByPage(Page<EduCourse> coursePage) {
    	baseMapper.selectPage(coursePage,null);

		long current = coursePage.getCurrent();
		long size = coursePage.getSize();
		long total = coursePage.getTotal();
		long pages = coursePage.getPages();
		List<EduCourse> courseList = coursePage.getRecords();
		boolean hasPrevious = coursePage.hasPrevious();
		boolean hasNext = coursePage.hasNext();

		HashMap<String, Object> map = new HashMap<>();
		map.put("current",current);
		map.put("size",size);
		map.put("total",total);
		map.put("pages",pages);
		map.put("courseList",courseList);
		map.put("hasPrevious",hasPrevious);
		map.put("hasNext",hasNext);

		return map;

	}

	@Override
	public Result getCourseInfo(String id) {
    	//课程相关信息
    	CourseInfoVo courseInfoVo = baseMapper.getCourseInfo(id);

    	//章节小节信息
		List<OneChapter> chapterList = chapterService.getChapterList(id);
		return Result.ok().data("courseInfo",courseInfoVo).data("chapterList",chapterList);
	}
}
