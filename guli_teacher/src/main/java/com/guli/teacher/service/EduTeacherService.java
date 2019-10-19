package com.guli.teacher.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.teacher.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.vo.TeacherVo;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author guli
 * @since 2019-08-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void pageCondition(Page<EduTeacher> page, TeacherVo condition);

	Map<String,Object> listAllTeachersByPage(Page<EduTeacher> teacherPage);

	Map<String,Object> getTeacherInfoById(String id);
}
