package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.Exception.TeacherException;
import com.guli.common.ResultCode;
import com.guli.teacher.entity.EduSubject;
import com.guli.teacher.entity.vo.OneSubject;
import com.guli.teacher.entity.vo.TwoSubject;
import com.guli.teacher.mapper.EduSubjectMapper;
import com.guli.teacher.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guli
 * @since 2019-08-20
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectMapper subjectMapper;

    @Override
    public List<String> importFile(MultipartFile file) {
        List<String> list = null;
        try {
            list = new ArrayList<>();
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum < 1){
                list.add("请添加数据再上传");
                return list;
            }

            for (int i = 1; i <= lastRowNum ; i++) {
                Row row = sheet.getRow(i);
                if(StringUtils.isEmpty(row)){
                    list.add("第" + i + "行为空");
                    continue;
                }

                Cell cell1 = row.getCell(0);
                if(cell1 == null){
                    list.add("第" + i + "行，第1列为空");
                    continue;
                }

                String stringCellValue = cell1.getStringCellValue();
                if(StringUtils.isEmpty(stringCellValue)){
                    list.add("第" + i + "行，第1列值为空");
                    continue;
                }

                EduSubject subject = getSubjectByTitle(stringCellValue);
                EduSubject eduSubject = new EduSubject();
                if(subject == null){
                    //添加
                    eduSubject.setParentId("0");
                    eduSubject.setSort(0);
                    eduSubject.setTitle(stringCellValue);
                    baseMapper.insert(eduSubject);
                    eduSubject.setId(eduSubject.getId());
                }else{
                    eduSubject.setId(subject.getId());
                }

                Cell cell2 = row.getCell(1);
                if(cell2 == null){
                    list.add("第" + i + "行，第2列为空");
                    continue;
                }

                String cellValue = cell2.getStringCellValue();
                if(StringUtils.isEmpty(cellValue)){
                    list.add("第" + i + "行，第2列值为空");
                    continue;
                }

                String pid = eduSubject.getId();


                List<EduSubject> subjects = getSubjectsByTitleAndParentId(cellValue, pid);

                if(subjects.size() == 0){
                    //添加
                    eduSubject.setParentId(pid);
                    eduSubject.setSort(0);
                    eduSubject.setTitle(cellValue);
                    eduSubject.setId(null) ;
                    baseMapper.insert(eduSubject);

                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<EduSubject> getSubjectsByTitleAndParentId(String cellValue, String pid) {
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",cellValue);
        queryWrapper.eq("parent_id",pid);
        return baseMapper.selectList(queryWrapper);
    }

    private EduSubject getSubjectByTitle(String title) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",title);
        wrapper.eq("parent_id",0);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<OneSubject> getTree() {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        List<OneSubject> list = new ArrayList<>();
        for (EduSubject eduSubject : eduSubjects) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            QueryWrapper<EduSubject> subWrapper = new QueryWrapper<>();
            subWrapper.eq("parent_id",eduSubject.getId());
            List<EduSubject> eduSubjects1 = baseMapper.selectList(subWrapper);
            for (EduSubject subject : eduSubjects1) {
                TwoSubject twoSubject = new TwoSubject();
                BeanUtils.copyProperties(subject,twoSubject);
                oneSubject.getChildren().add(twoSubject);
            }
            list.add(oneSubject);
        }
        return list;
    }

    @Override
    public Boolean removeSubject(String id) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapper);
        if(eduSubjects.size() != 0){
            throw new TeacherException(ResultCode.EXISTS_NODES,"存在子节点不可删除");
        }
        return baseMapper.deleteById(id) == 1;
    }

    @Override
    public boolean saveSubject(EduSubject subject) {
        if(subject.getParentId() == "0"){
            EduSubject OneLevelSubject = this.getSubjectByTitle(subject.getTitle());
            if(OneLevelSubject != null){
                throw new TeacherException(ResultCode.EXISTS_NODES,"节点已存在");
            }
            return baseMapper.insert(subject) == 1;
        }

        List<EduSubject> twoLevelSubject = this.getSubjectsByTitleAndParentId(subject.getTitle(), subject.getParentId());
        if(twoLevelSubject.size() == 1){
            throw new TeacherException(ResultCode.EXISTS_NODES,"节点已存在");
        }
        return baseMapper.insert(subject) == 1;
    }

    @Override
    public boolean updateSubject(EduSubject subject) {
        if(subject.getParentId() == "0"){
            EduSubject OneSubject = this.getSubjectByTitle(subject.getTitle());
            if(OneSubject != null){
                throw new TeacherException(ResultCode.EXISTS_NODES,"节点已存在");
            }
            return baseMapper.updateById(subject) == 1;
        }

        List<EduSubject> twoSubject = this.getSubjectsByTitleAndParentId(subject.getTitle(), subject.getParentId());
        if(twoSubject.size() == 1){
            throw new TeacherException(ResultCode.EXISTS_NODES,"节点已存在");
        }
        return baseMapper.updateById(subject) == 1;
    }
}
