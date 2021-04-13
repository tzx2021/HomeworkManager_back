package sc.hqu.graduationdesign.homeworkmanager.consumer.service;

import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.CourseCreateDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.CourseDataDto;
import sc.hqu.graduationdesign.homeworkmanager.consumer.dto.CourseUpdateDto;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentCourseEntity;

import java.util.List;

/**
 * 课程服务接口
 * @author tzx
 * @date 2021-04-07 20:27
 */
public interface CourseService {

    /**
     * 通过教工号查询该教师任教的所有课程
     * @param teacherNo         教工号
     * @return                  {@link CourseDataDto}
     */
    List<CourseDataDto> getCourseDataByTeacherNo(Long teacherNo);

    /**
     * 获取学生可选的课程
     * @param studentNo         学号
     * @param teacherNo          教工号
     * @return                  {@link CourseDataDto}
     */
    List<CourseDataDto> getStudentSelectableCourse(Long studentNo, Long teacherNo);

    /**
     * 课程创建
     * @param courseCreateDto   {@link CourseCreateDto}
     * @return                  创建完成的课程信息
     */
    CourseCreateDto create(CourseCreateDto courseCreateDto);

    /**
     * 课程信息更新
     * @param dto               {@link CourseUpdateDto}
     */
    void update(CourseUpdateDto dto);

    /**
     * 删除课程
     * @param courseId          课程id
     */
    void deleteCourse(Long courseId);

    /**
     * 学生选课
     * @param studentCourseEntities     学生课程管理记录实体对象
     */
    void selectCourse(List<StudentCourseEntity> studentCourseEntities);

}
