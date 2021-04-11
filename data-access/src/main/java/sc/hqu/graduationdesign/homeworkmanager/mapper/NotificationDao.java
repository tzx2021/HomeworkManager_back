package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationEntity;
import vinfer.learnjava.queryhelper.annotation.EnableInterception;

import java.util.List;

/**
 * 通知信息数据访问接口
 * @author tzx
 * @date 2021-03-14 23:43
 */
@Repository
public interface NotificationDao {

    /**
     * 通过系统账号查询该账号发布的所有通知信息
     * @param account       系统账号
     * @return              {@link NotificationEntity}
     */
    @EnableInterception
    @Select("select id,title,content,type,confirmable,total_confirm,member_type,create_date from t_notification where " +
            "account=#{account}")
    List<NotificationEntity> queryAllByAccount(Long account);

    /**
     * 插入通知记录
     * @param ne        {@link NotificationEntity}
     */
    void insertNotification(@Param("ne") NotificationEntity ne);

}
