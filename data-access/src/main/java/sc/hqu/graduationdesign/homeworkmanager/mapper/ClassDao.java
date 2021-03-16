package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Select("select id,class_code,name,total_student_num,create_date from t_class where head_teacher_no = #{tNo}")
    List<ClassEntity> queryAllByTeacherNo(Long tNo);

    /**
     * 插入班级信息记录
     * @param ce        {@link ClassEntity}
     */
    @Insert("insert into t_class(head_teacher_no,class_code,create_date) values(" +
            "#{headTeacherNo},#{classCode},#{createDate}"
            + ")")
    void insertClass(ClassEntity ce);

    /**
     * 更新班级信息
     * @param ce        {@link ClassEntity}
     */
    @Update("update t_class set head_Teacher_no=#{headTeacherNo},class_code=#{classCode},name=#{name} " +
            "where id = #{ce.id}")
    void updateClass(ClassEntity ce);

    /**
     * 删除班级信息
     * @param cId       班级id
     */
    @Delete("delete from t_class where id=#{cId}")
    void deleteClassById(Long cId);

}
