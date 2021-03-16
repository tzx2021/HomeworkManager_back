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
     * 插入课程信息记录
     * @param ce        {@link CourseEntity}
     */
    @Insert("insert into t_course(name,credit,class_time,course_period,create_date) values(" +
            "#{name},#{credit},#{classTime},#{coursePeriod},#{createDate}"
            + ")")
    void insertCourse(CourseEntity ce);

    /**
     * 更新课程信息
     * @param ce        {@link CourseEntity}
     */
    @Update("update t_course set name=#{name},class_time=#{classTime},course_period=#{coursePeriod} where " +
            "id=#{id}")
    void updateCourse(CourseEntity ce);

    /**
     * 更新课程学分   TODO    课程被选后不能修改学分
     * @param newCredit     新的课程学分
     * @param cid           课程id
     */
    @Update("update t_course set credit=#{newCredit} where id=#{cid}")
    void updateCourseCreditById(@Param("newCredit") Float newCredit, @Param("cIi") Integer cid);

    /**
     * 通过课程id删除课程信息     TODO    课程被选后不能删除
     * @param cid       课程id
     */
    @Delete("delete from t_course where id=#{cid}")
    void deleteCourseById(Long cid);

}
