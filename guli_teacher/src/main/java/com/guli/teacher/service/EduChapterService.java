package com.guli.teacher.service;

import com.guli.teacher.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneChapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
public interface EduChapterService extends IService<EduChapter> {

    List<OneChapter> getChapterList(String courseId);

    boolean saveChapter(EduChapter eduChapter, String courseId);

    boolean updateChapter(EduChapter chapter);

    boolean romoveChapterById(String id);
}
