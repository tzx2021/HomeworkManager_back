package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.CourseEntity;

import java.util.List;

/**
 * 课程信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:43
 */
@Repository
public interface CourseDao {

    /**
     * 根据教工号查询该教师教授的所有课程
     * @param tNo       教工号
     * @return          {@link CourseEntity}
     */
    @Select("select id,name,credit,class_time,course_period,create_date from t_course where id in (" +
            "select course_id from t_teacher_course where teacher_no=#{tNo}"
            + ")")
    List<CourseEntity> queryAllByTeacherNo(Long tNo);

    /**
     * 选取一个学生在教师发布的所有课程中为选取的课程
     * @param teacherNo         教工号
     * @param studentNo         学号
     * @return                  {@link CourseEntity}
     */
    @Select("select tc.id,tc.name,class_time from t_course tc,t_teacher_course ttc where " +
            "ttc.course_id = tc.id and ttc.teacher_no=#{teacherNo} " +
            "and tc.id not in (select course_id from t_student_course where student_no=#{studentNo})")
    List<CourseEntity> querySelectableByStudentNo(Long teacherNo,Long studentNo);

    /**
     * 插入课程信息记录
     * @param ce        {@link CourseEntity}
     */
    void insertCourse(@Param("ce") CourseEntity ce);

    /**
     * 更新课程信息
     * @param ce        {@link CourseEntity}
     */
    @Update("update t_course set name=#{name},class_time=#{classTime},course_period=#{coursePeriod} where " +
            "id=#{id}")
    void updateCourse(CourseEntity ce);

    /**
     * 通过课程id删除课程信息     TODO    课程被选后不能删除
     * @param cid       课程id
     */
    @Delete("delete from t_course where id=#{cid}")
    void deleteCourseById(Long cid);

}
