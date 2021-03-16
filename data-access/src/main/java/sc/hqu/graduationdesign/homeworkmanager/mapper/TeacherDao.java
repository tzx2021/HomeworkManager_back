package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherEntity;

/**
 * 教师信息数据访问接口
 *
 * TODO 目前教师端系统暂不提供外部注册的入口
 *      教师的管理账号应由学校更顶层的OA系统进行管控而不应该对外开发注册
 *      系统可以通过rpc的方式对两端数据库的必要数据进行同步
 *
 * @author tzx
 * @date 2021-03-14 23:45
 */
@Repository
public interface TeacherDao {

    /**
     * 通过教工号查询教师信息
     * @param tNo       教工号
     * @return          {@link TeacherEntity}
     */
    @Select("select teacher_no,name,gender,contact,address,job_title from t_teacher where teacher_no=#{tNo}")
    TeacherEntity queryByTeacherNo(Long tNo);

    /**
     * 通过教工号更新教师的联系方式
     * @param tNo           教工号
     * @param newContact    新的联系方式
     */
    @Update("update t_teacher set contact=#{newContact} where teacher_no=#{tNo}")
    void updateContactByTeacherNo(@Param("tNo")Long tNo,@Param("newContact") String newContact);

    /**
     * 通过教工号更新教师的住址
     * @param tNo           教工号
     * @param newAddress    新的住址
     */
    @Update("update t_teacher set address=#{newAddress} where teacher_no=#{tNo}")
    void updateAddressByTeacherNo(@Param("tNo")Long tNo,@Param("newAddress") String newAddress);

    /**
     * 插入教师信息记录
     * @param te        {@link TeacherEntity}
     */
    @Insert("insert into t_teacher(teacher_no,name,gender,contact,address,job_title) values(" +
            "#{teacherNo},#{name},#{gender},#{contact},#{address},#{jobTitle}"
            + ")")
    void insertTeacher(TeacherEntity te);

}
