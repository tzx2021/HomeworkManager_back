package sc.hqu.graduationdesign.homeworkmanager.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import sc.hqu.graduationdesign.homeworkmanager.entity.NotificationPublishEntity;

/**
 * 通知发布信息数据访问接口
 * @author tzx
 * @date 2021-03-17 1:03
 */
@Repository
public interface NotificationPublishDao {

    /**
     * 插入通知发布信息记录
     * @param npe       {@link NotificationPublishEntity}
     */
    @Insert("insert into t_notification_publish(pid,nid) values(" +
            "#{pid},#{nid}"
            + ")")
    void insertNotificationPublish(NotificationPublishEntity npe);

}
