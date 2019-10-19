package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.Exception.TeacherException;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.EduChapter;
import com.guli.teacher.entity.EduVideo;
import com.guli.teacher.entity.vo.OneChapter;
import com.guli.teacher.entity.vo.TwoVideo;
import com.guli.teacher.mapper.EduChapterMapper;
import com.guli.teacher.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<OneChapter> getChapterList(String courseId) {
        ArrayList<OneChapter> oneChapters = new ArrayList<>();
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.orderByAsc("sort");
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper);
        if(eduChapters == null){
            throw new TeacherException(ResultCode.CHAPTER_NOT_EXISTS,"章节不存在");
        }
        for (EduChapter eduChapter : eduChapters) {
            OneChapter oneChapter = new OneChapter();
            BeanUtils.copyProperties(eduChapter,oneChapter);
            QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("chapter_id",eduChapter.getId());
            queryWrapper.orderByAsc("sort");
            List<EduVideo> list = videoService.list(queryWrapper);
            for (EduVideo eduVideo : list) {
                TwoVideo twoVideo = new TwoVideo();
                BeanUtils.copyProperties(eduVideo,twoVideo);
                oneChapter.getChildren().add(twoVideo);
            }

            oneChapters.add(oneChapter);
        }
        return oneChapters;
    }

    @Override
    public boolean saveChapter(EduChapter eduChapter, String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("title",eduChapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if(count == 1){
            throw new TeacherException(ResultCode.CHAPTER_EXISTS,"章节已存在！");
        }
        eduChapter.setCourseId(courseId);
        int row = baseMapper.insert(eduChapter);
        return row == 1;
    }

    @Override
    public boolean updateChapter(EduChapter chapter) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",chapter.getCourseId());
        wrapper.eq("title",chapter.getTitle());
        Integer count = baseMapper.selectCount(wrapper);
        if(count == 1){
            throw new TeacherException(ResultCode.CHAPTER_EXISTS,"章节已存在！");
        }
        int row = baseMapper.updateById(chapter);
        return row == 1;

    }

    @Override
    public boolean romoveChapterById(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        List<EduVideo> list = videoService.list(wrapper);
        if(list != null && list.size() >0){
            throw new TeacherException(ResultCode.EXISTS_VIDEO,"存在video不可删除");
        }
        int i = baseMapper.deleteById(id);
        return i==1;
    }


}
