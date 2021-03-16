package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentEntity;
import sc.hqu.graduationdesign.homeworkmanager.model.ClassStudentView;

import java.util.List;

/**
 * 学生信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:44
 */
@Repository
public interface StudentDao {

    /**
     * 从视图数据中查询班级所有学生的简单信息
     * @param cid       班级id
     * @return          {@link ClassStudentView}
     */
    @Select("select student_no,name,gender from v_class_student where class_id=#{cid}")
    List<ClassStudentView> querySimpleInfoByClassIdInView(Long cid);

    /**
     * 从视图数据中查询班级所有学生的详细信息
     * @param cid       班级id
     * @return          {@link ClassStudentView}
     */
    @Select("select student_no,name,gender,contact,email,social_account from v_class where class_id=#{cid}")
    List<ClassStudentView> queryFullInfoByClassIdInView(Long cid);

    /**
     * 通过课程id查询已选该课程的学生
     * @param cid       课程id
     * @return          {@link StudentEntity}
     */
    @Select("select ts.student_no,name,gender,contact from t_student ts join t_course tc on " +
            "ts.student_no=tc.student_no where tc.class_id=#{cid}")
    List<StudentEntity> queryCourseStudentByCourseId(Long cid);

    /**
     * 批量插入学生信息记录
     * @param studentEntities       {@link StudentEntity}
     */
    void batchInsertStudent(@Param("ses")List<StudentEntity> studentEntities);

}
