package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentCourseEntity;

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
     * @param studentNoList     学生学号集合
     */
    void batchDeleteStudentCourse(@Param("studentNoList") List<Long> studentNoList);

}
