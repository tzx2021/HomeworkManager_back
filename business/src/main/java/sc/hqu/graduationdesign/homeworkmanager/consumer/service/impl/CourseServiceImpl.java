package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sc.hqu.graduationdesign.homeworkmanager.constant.FilePublishType;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.CourseService;
import sc.hqu.graduationdesign.homeworkmanager.entity.CourseEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentCourseEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherCourseEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.CourseDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FileDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.StudentCourseDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.TeacherCourseDao;
import sc.hqu.graduationdesign.homeworkmanager.utils.SecurityContextUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tzx
 * @date 2021-04-07 22:03
 */
@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentCourseDao studentCourseDao;

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private FileDao fileDao;

    @Override
    public List<CourseDataDto> getCourseDataByTeacherNo(Long teacherNo) {
        List<CourseEntity> courseEntities = courseDao.queryAllByTeacherNo(teacherNo);
        List<CourseDataDto> courseDataDtoList = new ArrayList<>(courseEntities.size());
        courseEntities.forEach(courseEntity -> {
            Long courseId = courseEntity.getId();
            CourseDataDto courseDataDto = new CourseDataDto();
            BeanUtils.copyProperties(courseEntity,courseDataDto);

            // 查询并设置选课信息
            List<CourseElectionData> courseElectionDataList = new ArrayList<>(8);
            studentCourseDao.queryStudentCourseElectionByCourseId(courseId).forEach(view -> {
                CourseElectionData courseElectionData = new CourseElectionData();
                BeanUtils.copyProperties(view,courseElectionData);
                courseElectionDataList.add(courseElectionData);
            });
            courseDataDto.setCourseElectionData(courseElectionDataList);

            // 查询该课程发布的文件
            List<SimpleFileDataDto> fileDataDtoList = new ArrayList<>(8);
            fileDao.queryPublishFile(courseId, FilePublishType.COURSE.getType()).forEach(fileEntity -> {
                SimpleFileDataDto simpleFileDataDto = new SimpleFileDataDto();
                BeanUtils.copyProperties(fileEntity,simpleFileDataDto);
                fileDataDtoList.add(simpleFileDataDto);
            });
            courseDataDto.setCourseFiles(fileDataDtoList);
            courseDataDtoList.add(courseDataDto);
        });
        return courseDataDtoList;
    }

    @Override
    public List<CourseDataDto> getStudentSelectableCourse(Long studentNo, Long teacherNo) {
        return courseDao.querySelectableByStudentNo(teacherNo, studentNo).stream()
                .map(courseEntity -> new CourseDataDto(courseEntity.getId(), courseEntity.getName(), courseEntity.getClassTime()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public CourseCreateDto create(CourseCreateDto courseCreateDto) {
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseCreateDto,courseEntity);
        courseEntity.setCreateDate(Calendar.getInstance().getTimeInMillis());
        // 保存课程记录
        courseDao.insertCourse(courseEntity);
        courseCreateDto.setId(courseEntity.getId());
        // 保存教师课程记录
        TeacherCourseEntity tce = new TeacherCourseEntity();
        tce.setCourseId(courseEntity.getId());
        Long teacherNo = Long.valueOf(SecurityContextUtil.userDetails().getUsername());
        tce.setTeacherNo(teacherNo);
        teacherCourseDao.insertTeacherCourse(tce);
        return courseCreateDto;
    }

    @Override
    public void update(CourseUpdateDto dto) {
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(dto,courseEntity);
        courseDao.updateCourse(courseEntity);
    }

    @Override
    public void deleteCourse(Long courseId) {
        // 前端需要控制已被学生选课的课程无法删除
        courseDao.deleteCourseById(courseId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public void selectCourse(List<StudentCourseEntity> studentCourseEntities) {
        studentCourseDao.batchInsertStudentCourse(studentCourseEntities);
    }
}
