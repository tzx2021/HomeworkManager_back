package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.SystemAccountEntity;
import sc.hqu.graduationdesign.homeworkmanager.entity.TeacherEntity;

/**
 * 系统账户信息数据访问接口
 * 当前系统是教师端系统，因此账户的账号直接和教师的教工号进行绑定
 * @author tzx
 * @date 2021-03-14 23:42
 */
@Repository
public interface SystemAccountDao {

    /**
     * 通过教工号查询账户信息
     * @param tNo       教工号
     * @return          {@link SystemAccountEntity}
     */
    @Select("select account,password,type,enable where t_system_account where account=#{tNo}")
    SystemAccountEntity queryByTeacherNo(Long tNo);

    /**
     * 通过教工号查询教师个人信息
     * @param tNo       教工号
     * @return          {@link TeacherEntity}
     */
    @Select("select teacher_no,name,gender,contact,address,job_title from t_teacher where teacher_no=#{tNo}")
    TeacherEntity queryTeacherInfoByTeacherNo(Long tNo);

    /**
     * 更新账户登录密码
     * @param newPassword       新的登录密码
     * @param account           系统账号（教工号）
     */
    @Update("update t_system_account set password=#{newPassword} where account=#{account}")
    void updatePasswordByAccount(@Param("newPassword")String newPassword,@Param("account") Long account);

    /**
     * 更新账户的可用状态
     * @param state     可用状态，1表示启用，0表示禁用
     * @param account   系统账号
     */
    @Update("update t_system_account set enable=#{state} where account=#{account}")
    void updateEnableStateByAccount(@Param("state")Integer state,@Param("account")Long account);

    /**
     * 插入系统账号信息记录
     * @param sae       {@link SystemAccountEntity}
     */
    @Insert("insert into t_system_account(account,password,type,enable) values(" +
            "#{account},#{password},#{type},#{enable}"
            + ")")
    void insertSystemAccount(SystemAccountEntity sae);

}
