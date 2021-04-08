package sc.hqu.graduationdesign.homeworkmanager.consumer.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.*;
import sc.hqu.graduationdesign.homeworkmanager.consumer.service.CourseService;
import sc.hqu.graduationdesign.homeworkmanager.entity.CourseEntity;
import sc.hqu.graduationdesign.homeworkmanager.mapper.CourseDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.FileDao;
import sc.hqu.graduationdesign.homeworkmanager.mapper.StudentCourseDao;
import sc.hqu.graduationdesign.homeworkmanager.model.StudentCourseElectionView;

import java.util.ArrayList;
import java.util.List;

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
                BeanUtils.copyProperties(view,courseDataDto);
                courseElectionDataList.add(courseElectionData);
            });
            courseDataDto.setCourseElectionData(courseElectionDataList);

            // 查询该课程发布的文件
            List<SimpleFileDataDto> fileDataDtoList = new ArrayList<>(8);
            fileDao.queryByPublishId(courseId).forEach(fileEntity -> {
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
    public CourseCreateDto create(CourseCreateDto courseCreateDto) {
        CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseCreateDto,courseEntity);
        courseDao.insertCourse(courseEntity);
        courseCreateDto.setId(courseEntity.getId());
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
}
