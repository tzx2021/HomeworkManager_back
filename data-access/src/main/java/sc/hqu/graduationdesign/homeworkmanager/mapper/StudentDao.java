package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentEntity;
import sc.hqu.graduationdesign.homeworkmanager.model.ClassStudentView;
import vinfer.learnjava.queryhelper.annotation.EnableInterception;

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
    @Select("select student_no,class_name,name,gender,contact from v_class_student where class_id=#{cid}")
    List<ClassStudentView> querySimpleInfoByClassIdInView(Long cid);

    /**
     * 查询所有学生的学号
     * @return          学生学号的集合
     */
    @Select("select student_no from t_student")
    List<Long> queryAllStudentNoExisted();

    /**
     * 通过学生学号集合查询班级学生的简易信息
     * @param studentNoList     学生学号
     * @return                  {@link ClassStudentView}
     */
    List<ClassStudentView> querySimpleInfoByStudentNoListInView(@Param("studentNoList") List<Long> studentNoList);

    /**
     * 从视图数据中查询班级所有学生的详细信息
     * @param cid       班级id
     * @return          {@link ClassStudentView}
     */
    @Select("select student_no,class_name,name,gender,contact,email,social_account from v_class_student where class_id=#{cid}")
    List<ClassStudentView> queryFullInfoByClassIdInView(Long cid);

    /**
     * 通过教工号查询教师带领的所有班级中所有的学生信息
     * @param teacherNo     教工号
     * @return              {@link ClassStudentView}
     */
    @EnableInterception
    @Select("select class_id,student_no,class_name,name,gender,contact,email,social_account from v_class_student where class_id in(" +
            "select id from t_class where head_teacher_no=#{teacherNo}" +
            ")")
    List<ClassStudentView> queryAllStudentByTeacherNo(Long teacherNo);


    /**
     * 批量插入学生信息记录
     * @param studentEntities       {@link StudentEntity}
     */
    void batchInsertStudent(@Param("ses")List<StudentEntity> studentEntities);

    /**
     * 将学生移出班级，这里移出的逻辑通过将班级id设置为0来实现
     * @param studentNo             学生号
     */
    @Delete("delete from t_student where student_no=#{studentNo}")
    void deleteStudentFromClass(Long studentNo);

}
