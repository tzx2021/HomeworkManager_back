package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.StudentClassEntity;

import java.util.List;

/**
 * 学生班级关联信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:45
 */
@Repository
public interface StudentClassDao {

    /**
     * 批量插入学生班级关联信息记录
     * @param studentClassEntities      {@link StudentClassEntity}
     */
    void batchInsertStudentClass(@Param("studentClassEntities")List<StudentClassEntity> studentClassEntities);

    /**
     * 批量删除学生班级信息记录
     * @param idList        学生学号集合
     */
    void batchDeleteStudentClass(@Param("idList")List<Long> idList);

}
