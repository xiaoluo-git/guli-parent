package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduCourse;
import com.guli.teacher.entity.EduTeacher;
import com.guli.teacher.mapper.EduTeacherMapper;
import com.guli.teacher.service.EduCourseService;
import com.guli.teacher.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-14
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
	@Autowired
	private EduCourseService courseService;

    @Override
    public void pageCondition(Page<EduTeacher> page, TeacherVo condition) {
        if(condition == null){
            baseMapper.selectPage(page,null);
            return;
        }
        String name = condition.getName();
        Integer level = condition.getLevel();
        String begin = condition.getBegin();
        String end = condition.getEnd();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        baseMapper.selectPage(page,wrapper);

    }

	@Override
	public Map<String, Object> listAllTeachersByPage(Page<EduTeacher> teacherPage) {
    	baseMapper.selectPage(teacherPage,null);

		long current = teacherPage.getCurrent();
		long size = teacherPage.getSize();
		long total = teacherPage.getTotal();
		long pages = teacherPage.getPages();
		List<EduTeacher> teacherList = teacherPage.getRecords();
		boolean hasPrevious = teacherPage.hasPrevious();
		boolean hasNext = teacherPage.hasNext();

		HashMap<String, Object> map = new HashMap<>();
		map.put("current",current);
		map.put("size",size);
		map.put("total",total);
		map.put("pages",pages);
		map.put("teacherList",teacherList);
		map.put("hasPrevious",hasPrevious);
		map.put("hasNext",hasNext);

		return map;
	}

	@Override
	public Map<String, Object> getTeacherInfoById(String id) {
		EduTeacher teacher = baseMapper.selectById(id);
		QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
		wrapper.eq("teacher_id",id);
		List<EduCourse> courseList = courseService.list(wrapper);
		HashMap<String, Object> map = new HashMap<>();
		map.put("teacher",teacher);
		map.put("courseList",courseList);
		return map;
	}
}
