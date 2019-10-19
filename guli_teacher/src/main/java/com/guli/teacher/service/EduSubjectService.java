package com.guli.teacher.service;

import com.guli.teacher.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guli
 * @since 2019-08-20
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importFile(MultipartFile file);

    List<OneSubject> getTree();

    Boolean removeSubject(String id);

    boolean saveSubject(EduSubject subject);

    boolean updateSubject(EduSubject subject);
}
