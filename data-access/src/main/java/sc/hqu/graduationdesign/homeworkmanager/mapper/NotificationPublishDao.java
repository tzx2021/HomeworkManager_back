package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationPublishEntity;

import java.util.List;

/**
 * 通知发布信息数据访问接口
 * @author tzx
 * @date 2021-03-17 1:03
 */
@Repository
public interface NotificationPublishDao {

    /**
     * 插入通知发布信息记录
     * @param notificationPublishEntities       {@link NotificationPublishEntity}
     */
    void batchInsert(@Param("npeList") List<NotificationPublishEntity> notificationPublishEntities);

    /**
     * 通过通知id查询所有的通知发布记录
     * @param nid       通知id
     * @return          {@link NotificationPublishEntity}
     */
    @Select("select pid,member_type from t_notification_publish where nid=#{nid}")
    List<NotificationPublishEntity> queryByNotificationId(Long nid);

}
