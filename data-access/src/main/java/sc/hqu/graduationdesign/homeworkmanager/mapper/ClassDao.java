package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.ClassEntity;

import java.util.List;

/**
 * 课程数据访问接口
 * @author tzx
 * @date 2021-03-14 23:43
 */
@Repository
public interface ClassDao {

    /**
     * 通过教工号查询教师管理的所有班级信息
     * @param tNo       教工号
     * @return          {@link ClassEntity}
     */
    @Select("select id,class_code,head_teacher_no,name,total_student_num,create_date from t_class where head_teacher_no=#{tNo}")
    List<ClassEntity> queryAllByTeacherNo(Long tNo);

    /**
     * 根据id查询班级信息
     * @param id        班级信息id
     * @return          {@link ClassEntity}
     */
    @Select("select id,class_code,name,total_student_num,create_date from t_class where id=#{id}")
    ClassEntity queryById(Long id);

    /**
     * 插入班级信息记录
     * @param ce        {@link ClassEntity}
     */
    @Insert("insert into t_class(head_teacher_no,name,class_code,create_date) values(" +
            "#{headTeacherNo},#{name},#{classCode},#{createDate}"
            + ")")
    void insertClass(ClassEntity ce);

    /**
     * 更新班级信息
     * @param ce        {@link ClassEntity}
     */
    @Update("update t_class set class_code=#{classCode},name=#{name} " +
            "where id=#{id}")
    void updateClass(ClassEntity ce);

    /**
     * 更新班级学生数
     * @param id        班级id
     * @param num       学生总数
     */
    @Update("update t_class set total_student_num=total_student_num+#{num} where id=#{id}")
    void updateClassStudentNumByClassId(@Param("id") Long id, @Param("num") Integer num);

    /**
     * 根据学生学号更新班级学生数
     * @param studentNo     学生学号
     */
    @Update("update t_class set total_student_num=total_student_num-1 where id=(" +
            "select class_id from t_student where student_no=#{studentNo}"+
            ")")
    void reduceClassStudentNumByStudentNo(Long studentNo);

    /**
     * 删除班级信息
     * @param cId       班级id
     */
    @Delete("delete from t_class where id=#{cId}")
    void deleteClassById(Long cId);

}
