package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentCourseEntity;
import sc.hqu.graduationdesign.homeworkmanager.model.StudentCourseElectionView;

import java.util.List;

/**
 * 学生课程关联信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:45
 */
@Repository
public interface StudentCourseDao {

    /**
     * 批量插入学生课程信息记录
     * @param studentCourseEntities     {@link StudentCourseEntity}
     */
    void batchInsertStudentCourse(@Param("studentCourseEntities")List<StudentCourseEntity> studentCourseEntities);

    /**
     * 批量删除学生课程信息记录
     * @param idList     关联记录id集合
     */
    void batchDeleteStudentCourse(@Param("idList") List<Long> idList);

    /**
     * 从视图中查询学生选课信息
     * @param courseId      课程id
     * @return              {@link StudentCourseElectionView}
     */
    @Select("select student_no,class_name,student_name,gender,contact from v_student_course_election where course_id=#{courseId}")
    List<StudentCourseElectionView> queryStudentCourseElectionByCourseId(Long courseId);

}
