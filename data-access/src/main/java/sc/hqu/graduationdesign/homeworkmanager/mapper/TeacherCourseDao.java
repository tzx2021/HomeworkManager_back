package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherCourseEntity;


/**
 * 教师课程信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:46
 */
@Repository
public interface TeacherCourseDao {

    /**
     * 插入教师课程关联信息记录
     * @param tce       {@link TeacherCourseEntity}
     */
    @Insert("insert into t_teacher_course(teacher_no,course_id) values(#{teacherNo},#{courseId})")
    void insertTeacherCourse(TeacherCourseEntity tce);

    /**
     * 通过删除教师教授的课程
     * @param id        课程关联id
     */
    @Delete("delete from t_teacher_course where id=#{id}")
    void deleteById(Long id);

}
